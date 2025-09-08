package com.example.quizapp.service;

import com.example.quizapp.dto.QuizRequestDTO;
import com.example.quizapp.dto.SubmitQuizDTO;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Quiz createQuiz(QuizRequestDTO dto) {
        List<Question> questions = questionRepository.findByCategoryId(dto.getCategoryId());

        Collections.shuffle(questions);
        List<Question> selected = questions.stream()
                .limit(dto.getNumQuestions())
                .toList();

        Quiz quiz = new Quiz();
        quiz.setTitle(dto.getTitle());
        quiz.setQuestions(selected);
        return quizRepository.save(quiz);
    }

    public Quiz getQuiz(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    public int calculateScore(SubmitQuizDTO submission) {
        Optional<Quiz> quizOpt = quizRepository.findById(submission.getQuizId());
        if (quizOpt.isEmpty()) return -1;

        int score = 0;
        for (Question q : quizOpt.get().getQuestions()) {
            String correct = q.getAnswer().trim().toLowerCase();
            String given = submission.getAnswers().getOrDefault(q.getId(), "").trim().toLowerCase();
            if (correct.equals(given)) {
                score++;
            }
        }
        return score;
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public List<Question> generateQuiz(Long categoryId, int num) {
        List<Question> allQuestions = questionRepository.findByCategoryId(categoryId);

        if (allQuestions.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.shuffle(allQuestions);

        if (num >= allQuestions.size()) {
            return allQuestions;
        }

        return allQuestions.subList(0, num);
    }

}
