package app;

import app.dtos.CurrentAccountDTO;
import app.dtos.CustomerDTO;
import app.dtos.SavingAccountDTO;
import app.entities.*;
import app.enums.AccountStatus;
import app.enums.OperationType;
import app.exceptions.AccountNotFoundException;
import app.exceptions.CustomerNotFoundException;
import app.exceptions.InsufficientFundException;
import app.repositories.AccountRepository;
import app.repositories.CustomerRepository;
import app.repositories.OperationRepository;
import app.services.AccountService;
import app.services.CustomerService;
import app.services.OperationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }


//    @Bean
    CommandLineRunner commandLineRunner(
            CustomerService customerService,
            AccountService accountService
    ) {
        return args -> {

            Stream.of("Nisrin", "Adam", "Ahmed").forEach(
                    username -> {

                        CustomerDTO customerDTO = new CustomerDTO();
                        customerDTO.setName(username);
                        customerDTO.setEmail(username + "@" + "email.com");

                        customerService.create(customerDTO);


                        CurrentAccountDTO currentAccountDTO;
                        SavingAccountDTO savingAccountDTO;

                        try {

                            savingAccountDTO = accountService.createSaving(
                                    Math.round(Math.random() * 100000 * 100.00) / 100.00,
                                    customerDTO.getId(),
                                    5.2
                            );

                            currentAccountDTO = accountService.createCurrent(
                                    Math.round(Math.random() * 100000 * 100.00) / 100.00,
                                    customerDTO.getId(),
                                    8000
                            );

                            for (int i = 0; i < 5; i++) {
                                System.out.println(savingAccountDTO.getId());
                                System.out.println(currentAccountDTO.getId());

                                accountService.debit(
                                        savingAccountDTO.getId(),
                                        Math.round(Math.random() * 1000 * 100.00) / 100.00,
                                        "Debiting operation: "+i+1
                                );

                                accountService.credit(
                                        currentAccountDTO.getId(),
                                        Math.round(Math.random() * 1000 * 100.00) / 100.00,
                                        "Debiting operation: "+i+1
                                );
                            }

                        } catch (CustomerNotFoundException | AccountNotFoundException | InsufficientFundException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        };
    }
}
