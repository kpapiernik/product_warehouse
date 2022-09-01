package com.krzysztofpapiernik.products.service;

import com.krzysztofpapiernik.products.dto.CreateCustomerDto;
import com.krzysztofpapiernik.products.dto.GetCustomerDto;
import com.krzysztofpapiernik.products.model.Customer;
import com.krzysztofpapiernik.products.repository.CustomerRepository;
import com.krzysztofpapiernik.products.exception.CustomerServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public GetCustomerDto addCustomer(CreateCustomerDto createCustomerDto){

        if(customerRepository.findCustomerByEmail(createCustomerDto.email()).isPresent()){
            throw new CustomerServiceException("Provided email is taken");
        }

        return customerRepository
                .save(createCustomerDto.toCustomer())
                .toGetCustomerDto();
    }

    public List<GetCustomerDto> getAll(){
        return customerRepository
                .findAll()
                .stream()
                .map(Customer::toGetCustomerDto)
                .toList();
    }

    public GetCustomerDto getCustomerById(Long id){
        if(customerRepository.findById(id).isEmpty()){
            throw new CustomerServiceException(Map.of("id", "User with id: %s does not exist".formatted(id)));
        }
        return customerRepository
                .findById(id)
                .map(Customer::toGetCustomerDto)
                .orElseThrow(() -> new CustomerServiceException(Map.of("id", "Cannot get user with id: %s".formatted(id))));
    }

}
