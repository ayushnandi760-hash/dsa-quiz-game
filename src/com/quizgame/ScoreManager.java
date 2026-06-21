package com.quizgame;

public class ScoreManager {
    private int currentScore;
    private LeaderboardManager leaderboardManager;

    public ScoreManager(LeaderboardManager leaderboardManager) {
        this.leaderboardManager = leaderboardManager;
        this.currentScore = 0;
    }

    public void addPoints(int points) {
        this.currentScore += points;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void resetScore() {
        this.currentScore = 0;
    }

    public void saveScore() {
        leaderboardManager.addScore(currentScore);
    }
}
