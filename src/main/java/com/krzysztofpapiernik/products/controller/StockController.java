package com.krzysztofpapiernik.products.controller;


import com.krzysztofpapiernik.products.controller.dto.ResponseDataDto;
import com.krzysztofpapiernik.products.dto.CreateStockPositionDto;
import com.krzysztofpapiernik.products.dto.GetPositionFromStockDto;
import com.krzysztofpapiernik.products.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDataDto<GetPositionFromStockDto> addStockPosition(@RequestBody CreateStockPositionDto createStockPositionDto){
        return new ResponseDataDto<>(stockService.addProductToStock(createStockPositionDto));
    }

    @GetMapping("/{productId}")
    public ResponseDataDto<GetPositionFromStockDto> getStockPositionQuantity(@PathVariable("productId") Long id){
        return new ResponseDataDto<>(stockService.getPositionFromStock(id));
    }
}
