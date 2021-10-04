package com.bo.banking.utils;

import com.bo.banking.customer.dto.CustomerRequest;
import com.bo.banking.customer.model.Customer;
import com.bo.banking.financial.dto.AccountRequest;
import com.bo.banking.financial.dto.TransactionRequest;
import com.bo.banking.financial.model.Account;
import com.bo.banking.financial.model.Transaction;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import static java.math.RoundingMode.HALF_UP;

public final class RandomUtils {
    public static final Random RANDOM = new Random();

    private RandomUtils() {
    }

    public static String randomString() {
        return randomString(20, 30);
    }

    public static String randomString(int min, int max) {
        return RandomStringUtils.randomAlphanumeric(randomInt(min, max));
    }

    public static int randomInt(int min, int max) {
        return min + RANDOM.nextInt(max - min + 1);
    }

    public static long randomLong() {
        return randomLong(0, 1000);
    }

    public static long randomLong(int min, int max) {
        return min + (long) (Math.random() * (max - min));
    }

    public static BigDecimal randomBigDecimal() {
        return randomBigDecimal(1, 999);
    }

    public static BigDecimal randomBigDecimal(int min, int max) {
        return new BigDecimal(randomInt(min * 1000, max * 1000)).divide(BigDecimal.valueOf(1000), 3, HALF_UP);
    }

    public static LocalDateTime randomLocalDateTime() {
        LocalDateTime result = LocalDateTime.now();
        return result.plusHours(randomInt(100, 500));
    }

    public static LocalDate randomLocalDate() {
        LocalDate result = LocalDate.now();
        return result.plusDays(randomInt(10, 50));
    }

    public static Customer randomCustomer() {
        Customer customer = new Customer();
        customer.setId(randomLong());
        customer.setFirstName(randomString());
        customer.setLastName(randomString());

        return customer;
    }

    public static CustomerRequest randomCustomerRequest() {
        CustomerRequest request = new CustomerRequest();
        request.setFirstName(randomString());
        request.setLastName(randomString());

        return request;
    }

    public static Account randomAccount() {
        Account account = new Account();
        account.setId(randomLong());
        account.setBalance(randomBigDecimal());
        account.setIban(randomString());

        return account;
    }

    public static Transaction randomTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(randomLong());
        transaction.setAmount(randomBigDecimal());
        transaction.setDescription(randomString());
        transaction.setFromAccount(randomAccount());
        transaction.setToAccount(randomAccount());
        transaction.setDate(randomLocalDateTime());

        return transaction;
    }

    public static AccountRequest randomAccountRequest() {
        AccountRequest request = new AccountRequest();
        request.setIban(randomString());
        request.setCustomerId(randomLong());
        request.setBalance(randomBigDecimal());

        return request;
    }

    public static TransactionRequest randomTransactionRequest() {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(randomBigDecimal());
        request.setDescription(randomString());
        request.setFromAccountId(randomLong());
        request.setToAccountId(randomLong());

        return request;
    }
}
