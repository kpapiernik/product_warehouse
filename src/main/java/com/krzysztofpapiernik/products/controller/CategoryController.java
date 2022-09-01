package com.krzysztofpapiernik.products.controller;

import com.krzysztofpapiernik.products.controller.dto.ResponseDataDto;
import com.krzysztofpapiernik.products.dto.CreateCategoryDto;
import com.krzysztofpapiernik.products.dto.GetCategoryDto;
import com.krzysztofpapiernik.products.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseDataDto<GetCategoryDto> addCategory(@RequestBody CreateCategoryDto createCategoryDto){
        return new ResponseDataDto<>(categoryService.addCategory(createCategoryDto));
    }

}
