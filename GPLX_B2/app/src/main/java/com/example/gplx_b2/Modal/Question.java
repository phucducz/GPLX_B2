package com.example.gplx_b2.Modal;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private String question;
    private List<String> answerList;
    private String answerCorrect;

    public Question() {}

    public Question(String question, List<String> answerList, String answerCorrect) {
        this.question = question;
        this.answerList = answerList;
        this.answerCorrect = answerCorrect;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public String getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(String answerCorrect) {
        this.answerCorrect = answerCorrect;
    }
}
