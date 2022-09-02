package com.krzysztofpapiernik.products.controller;


import com.krzysztofpapiernik.products.controller.dto.ResponseDataDto;
import com.krzysztofpapiernik.products.dto.CreateProducerDto;
import com.krzysztofpapiernik.products.dto.GetProducerDto;
import com.krzysztofpapiernik.products.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producers")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDataDto<GetProducerDto> addProducer(@RequestBody CreateProducerDto createProducerDto){
        return new ResponseDataDto<>(producerService.addProducer(createProducerDto));
    }

    @GetMapping
    public ResponseDataDto<List<GetProducerDto>> getAllProducers(){
        return new ResponseDataDto<>(producerService.getAll());
    }
}
