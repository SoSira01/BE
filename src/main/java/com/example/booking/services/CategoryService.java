package com.example.booking.services;

import com.example.booking.dtos.CategoryDTO;
import com.example.booking.entities.Category;
import com.example.booking.entities.CategoryOwner;
import com.example.booking.entities.User;
import com.example.booking.Jwt.JwtTokenUtil;
import com.example.booking.repositories.CategoryOwnerRepository;
import com.example.booking.repositories.CategoryRepository;
import com.example.booking.repositories.UserRepository;
import com.example.booking.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryOwnerRepository ownerRepository;

    //get all
    public List<CategoryDTO> getCategory () {
        List<Category> category = repository.findAll();
        return listMapper.mapList(category, CategoryDTO.class, modelMapper);
    }

    //get booking by id
    public CategoryDTO getCategoryById(Integer categoryId) {
        Category category = repository.findById(categoryId)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "category id "+ categoryId+
                        "Does Not Exist !!!"
                ));
        return modelMapper.map(category, CategoryDTO.class);
    }
    //Create
    public Category create(CategoryDTO newCategory) {
        Category category = modelMapper.map(newCategory, Category.class);
        return repository.saveAndFlush(category);
    }
    //Edit
    public CategoryDTO editCategory(HttpServletRequest request,CategoryDTO editcategorydto, Integer id){
        Boolean condition = false;
        String requestToken = request.getHeader("Authorization").substring(7);
        String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
        User user = userRepository.findByEmail(userName);
        if (user.getRole().name() == "lecturer") {
            List<CategoryOwner> ownerList = ownerRepository.findByUser_Name(user.getName());
            for (CategoryOwner owner : ownerList) {
                System.out.println(owner.getEventCategory().getId());
                System.out.println(id);
                if(owner.getEventCategory().getId() == id) { condition = true; }
            }
        } else if (user.getRole().name() == "admin") { condition = true; }
        else { condition = false; }
        if(condition) {
            Category category = modelMapper.map(editcategorydto, Category.class);
            Category ct = repository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Booking id" + id +
                            "Not found ID to Edit"
                    ));
            ct.setCategoryName(category.getCategoryName());
            ct.setDuration(category.getDuration());
            if(category.getCategoryDescription() != null){
                System.out.println(category.getCategoryDescription());
                if(category.getCategoryDescription() != "No description") {
                    ct.setCategoryDescription(category.getCategoryDescription());
                }
                if (category.getCategoryDescription() == "No description") {
                    ct.setCategoryDescription(null);
                }
            }

            repository.saveAndFlush(ct);
            return modelMapper.map(ct,CategoryDTO.class);
        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    //delete category
    public void deleteById(Integer id) {
        repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + "Not Found ID To Delete"));
        repository.deleteById(id);
    }
    //check lecturer to get edit
    public ResponseEntity<?> checkPermission(HttpServletRequest request, Integer cateId) {
        Boolean condition = false;
        String requestToken = request.getHeader("Authorization").substring(7);
        String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
        User user = userRepository.findByEmail(userName);
        if (user.getRole().name() == "lecturer") {
            List<CategoryOwner> ownerList = ownerRepository.findByUser_Name(user.getName());
            for (CategoryOwner owner : ownerList) {
                System.out.println(owner.getEventCategory().getId());
                System.out.println(cateId);
                if(owner.getEventCategory().getId() == cateId) {
                    condition = true;
                }
            }
        }
        else if (user.getRole().name() == "admin") {
            condition = true;
        } else {
            condition = false;
        }
        if(condition) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
