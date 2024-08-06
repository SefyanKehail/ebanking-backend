package app.services;

import app.dtos.AccountHistoryDTO;
import app.dtos.OperationDTO;
import app.entities.Operation;
import app.exceptions.AccountNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OperationService {

    Operation create(Operation operation);

    List<OperationDTO> getOperationList(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int pageNumber, int pageSize) throws AccountNotFoundException;
}
