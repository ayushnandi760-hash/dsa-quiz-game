package com.quizgame;

import java.util.List;

public class QuizManager {
    private List<Question> currentRoundQuestions;
    private int currentQuestionIndex;
    private ScoreManager scoreManager;
    private static final int QUESTIONS_PER_ROUND = 10;
    private static final int POINTS_PER_QUESTION = 10;

    public QuizManager(QuestionBank questionBank, String difficulty, ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
        this.currentRoundQuestions = questionBank.getQuestionsByDifficulty(difficulty, QUESTIONS_PER_ROUND);
        this.currentQuestionIndex = 0;
        this.scoreManager.resetScore();
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < currentRoundQuestions.size();
    }

    public Question getNextQuestion() {
        if (hasNextQuestion()) {
            return currentRoundQuestions.get(currentQuestionIndex++);
        }
        return null;
    }

    public boolean checkAnswer(Question question, String selectedOption) {
        if (question.getCorrectAnswer().equalsIgnoreCase(selectedOption)) {
            scoreManager.addPoints(POINTS_PER_QUESTION);
            return true;
        }
        return false;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionIndex; // Since it's incremented when fetching
    }

    public int getTotalQuestions() {
        return currentRoundQuestions.size();
    }
}
