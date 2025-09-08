package com.example.quizapp.service;

import com.example.quizapp.dto.QuestionDTO;
import com.example.quizapp.model.Category;
import com.example.quizapp.model.Question;
import com.example.quizapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public List<Question> getQuestionsByCategory(Long categoryId) {
        return questionRepository.findByCategoryId(categoryId);
    }

    public List<QuestionDTO> getAllQuestionDTOs() {
        return questionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public QuestionDTO convertToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setContent(question.getContent());
        dto.setOption1(question.getOption1());
        dto.setOption2(question.getOption2());
        dto.setOption3(question.getOption3());
        dto.setOption4(question.getOption4());
        dto.setAnswer(question.getAnswer());

        if (question.getCategory() != null) {
            dto.setCategoryId(question.getCategory().getId());
            dto.setCategoryName(question.getCategory().getCatName());
        }

        return dto;
    }

    public void deleteAll() {
        questionRepository.deleteAll();
    }

    public QuestionDTO getQuestionDTOById(Long id) {
        Question question = questionRepository.findById(id).orElse(null);
        if (question == null) {
            return null;
        }
        return convertToDTO(question);
    }

    public List<QuestionDTO> getQuestionDTOsByCategory(Long categoryId) {
        List<Question> questions = questionRepository.findByCategoryId(categoryId);
        return questions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean updateQuestionFromDTO(Long id, QuestionDTO dto) {
        Question existing = questionRepository.findById(id).orElse(null);
        if (existing == null) return false;

        existing.setContent(dto.getContent());
        existing.setOption1(dto.getOption1());
        existing.setOption2(dto.getOption2());
        existing.setOption3(dto.getOption3());
        existing.setOption4(dto.getOption4());
        existing.setAnswer(dto.getAnswer());

        // Optional: set category if needed
        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            existing.setCategory(category);
        }

        questionRepository.save(existing);
        return true;
    }

}
