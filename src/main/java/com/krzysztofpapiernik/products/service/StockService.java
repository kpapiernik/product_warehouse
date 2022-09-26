package com.krzysztofpapiernik.products.service;

import com.krzysztofpapiernik.products.dto.CreateStockPositionDto;
import com.krzysztofpapiernik.products.dto.GetPositionFromStockDto;
import com.krzysztofpapiernik.products.dto.UpdateStockPositionDto;
import com.krzysztofpapiernik.products.exception.StockServiceException;
import com.krzysztofpapiernik.products.model.Stock;
import com.krzysztofpapiernik.products.repository.ProductRepository;
import com.krzysztofpapiernik.products.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public GetPositionFromStockDto addProductToStock(CreateStockPositionDto createStockPositionDto){
        var productId = createStockPositionDto.productId();
        var product = productRepository
                .findById(productId)
                .orElseThrow(() -> new StockServiceException(Map.of("product", "does not exist")));

        if(stockRepository.findByProductId(productId).isPresent()){
            throw new StockServiceException(Map.of("product", "already exists"));
        }

        return stockRepository
                .save(createStockPositionDto
                        .toStock()
                        .withProduct(product))
                .toGetPositionFromStockDto();
    }

    public GetPositionFromStockDto increaseStockPositionQuantity(UpdateStockPositionDto updateStockPositionDto){
        var productId = updateStockPositionDto.productId();
        var quantity = updateStockPositionDto.quantity();

        if(productRepository.findById(productId).isEmpty()){
            throw new StockServiceException(Map.of("product", "does not exist"));
        }
        return stockRepository
                .save(updateStockPositionDto
                        .toStock()
                        .withQuantityAdded(quantity))
                .toGetPositionFromStockDto();
    }

    public GetPositionFromStockDto decreaseStockPositionQuantity(Long productId, Integer quantity){

//        if(productRepository.findById(productId).isEmpty()){
//            throw new StockServiceException(Map.of("product", "does not exist"));
//        }
        var productOptional = productRepository.findById(productId);

        var stock = stockRepository
                .findByProductId(productId)
                .orElseThrow(() -> new StockServiceException(Map.of("product", "doesnot exist")));

        if(productOptional.isEmpty()){
            throw new StockServiceException(Map.of("product", "does not exist"));
        }
        if (stock.hasQuantityLessThanDemanded(quantity)) {
                throw new StockServiceException(Map.of("quantity", "product quantity is less than demanded"));
        }

        var updatedQuantity = stock.getQuantity() - quantity;

        stockRepository.updateStockQuantity(productOptional.get(), updatedQuantity);

        return stock.withQuantitySubtracted(quantity).toGetPositionFromStockDto();
    }

    public GetPositionFromStockDto getPositionFromStock(Long productId){

        if(productRepository.findById(productId).isEmpty()){
            throw new StockServiceException(Map.of("product", "does not exist"));
        }

        var stock = stockRepository
                .findByProductId(productId)
                .orElseThrow(() -> new StockServiceException(Map.of("product", "doesnot exist")));

        return stock.toGetPositionFromStockDto();
    }
}
