package com.example.booking.controller;

import com.example.booking.dtos.BookingDTO;
import com.example.booking.dtos.CategoryDTO;
import com.example.booking.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryservice;
    //get all
    @GetMapping("")
    public List<CategoryDTO> getAllCategory() {
        return categoryservice.getCategory();
    }
    //get by id working
    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Integer id){
        return  categoryservice.getCategoryById(id);
    }
    //edit
    @PatchMapping("")
    public CategoryDTO editCategory(@Valid @RequestBody CategoryDTO editcategorydto,@PathVariable Integer id){
        return categoryservice.editCategory(editcategorydto, id);
    }
}
