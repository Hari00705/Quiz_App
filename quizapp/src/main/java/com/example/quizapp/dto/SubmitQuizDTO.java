package com.example.quizapp.dto;

import java.util.Map;

public class SubmitQuizDTO {
    private Long quizId;
    private Map<Long, String> answers; // Map<QuestionId, GivenAnswer>

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers;
    }
}
