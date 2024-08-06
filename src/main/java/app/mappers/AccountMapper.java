package app.mappers;

import app.dtos.AccountDTO;
import app.dtos.CurrentAccountDTO;
import app.dtos.SavingAccountDTO;
import app.entities.Account;
import app.entities.CurrentAccount;
import app.entities.SavingAccount;
import app.enums.AccountType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountMapper {

    private CustomerMapper customerMapper;
    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
        // since CurrentAccount has a Customer object not a CustomerDTO we need to set it manually
        currentAccountDTO.setCustomerDTO(customerMapper.fromCustomer(currentAccount.getCustomer()));
        currentAccountDTO.setAccountType(AccountType.CURRENT);
        return currentAccountDTO;
    }

    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        currentAccount.setCustomer(customerMapper.fromCustomerDTO(currentAccountDTO.getCustomerDTO()));
        return currentAccount;
    }

    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount){
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingAccountDTO);
        savingAccountDTO.setCustomerDTO(customerMapper.fromCustomer(savingAccount.getCustomer()));
        savingAccountDTO.setAccountType(AccountType.SAVING);
        return savingAccountDTO;
    }

    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
        savingAccount.setCustomer(customerMapper.fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

//    public AccountDTO fromAccount(Account account){
//        AccountDTO accountDTO = new AccountDTO();
//        BeanUtils.copyProperties(account, accountDTO);
//        accountDTO.setCustomerDTO(customerMapper.fromCustomer(account.getCustomer()));
//        return accountDTO;
//    }
//
//    public Account fromAccountDTO(AccountDTO accountDTO){
//        Account account = new Account();
//        BeanUtils.copyProperties(accountDTO, account);
//        account.setCustomer(customerMapper.fromCustomerDTO(accountDTO.getCustomerDTO()));
//        return account;
//    }
}
