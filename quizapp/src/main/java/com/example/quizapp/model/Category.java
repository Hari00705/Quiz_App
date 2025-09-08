package com.example.quizapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity

public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String catName;

        @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
        @JsonIgnoreProperties("category")
        private List<Question> questions;


        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Category() {
        }

        public Category(Long id, String catName, List<Question> questions) {
                this.id = id;
                this.catName = catName;
                this.questions = questions;
        }

        public String getCatName() {
                return catName;
        }

        public void setCatName(String catName) {
                this.catName = catName;
        }

        public List<Question> getQuestions() {
                return questions;
        }

        public void setQuestions(List<Question> questions) {
                this.questions = questions;
        }
}

