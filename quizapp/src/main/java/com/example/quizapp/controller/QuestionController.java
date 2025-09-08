// QuestionController.java
package com.example.quizapp.controller;
import com.example.quizapp.dto.QuestionDTO;
import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping()
    public String addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
        return "Question added successfully";
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestion();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionDTOById(@PathVariable Long id) {
        QuestionDTO dto = questionService.getQuestionDTOById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsDTOByCategory(@PathVariable Long categoryId) {
        List<QuestionDTO> dtos = questionService.getQuestionDTOsByCategory(categoryId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionDTO>> getAllQuestionsDTO() {
        List<QuestionDTO> dtoList = questionService.getAllQuestionDTOs();
        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("/del")
        public String deleteAll(){
            questionService.deleteAll();
            return "Deleted";
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO dto) {
        boolean updated = questionService.updateQuestionFromDTO(id, dto);
        if (updated) {
            return ResponseEntity.ok("Question updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
