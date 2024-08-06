package app.services;

import app.dtos.AccountDTO;
import app.dtos.CurrentAccountDTO;
import app.dtos.SavingAccountDTO;
import app.entities.*;
import app.enums.AccountStatus;
import app.enums.OperationType;
import app.exceptions.AccountNotFoundException;
import app.exceptions.CustomerNotFoundException;
import app.exceptions.InsufficientFundException;
import app.mappers.AccountMapper;
import app.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private CustomerService customerService;
    private OperationService operationService;
    private AccountMapper accountMapper;

    @Override
    public CurrentAccountDTO createCurrent(
            double initialBalance, Long customerId, double overDraft
    ) throws CustomerNotFoundException {

        log.info("creating a new current account ");

        Customer customer = this.customerService.getById(customerId);

        CurrentAccount currentAccount = new CurrentAccount();

        currentAccount.setCreatedAt(LocalDate.now());
        currentAccount.setAccountStatus(AccountStatus.CREATED);
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);

        accountRepository.save(currentAccount);
        return accountMapper.fromCurrentAccount(currentAccount);
    }

    @Override
    public SavingAccountDTO createSaving(
            double initialBalance, Long customerId, double interestRate
    ) throws CustomerNotFoundException {
        log.info("creating a new saving account ");

        Customer customer = this.customerService.getById(customerId);

        SavingAccount savingAccount = new SavingAccount();

        savingAccount.setCreatedAt(LocalDate.now());
        savingAccount.setAccountStatus(AccountStatus.CREATED);
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);

        accountRepository.save(savingAccount);
        return accountMapper.fromSavingAccount(savingAccount);
    }

    @Override
    public AccountDTO getDTOById(String accountId) throws AccountNotFoundException {
        Account account = this.accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException("Account with Id: " + accountId + " not found"));
        if (account instanceof CurrentAccount) {
            return accountMapper.fromCurrentAccount((CurrentAccount) account);
        }else {
            return accountMapper.fromSavingAccount((SavingAccount) account);
        }
    }

    public Account getById(String accountId) throws AccountNotFoundException {
        return this.accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException("Account with Id: " + accountId + " not found"));
    }

    @Override
    public void debit(
            String accountId, double amount, String description
    ) throws AccountNotFoundException, InsufficientFundException {
        Account account = this.getById(accountId);
        // Todo: add overDraft support, and rules debit control for savings accounts
        if (account.getBalance() < amount) {
            throw new InsufficientFundException("Insufficient funds");
        }

        // create and save an operation
        Operation operation = new Operation();

        operation.setAccount(account);
        operation.setOperationType(OperationType.DEBIT);
        operation.setAmount(amount);
        operation.setOperationDate(LocalDate.now());
        operation.setDescription(!description.isEmpty() ? description : "Debit operation");

        operationService.create(operation);

        // update balance and save account
        account.setBalance(Math.round((account.getBalance() - amount) * 100.00) / 100.00);
        accountRepository.save(account);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws AccountNotFoundException {
        Account account = this.getById(accountId);

        // Todo: add interest support

        // create and save an operation
        Operation operation = new Operation();

        operation.setAccount(account);
        operation.setOperationType(OperationType.CREDIT);
        operation.setAmount(amount);
        operation.setOperationDate(LocalDate.now());
        operation.setDescription(!description.isEmpty() ? description : "Credit operation");

        operationService.create(operation);

        // update balance and save account
        account.setBalance(Math.round((account.getBalance() + amount) * 100.00) / 100.00);
        accountRepository.save(account);
    }

    @Override
    public void transfer(
            String sourceAccountId, String destinationAccountId, double amount, String description
    ) throws AccountNotFoundException, InsufficientFundException {
        this.debit(sourceAccountId, amount, "Transfer from: " + sourceAccountId);
        this.credit(destinationAccountId, amount, "Transfer to: " + destinationAccountId);
    }

    @Override
    public List<AccountDTO> getAccountList(){
        return accountRepository.findAll().stream().map(
                account -> {
                    if (account instanceof CurrentAccount) {
                        return accountMapper.fromCurrentAccount((CurrentAccount) account);
                    }else {
                        return accountMapper.fromSavingAccount((SavingAccount) account);
                    }                }
        ).toList();
    }
}
