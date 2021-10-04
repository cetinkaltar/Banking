package com.bo.banking.financial.dao;

import com.bo.banking.financial.model.Account;
import com.bo.banking.financial.model.Transaction;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface TransactionDao {
    @Select("Select * from transaction where id=#{id}")
    Optional<Transaction> findById(Long id);

    @Insert("Insert into transaction(amount,description,from_account_id,to_account_id,date) VALUES(#{amount}, #{description}, #{fromAccount.id}, #{toAccount.id}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int add(Transaction transaction);

    @Delete("Delete from transaction where id=#{id}")
    int delete(Long id);

    @Select("select * from transaction where (from_account_id = #{accountId} or to_account_id = #{accountId}) and date::date >= #{dayFrom} and date <= #{dayTo}")
    @Results(id = "transactionResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "amount", property = "amount"),
            @Result(column = "description", property = "description"),
            @Result(column = "from_account_id", property = "fromAccount", javaType = Account.class, one = @One(select = "selectAccount")),
            @Result(column = "to_account_id", property = "toAccount", javaType = Account.class, one = @One(select = "selectAccount"))
    })
    List<Transaction> selectTransactions(Long accountId, LocalDateTime dayFrom, LocalDateTime dayTo);

    @Select("select * from transaction where from_account_id = #{accountId} or to_account_id = #{accountId}")
    @ResultMap("transactionResultMap")
    List<Transaction> selectTransactionsByAccountId(Long accountId);

    @Select("select * from account where id = #{accountId}")
    @Results(id = "selectAccountResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "iban", property = "iban"),
            @Result(column = "balance", property = "balance"),
    })
    Account selectAccount(Long accountId);
}
