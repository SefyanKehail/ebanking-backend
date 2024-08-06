package app.services;

import app.dtos.CustomerDTO;
import app.entities.Customer;
import app.exceptions.CustomerNotFoundException;
import app.mappers.CustomerMapper;
import app.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @Override
    public CustomerDTO create(CustomerDTO customerDTO) {
        return customerMapper.fromCustomer(customerRepository.save(this.customerMapper.fromCustomerDTO(customerDTO)));
    }

    public Customer getById(Long customerId) throws CustomerNotFoundException {
        return this.customerRepository.findById(customerId).orElseThrow( () -> new CustomerNotFoundException("Customer with Id: " + customerId + " not found"));
    }

    @Override
    public CustomerDTO getDTOById(Long customerId) throws CustomerNotFoundException {
        return this.customerMapper.fromCustomer(this.getById(customerId));
    }

    @Override
    public List<CustomerDTO> getAll(){
        List<Customer>  customerList = customerRepository.findAll();
        return customerList.stream().map( customer -> customerMapper.fromCustomer(customer)).toList();
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer customer = customerMapper.fromCustomerDTO(customerDTO);
        return  customerMapper.fromCustomer(customerRepository.save(customer));
    }

    @Override
    public void delete(Long customerId){
        customerRepository.deleteById(customerId);
    }
}
