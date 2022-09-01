package com.krzysztofpapiernik.products.service;

import com.krzysztofpapiernik.products.dto.CreateProducerDto;
import com.krzysztofpapiernik.products.dto.GetProducerDto;
import com.krzysztofpapiernik.products.exception.ProducerServiceException;
import com.krzysztofpapiernik.products.model.Producer;
import com.krzysztofpapiernik.products.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ProducerService {
    private final ProducerRepository producerRepository;

    public GetProducerDto addProducer(CreateProducerDto createProducerDto){
        if(producerRepository.findByName(createProducerDto.name()).isPresent()){
            throw new ProducerServiceException(Map.of("name", "already exists"));
        }
        return producerRepository
                .save(createProducerDto.toProducer())
                .toGetProducerDto();
    }

    public GetProducerDto getProducer(Long id){
        if(producerRepository.findById(id).isEmpty()){
            throw new ProducerServiceException(Map.of("id", "Producer with id: %s does not exist".formatted(id)));
        }
        return producerRepository
                .findById(id)
                .map(Producer::toGetProducerDto)
                .orElseThrow(() -> new ProducerServiceException(Map.of("id", "Cannot get Producer with id: %s".formatted(id))));
    }

    public List<GetProducerDto> getAll(){
        return producerRepository
                .findAll()
                .stream()
                .map(Producer::toGetProducerDto)
                .toList();
    }
}
