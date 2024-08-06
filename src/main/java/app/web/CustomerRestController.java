package app.web;

import app.dtos.CustomerDTO;
import app.entities.Customer;
import app.exceptions.CustomerNotFoundException;
import app.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerRestController {
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return customerService.getAll();
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO get(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return customerService.getDTOById(customerId);
    }

    @PostMapping("/customers")
    public CustomerDTO create(@RequestBody CustomerDTO customerDTO) {
        return customerService.create(customerDTO);
    }

    @PutMapping("/customers/{id}")
    public CustomerDTO update(@PathVariable(name = "id") Long customerId, @RequestBody CustomerDTO customerDTO) {
        // probably add validation if the id doesn't exist
        customerDTO.setId(customerId);
        return customerService.update(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void delete(@PathVariable(name = "id") Long customerId){
        customerService.delete(customerId);
    }
}
