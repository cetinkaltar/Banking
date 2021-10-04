package com.bo.banking.financial.validator;

import com.bo.banking.financial.dto.TransactionRequest;
import com.bo.banking.financial.model.Account;
import com.bo.banking.financial.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TransactionRequestConstraintValidator implements ConstraintValidator<ValidTransactionRequest, TransactionRequest> {
    private final AccountService accountService;

    private static final String NOT_NULL_ACCOUNT_ERROR_MSG = "There is no account for ";
    private static final String BALANCE_ERROR_MSG = "From account balance need to be equal or more than transaction amount!";
    private static final String FROM_ACCOUNT_PROPERTY_NODE = "request.fromAccountId";
    private static final String TO_ACCOUNT_PROPERTY_NODE = "request.toAccountId";
    private static final String AMOUNT_PROPERTY_NODE = "request.amount";

    @Override
    public boolean isValid(TransactionRequest request, ConstraintValidatorContext context) {
        Account fromAccount = accountService.findEntityById(request.getFromAccountId());
        Account toAccount = accountService.findEntityById(request.getToAccountId());

        return isNotNullValue(fromAccount, context, FROM_ACCOUNT_PROPERTY_NODE) &&
                isNotNullValue(toAccount, context, TO_ACCOUNT_PROPERTY_NODE) &&
                isBalanceEnough(fromAccount, request.getAmount(), context, AMOUNT_PROPERTY_NODE);
    }

    private boolean isBalanceEnough(Account fromAccount, BigDecimal amount, ConstraintValidatorContext context, String propertyNode) {
        if (fromAccount != null && fromAccount.getBalance().compareTo(amount) < 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(BALANCE_ERROR_MSG)
                    .addPropertyNode(propertyNode)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }


    private boolean isNotNullValue(Object value, ConstraintValidatorContext context, String propertyNode) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format(NOT_NULL_ACCOUNT_ERROR_MSG, propertyNode))
                    .addPropertyNode(propertyNode)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
