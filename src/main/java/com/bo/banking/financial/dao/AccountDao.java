package com.bo.banking.financial.dao;

import com.bo.banking.customer.model.Customer;
import com.bo.banking.financial.model.Account;
import com.bo.banking.financial.model.Transaction;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper
public interface AccountDao {

    @Select("Select * from account where customer_id=#{id}")
    @ResultMap("accountResultMap")
    List<Account> findAllByCustomerId(Long id);

    @Insert("Insert into account(iban,balance,customer_id) VALUES(#{iban}, #{balance}, #{customer.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int add(Account account);

    @Update("UPDATE account SET iban=#{iban}, balance =#{balance}, customer_id =#{customer.id} WHERE id =#{id}")
    int update(Account account);

    @Delete("Delete from account where id =#{id}")
    int delete(Long id);

    @Select("select * from customer where id = #{customerId}")
    @Results(id = "customerResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName")
    })
    Customer selectCustomer(Long customerId);

    @Select("select * from transaction where from_account_id = #{accountId} or to_account_id = #{accountId}")
    @Results(id = "transactionResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "amount", property = "amount"),
            @Result(column = "description", property = "description"),
            @Result(column = "from_account_id", property = "fromAccount", javaType = Account.class, one = @One(select = "selectAccount")),
            @Result(column = "to_account_id", property = "toAccount", javaType = Account.class, one = @One(select = "selectAccount"))
    })
    List<Transaction> selectTransactions(Long accountId);

    @Select("select * from account where id = #{id}")
    @Results(id = "accountResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "iban", property = "iban"),
            @Result(column = "balance", property = "balance"),
            @Result(column = "customer_id", property = "customer", javaType = Customer.class, one = @One(select = "selectCustomer")),
            @Result(column = "id", property = "transactions", javaType = ArrayList.class, many = @Many(select = "selectTransactions"))
    })
    Optional<Account> selectAccount(Long id);
}
