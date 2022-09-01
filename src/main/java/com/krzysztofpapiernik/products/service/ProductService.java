package com.krzysztofpapiernik.products.service;

import com.krzysztofpapiernik.products.dto.CreateProductDto;
import com.krzysztofpapiernik.products.dto.GetProductDto;
import com.krzysztofpapiernik.products.exception.ProductServiceException;
import com.krzysztofpapiernik.products.model.Product;
import com.krzysztofpapiernik.products.repository.CategoryRepository;
import com.krzysztofpapiernik.products.repository.ProducerRepository;
import com.krzysztofpapiernik.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;

    public GetProductDto addProduct(CreateProductDto createProductDto){
        var name = createProductDto.name();
        var categoryId = createProductDto.categoryId();
        var producerId = createProductDto.producerId();
        if(productRepository.findByNameAndCategory_IdAndProducer_Id(name, categoryId, producerId).isPresent()){
            throw new ProductServiceException(Map.of("product", "already exists"));
        }

        var category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ProductServiceException(Map.of("product", "provided category id does not exist")));

        var producer = producerRepository
                .findById(producerId)
                .orElseThrow(() -> new ProductServiceException(Map.of("product", "provided producer id does not exist")));

        return productRepository
                .save(createProductDto
                        .toProduct()
                        .withCategory(category)
                        .withProducer(producer))
                .toGetProductDto();
    }

    public List<GetProductDto> getAll(){
        return productRepository
                .findAll()
                .stream()
                .map(Product::toGetProductDto)
                .toList();
    }

    public GetProductDto getProduct(Long id){
        return productRepository
                .findById(id)
                .map(Product::toGetProductDto)
                .orElseThrow(() -> new ProductServiceException(Map.of("id", "product with id: %s does not exist".formatted(id))));
    }

}
