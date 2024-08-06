package app.services;

import app.dtos.AccountHistoryDTO;
import app.dtos.OperationDTO;
import app.entities.Operation;
import app.exceptions.AccountNotFoundException;
import app.mappers.OperationMapper;
import app.repositories.AccountRepository;
import app.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class OperationServiceImpl implements OperationService {
    private OperationRepository operationRepository;
    private OperationMapper operationMapper;
    private AccountRepository accountRepository;

    @Override
    public Operation create(Operation operation) {
        return this.operationRepository.save(operation);
    }

    @Override
    public List<OperationDTO> getOperationList(String accountId) {
        return this.operationRepository.findByAccountId(accountId).stream().map(
                (operation -> operationMapper.fromOperation(operation))
        ).toList();
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int pageNumber, int pageSize) throws AccountNotFoundException {
        Page<Operation> operationPage = this.operationRepository.findByAccountId(accountId, PageRequest.of(pageNumber, pageSize));
        List<OperationDTO> operationDTOList = operationPage.stream()
                .map((operation -> operationMapper.fromOperation(operation))).toList();
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        accountHistoryDTO.setAccountId(accountId);
        accountHistoryDTO.setBalance(accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException("Account with id:"+accountId+" was not found")
        ).getBalance());
        accountHistoryDTO.setOperationDTOList(operationDTOList);
        accountHistoryDTO.setCurrentPage(pageNumber);
        accountHistoryDTO.setPageSize(pageSize);
        accountHistoryDTO.setTotalPages(operationPage.getTotalPages());
        return accountHistoryDTO;
    }
}
