package com.example.quizapp.service;

import com.example.quizapp.dto.CategoryDTO;
import com.example.quizapp.model.Category;
import com.example.quizapp.model.Question;
import com.example.quizapp.repository.CategoryRepository;
import com.example.quizapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // Add category from DTO
    public void addCategoryFromDTO(CategoryDTO dto) {
        Category category = new Category();
        category.setCatName(dto.getCatName());

        if (dto.getQuestionIds() != null) {
            List<Question> questions = questionRepository.findAllById(dto.getQuestionIds());
            category.setQuestions(questions);
        }

        categoryRepository.save(category);
    }

    // Update category from DTO
    public boolean updateCategoryFromDTO(Long id, CategoryDTO dto) {
        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCatName(dto.getCatName());

            if (dto.getQuestionIds() != null) {
                List<Question> questions = questionRepository.findAllById(dto.getQuestionIds());
                existing.setQuestions(questions);
            }

            categoryRepository.save(existing);
            return true;
        }
        return false;
    }

    // Get all categories as DTOs
    public List<CategoryDTO> getAllCategoryDTOs() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new ArrayList<>();

        for (Category category : categories) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(category.getId());
            dto.setCatName(category.getCatName());

            List<Long> questionIds = new ArrayList<>();
            if (category.getQuestions() != null) {
                for (Question q : category.getQuestions()) {
                    questionIds.add(q.getId());
                }
            }
            dto.setQuestionIds(questionIds);
            dtoList.add(dto);
        }

        return dtoList;
    }

    // Original entity-based methods
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCatName(updatedCategory.getCatName());
            return categoryRepository.save(existing);
        }
        return null;
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
