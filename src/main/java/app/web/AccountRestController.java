package app.web;

import app.dtos.AccountDTO;
import app.dtos.AccountHistoryDTO;
import app.dtos.OperationDTO;
import app.exceptions.AccountNotFoundException;
import app.services.AccountService;
import app.services.OperationService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AccountRestController {
    private AccountService accountService;
    private OperationService operationService;

    @GetMapping("/accounts/{id}")
    public AccountDTO get(@PathVariable(name = "id") String accountId) throws AccountNotFoundException {
        return accountService.getDTOById(accountId);
    }

    @GetMapping("/accounts")
    public List<AccountDTO> getAll() {
        return accountService.getAccountList();
    }


    @GetMapping("/accounts/{id}/operations")
    public List<OperationDTO> getOperationList(@PathVariable(name = "id") String accountId) {
        return this.operationService.getOperationList(accountId);
    }

    @GetMapping("/accounts/{id}/accountHistory")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable(name = "id") String accountId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) throws AccountNotFoundException {
        return this.operationService.getAccountHistory(accountId, pageNumber, pageSize);
    }
}
