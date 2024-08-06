package app.services;

import app.dtos.AccountDTO;
import app.dtos.CurrentAccountDTO;
import app.dtos.SavingAccountDTO;
import app.entities.Account;
import app.entities.CurrentAccount;
import app.entities.SavingAccount;
import app.exceptions.AccountNotFoundException;
import app.exceptions.CustomerNotFoundException;
import app.exceptions.InsufficientFundException;

import java.util.Currency;
import java.util.List;

public interface AccountService {
    CurrentAccountDTO createCurrent(
            double initialBalance,
            Long customerId,
            double overDraft
    ) throws CustomerNotFoundException;

    SavingAccountDTO createSaving(
            double initialBalance,
            Long customerId,
            double interestRate
    ) throws CustomerNotFoundException;


    AccountDTO getDTOById(String accountId) throws AccountNotFoundException;

    Account getById(String accountId) throws AccountNotFoundException;


    void debit(String accountId, double amount, String description) throws AccountNotFoundException, InsufficientFundException;

    void credit(String accountId, double amount, String description) throws AccountNotFoundException;

    void transfer(String sourceAccountId, String destinationAccountId, double amount, String description) throws AccountNotFoundException, InsufficientFundException;

    List<AccountDTO> getAccountList();
}
