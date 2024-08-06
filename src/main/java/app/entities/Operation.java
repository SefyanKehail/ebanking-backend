package app.entities;

import app.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate operationDate;
    private double amount;
    private String description;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    @ManyToOne
    private Account account;
}
