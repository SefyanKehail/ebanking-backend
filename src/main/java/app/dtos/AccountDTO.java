package app.dtos;

import app.enums.AccountStatus;
import app.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor @Data
public class AccountDTO {
    private String id;
    private double balance;
    private LocalDate createdAt;
    private AccountStatus accountStatus;
    private CustomerDTO customerDTO;
    private AccountType accountType;
}
