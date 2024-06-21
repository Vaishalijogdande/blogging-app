package com.blogging_app.service;

import com.blogging_app.dto.CategoryDto;
import com.blogging_app.entity.Category;
import com.blogging_app.exception.ResourceNotFoundException;
import com.blogging_app.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryDto createCategory(CategoryDto categoryDto){

        Category cat = this.modelMapper.map(categoryDto, Category.class);
        Category addedCategory= this.categoryRepository.save(cat);
        return this.modelMapper.map(addedCategory, CategoryDto.class);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId){

        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category ", "categoryId ", categoryId));

        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory =  this.categoryRepository.save(cat);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);

    }

    public CategoryDto getCategoryById(Long categoryId){
       Category cat = this.categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category ", " CategoryId ", categoryId));
        return this.modelMapper.map(cat, CategoryDto.class);

    }

    public List<CategoryDto> getAllCategories(){
       List<Category> categories = this.categoryRepository.findAll();
       List<CategoryDto> catDtos = categories.stream().map((cat) ->
               this.modelMapper.map(cat, CategoryDto.class)).toList();

       return catDtos;
    }

    public String deleteCategory(Long categoryId){
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category ", "categoryId ", categoryId));

        this.categoryRepository.deleteById(categoryId);
        return "Category deleted Successfully!!!";
    }


}
