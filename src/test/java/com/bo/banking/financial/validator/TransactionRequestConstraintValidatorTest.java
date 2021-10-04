package com.bo.banking.financial.validator;

import com.bo.banking.financial.dto.TransactionRequest;
import com.bo.banking.financial.model.Account;
import com.bo.banking.financial.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static com.bo.banking.utils.RandomUtils.randomTransactionRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionRequestConstraintValidatorTest {
    @Mock
    private AccountService accountService;
    @Mock
    private ConstraintValidatorContext contextMock;
    @InjectMocks
    private TransactionRequestConstraintValidator validator;

    @Test
    public void isValid() {
        //given
        TransactionRequest request = randomTransactionRequest();
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(request.getAmount());
        when(accountService.findEntityById(request.getFromAccountId())).thenReturn(account);
        when(accountService.findEntityById(request.getToAccountId())).thenReturn(account);

        //when
        boolean result = validator.isValid(request, contextMock);

        //then
        assertThat(result).isTrue();
    }
}