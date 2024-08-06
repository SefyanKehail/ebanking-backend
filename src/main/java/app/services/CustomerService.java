package app.services;

import app.dtos.CustomerDTO;
import app.entities.Customer;
import app.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    CustomerDTO create(CustomerDTO customerDTO);
    Customer getById(Long customerId) throws CustomerNotFoundException;

    CustomerDTO getDTOById(Long customerId) throws CustomerNotFoundException;

    List<CustomerDTO> getAll();

    CustomerDTO update(CustomerDTO customerDTO);

    void delete(Long customerId);
}
