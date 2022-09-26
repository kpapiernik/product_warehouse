package com.krzysztofpapiernik.products.controller;


import com.krzysztofpapiernik.products.controller.dto.ResponseDataDto;
import com.krzysztofpapiernik.products.dto.CreateProductDto;
import com.krzysztofpapiernik.products.dto.GetProductDto;
import com.krzysztofpapiernik.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDataDto<GetProductDto> addProduct(@RequestBody CreateProductDto createProductDto){
        return new ResponseDataDto<>(productService.addProduct(createProductDto));
    }

    @GetMapping
    public ResponseDataDto<List<GetProductDto>> gettAllProducts(){
        return new ResponseDataDto<>(productService.getAll());
    }

    @GetMapping("/{productId}")
    public ResponseDataDto<GetProductDto> getProduct(@PathVariable("productId") Long id){
        return new ResponseDataDto<>(productService.getProduct(id));
    }
}
