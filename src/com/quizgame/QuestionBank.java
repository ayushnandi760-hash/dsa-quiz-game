package com.quizgame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class QuestionBank {
    private List<Question> allQuestions;

    public QuestionBank(String jsonFilePath) {
        allQuestions = new ArrayList<>();
        loadQuestionsFromJson(jsonFilePath);
    }

    private void loadQuestionsFromJson(String filePath) {
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonBuilder.append(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading questions file: " + e.getMessage());
        }

        String json = jsonBuilder.toString();
        parseJsonArray(json);
    }

    /**
     * A lightweight custom JSON parser to avoid external dependencies like Gson/Jackson.
     * Assumes a specific, well-formed JSON structure.
     */
    private void parseJsonArray(String json) {
        int depth = 0;
        int objStart = -1;
        boolean inQuotes = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
            }

            if (!inQuotes) {
                if (c == '{') {
                    if (depth == 0) objStart = i;
                    depth++;
                } else if (c == '}') {
                    depth--;
                    if (depth == 0 && objStart != -1) {
                        String objStr = json.substring(objStart + 1, i);
                        allQuestions.add(parseJsonObject(objStr));
                    }
                }
            }
        }
    }

    private Question parseJsonObject(String obj) {
        String question = extractValue(obj, "question");
        String answer = extractValue(obj, "answer");
        String explanation = extractValue(obj, "explanation");
        String topic = extractValue(obj, "topic");
        String difficulty = extractValue(obj, "difficulty");
        
        List<String> options = new ArrayList<>();
        String optionsStr = extractArray(obj, "options");
        if (!optionsStr.isEmpty()) {
            // Split by comma outside of quotes
            String[] opts = optionsStr.split("\",\\s*\"");
            for (String opt : opts) {
                options.add(opt.replace("\"", "").trim());
            }
        }

        return new Question(question, options, answer, explanation, topic, difficulty);
    }

    private String extractValue(String json, String key) {
        String regex = "\"" + key + "\"\\s*:\\s*\"(.*?)\"";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1).replace("\\\"", "\"").replace("\\n", "\n");
        }
        return "";
    }

    private String extractArray(String json, String key) {
        String regex = "\"" + key + "\"\\s*:\\s*\\[(.*?)\\]";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public List<Question> getQuestionsByDifficulty(String difficulty, int count) {
        List<Question> filtered = allQuestions.stream()
                .filter(q -> q.getDifficultyLevel().equalsIgnoreCase(difficulty))
                .collect(Collectors.toList());
        
        Random rand = new Random();
        for (int i = 0; i < filtered.size(); i++) {
            int randomIndexToSwap = rand.nextInt(filtered.size());
            Question temp = filtered.get(randomIndexToSwap);
            filtered.set(randomIndexToSwap, filtered.get(i));
            filtered.set(i, temp);
        }
        
        return filtered.stream().limit(count).collect(Collectors.toList());
    }
}
