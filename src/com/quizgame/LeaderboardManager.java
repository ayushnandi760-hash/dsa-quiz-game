package com.quizgame;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardManager {
    private static final String LEADERBOARD_FILE = "leaderboard.txt";
    private List<Integer> topScores;

    public LeaderboardManager() {
        topScores = new ArrayList<>();
        loadScores();
    }

    private void loadScores() {
        File file = new File(LEADERBOARD_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    topScores.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    // Ignore invalid lines
                }
            }
            Collections.sort(topScores, Collections.reverseOrder());
        } catch (IOException e) {
            System.err.println("Error reading leaderboard: " + e.getMessage());
        }
    }

    public void addScore(int score) {
        topScores.add(score);
        Collections.sort(topScores, Collections.reverseOrder());
        
        // Keep only top 10 scores
        if (topScores.size() > 10) {
            topScores = topScores.subList(0, 10);
        }
        
        saveScores();
    }

    private void saveScores() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LEADERBOARD_FILE))) {
            for (int score : topScores) {
                pw.println(score);
            }
        } catch (IOException e) {
            System.err.println("Error writing leaderboard: " + e.getMessage());
        }
    }

    public List<Integer> getTopScores() {
        return topScores;
    }

    public int getHighScore() {
        return topScores.isEmpty() ? 0 : topScores.get(0);
    }
}
