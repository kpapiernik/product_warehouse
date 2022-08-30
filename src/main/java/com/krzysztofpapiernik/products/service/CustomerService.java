package com.krzysztofpapiernik.products.service;

import com.krzysztofpapiernik.products.dto.CreateCustomerDto;
import com.krzysztofpapiernik.products.dto.GetCustomerDto;
import com.krzysztofpapiernik.products.model.Customer;
import com.krzysztofpapiernik.products.repository.CustomerRepository;
import com.krzysztofpapiernik.products.service.exception.CustomerServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public GetCustomerDto addCustomer(CreateCustomerDto createCustomerDto){
        createCustomerDto.validate();

        if(customerRepository.findCustomerByEmail(createCustomerDto.email()).isPresent()){
            throw new CustomerServiceException("Provided email is taken");
        }

        var customer = createCustomerDto.toCustomer();
        return customerRepository.save(customer).toGetCustomerDto();
    }

    public List<GetCustomerDto> getAll(){
        return customerRepository
                .findAll()
                .stream()
                .map(Customer::toGetCustomerDto)
                .toList();
    }

}
