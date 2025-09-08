package com.example.quizapp.controller;

import com.example.quizapp.dto.QuizRequestDTO;
import com.example.quizapp.dto.SubmitQuizDTO;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizRequestDTO dto) {
        Quiz quiz = quizService.createQuiz(dto);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Question>> generateQuiz(
            @RequestParam Long categoryId,
            @RequestParam int num
    ) {
        List<Question> questions = quizService.generateQuiz(categoryId, num);
        if (questions == null || questions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(questions);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.getQuiz(id);
        if (quiz == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitQuiz(@RequestBody SubmitQuizDTO submission) {
        int score = quizService.calculateScore(submission);
        if (score == -1) return ResponseEntity.badRequest().body("Quiz not found.");
        return ResponseEntity.ok("Score: " + score);
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }
}
