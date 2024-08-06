package app.dtos;


import app.entities.Account;
import app.enums.OperationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationDTO {
    private Long id;
    private LocalDate operationDate;
    private double amount;
    private String description;
    private OperationType operationType;
}
