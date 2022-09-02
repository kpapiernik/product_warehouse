package com.krzysztofpapiernik.products.controller;

import com.krzysztofpapiernik.products.controller.dto.ResponseDataDto;
import com.krzysztofpapiernik.products.dto.CreateCategoryDto;
import com.krzysztofpapiernik.products.dto.GetCategoryDto;
import com.krzysztofpapiernik.products.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDataDto<GetCategoryDto> addCategory(@RequestBody CreateCategoryDto createCategoryDto){
        return new ResponseDataDto<>(categoryService.addCategory(createCategoryDto));
    }

    @GetMapping
    public ResponseDataDto<List<GetCategoryDto>> getAllCategories(){
        return new ResponseDataDto<>(categoryService.getAll());
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long id){
        categoryService.deleteCategory(id);
    }

}
