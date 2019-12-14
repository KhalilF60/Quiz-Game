package com.example.quizgame;

import java.util.List;

public class TriviaResponse {
    int response_code;
    List<QuizQuestion> results = null;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public List<QuizQuestion> getResults() {
        return results;
    }

    public void setResults(List<QuizQuestion> results) {
        this.results = results;
    }
}
