package com.bo.banking.customer.dao;

import com.bo.banking.customer.dto.CustomerRequest;
import com.bo.banking.customer.model.Customer;
import com.bo.banking.financial.model.Account;
import com.bo.banking.financial.model.Transaction;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper
public interface CustomerDao {
    @Select("select * from customer where id = #{id}")
    @ResultMap("customerResultMap")
    Optional<Customer> findById(Long id);

    @Insert("Insert into customer(first_name,last_name) VALUES(#{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    long add(Customer customer);

    @Update("UPDATE customer SET first_name=#{request.firstName}, last_name =#{request.lastName} WHERE id =#{request.id}")
    int update(@Param("request") Customer customer);

    @Delete("Delete from customer where id=#{id}")
    int delete(Long id);

    @Select("select * from customer  where upper(first_name) LIKE #{searchQuery} || '%' OR upper(first_name) LIKE #{searchQuery} || '%' OR upper(last_name) LIKE #{searchQuery} || '%'")
    @Results(id = "customerResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "id", property = "accounts", javaType = ArrayList.class, many = @Many(select = "selectAccountsByCustomerId"))
    })
    List<Customer> search(String searchQuery);

    @Select("select * from account where customer_id = #{customerId}")
    @Results(id = "accountResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "iban", property = "iban"),
            @Result(column = "balance", property = "balance"),
            @Result(column = "id", property = "transactions", javaType = ArrayList.class, many = @Many(select = "selectTransactions"))
    })
    List<Account> selectAccountsByCustomerId(Long customerId);

    @Select("select * from transaction where from_account_id = #{accountId} or to_account_id = #{accountId}")
    @Results(id = "transactionResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "amount", property = "amount"),
            @Result(column = "description", property = "description"),
            @Result(column = "from_account_id", property = "fromAccount", javaType = Account.class, one = @One(select = "selectAccount")),
            @Result(column = "to_account_id", property = "toAccount", javaType = Account.class, one = @One(select = "selectAccount"))
    })
    List<Transaction> selectTransactions(Long accountId);

    @Select("select * from account where id = #{accountId}")
    @Results(id = "selectAccountResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "iban", property = "iban"),
            @Result(column = "balance", property = "balance"),
    })
    Account selectAccount(Long accountId);
}
