package com.example.booking.controller;

import com.example.booking.dtos.CategoryDTO;
import com.example.booking.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.booking.entities.Category;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;

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
    //create
    @PostMapping("")
    @PreAuthorize("hasAuthority('admin')")
    public Category create(@Valid @RequestBody CategoryDTO newCategory){
        return  categoryservice.create(newCategory);
    }
    //edit
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin','lecturer')")
    public CategoryDTO editCategory(HttpServletRequest request,@Valid @RequestBody CategoryDTO editcategorydto,@PathVariable Integer id){
        return categoryservice.editCategory(request,editcategorydto, id);
    }
    //delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public void deleteById(@PathVariable Integer id){
        categoryservice.deleteById(id);
    }
    //check permission in category
    @GetMapping("/{id}/permission")
    public ResponseEntity<?> checkPermission(HttpServletRequest request,@PathVariable Integer id) {
        return categoryservice.checkPermission(request,id);
    }
}
