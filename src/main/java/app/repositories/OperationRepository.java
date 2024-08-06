package app.repositories;

import app.dtos.AccountHistoryDTO;
import app.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByAccountId(String accountId);

    Page<Operation> findByAccountId(String accountId, Pageable pageable);
}
