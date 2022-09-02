package com.krzysztofpapiernik.products.service;

import com.krzysztofpapiernik.products.dto.CreateCategoryDto;
import com.krzysztofpapiernik.products.dto.GetCategoryDto;
import com.krzysztofpapiernik.products.model.Category;
import com.krzysztofpapiernik.products.repository.CategoryRepository;
import com.krzysztofpapiernik.products.exception.CategoryServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public GetCategoryDto addCategory(CreateCategoryDto createCategoryDto){

        if(categoryRepository.findCategoryByName(createCategoryDto.name()).isPresent()){
            throw new CategoryServiceException(Map.of("name", "already exists"));
        }
        return categoryRepository
                .save(createCategoryDto.toCategory())
                .toGetCategoryDto();
    }

    public GetCategoryDto getCategory(Long id){

        return categoryRepository
                .findById(id)
                .map(Category::toGetCategoryDto)
                .orElseThrow(() -> new CategoryServiceException(Map.of("id", "Category with id: %s does not exist".formatted(id))));
    }

    public List<GetCategoryDto> getAll(){
        return categoryRepository
                .findAll()
                .stream()
                .map(Category::toGetCategoryDto)
                .toList();
    }

    public void deleteCategory(Long id){
        var category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new CategoryServiceException(Map.of("id", "Category with id: %s does not exist".formatted(id))));

        categoryRepository.delete(category);
    }
}
