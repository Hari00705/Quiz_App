package com.example.quizapp.controller;

import com.example.quizapp.dto.CategoryDTO;
import com.example.quizapp.model.Category;
import com.example.quizapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // ✅ Add Category via DTO
    @PostMapping("/dto")
    public ResponseEntity<String> addCategoryDTO(@RequestBody CategoryDTO dto) {
        categoryService.addCategoryFromDTO(dto);
        return ResponseEntity.ok("Category added successfully (via DTO)");
    }

    // ✅ Get All Categories as DTOs
    @GetMapping("/dto")
    public ResponseEntity<List<CategoryDTO>> getAllCategoryDTOs() {
        List<CategoryDTO> dtos = categoryService.getAllCategoryDTOs();
        return ResponseEntity.ok(dtos);
    }

    // ✅ Update Category via DTO
    @PutMapping("/dto/{id}")
    public ResponseEntity<String> updateCategoryDTO(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        boolean updated = categoryService.updateCategoryFromDTO(id, dto);
        if (updated) {
            return ResponseEntity.ok("Category updated successfully (via DTO)");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔁 Entity-based Endpoints
    @PostMapping
    public String addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return "Category added successfully";
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/del")
    public String deleteAll() {
        categoryService.deleteAll();
        return "All categories deleted";
    }
}
