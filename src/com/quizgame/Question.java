package com.quizgame;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> options;
    private String correctAnswer;
    private String explanation;
    private String topicName;
    private String difficultyLevel;

    public Question(String questionText, List<String> options, String correctAnswer, 
                    String explanation, String topicName, String difficultyLevel) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.topicName = topicName;
        this.difficultyLevel = difficultyLevel;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }
}
