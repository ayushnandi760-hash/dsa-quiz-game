package com.quizgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private QuestionBank questionBank;
    private LeaderboardManager leaderboardManager;
    private ScoreManager scoreManager;
    private QuizManager quizManager;
    private TimerManager timerManager;

    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JLabel timerLabel;
    private JLabel scoreLabel;
    private JLabel topicLabel;
    private JLabel difficultyLabel;
    private JLabel explanationLabel;
    private JButton submitButton;
    private JButton nextButton;

    private Question currentQuestion;

    public Main() {
        setTitle("DSA Quiz Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load JSON file
        questionBank = new QuestionBank("questions.json");
        leaderboardManager = new LeaderboardManager();
        scoreManager = new ScoreManager(leaderboardManager);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createStartPanel(), "Start");
        mainPanel.add(createQuizPanel(), "Quiz");
        mainPanel.add(createResultPanel(), "Result");
        mainPanel.add(createLeaderboardPanel(), "Leaderboard");

        add(mainPanel);
        cardLayout.show(mainPanel, "Start");
    }

    private JPanel createStartPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Professional DSA Quiz");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(25, 25, 112));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel diffLabel = new JLabel("Select Difficulty:");
        diffLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(diffLabel, gbc);

        String[] difficulties = {"Easy", "Medium", "Hard"};
        JComboBox<String> diffCombo = new JComboBox<>(difficulties);
        diffCombo.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        panel.add(diffCombo, gbc);

        JButton startBtn = new JButton("Start Quiz");
        startBtn.setFont(new Font("Arial", Font.BOLD, 18));
        startBtn.setBackground(new Color(60, 179, 113));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        startBtn.addActionListener(e -> {
            String selectedDiff = (String) diffCombo.getSelectedItem();
            startQuiz(selectedDiff);
        });

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(startBtn, gbc);

        JButton leaderboardBtn = new JButton("View Leaderboard");
        leaderboardBtn.setFont(new Font("Arial", Font.BOLD, 18));
        leaderboardBtn.addActionListener(e -> showLeaderboard());
        gbc.gridy = 3;
        panel.add(leaderboardBtn, gbc);

        return panel;
    }

    private JPanel createQuizPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top info bar
        JPanel infoPanel = new JPanel(new GridLayout(1, 3));
        topicLabel = new JLabel("Topic: ");
        difficultyLabel = new JLabel("Difficulty: ");
        timerLabel = new JLabel("Time: 30", SwingConstants.RIGHT);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(Color.RED);

        infoPanel.add(topicLabel);
        infoPanel.add(difficultyLabel);
        infoPanel.add(timerLabel);
        panel.add(infoPanel, BorderLayout.NORTH);

        // Center Question and Options
        JPanel centerPanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel("Question text here");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 22));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Use HTML to wrap text if question is long
        centerPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton("Option " + (i + 1));
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        centerPanel.add(optionsPanel, BorderLayout.CENTER);

        explanationLabel = new JLabel("");
        explanationLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        explanationLabel.setForeground(new Color(0, 100, 0)); // Dark green
        explanationLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        centerPanel.add(explanationLabel, BorderLayout.SOUTH);

        panel.add(centerPanel, BorderLayout.CENTER);

        // Bottom bar
        JPanel bottomPanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitButton = new JButton("Submit Answer");
        nextButton = new JButton("Next Question");
        nextButton.setEnabled(false);

        submitButton.addActionListener(e -> submitAnswer());
        nextButton.addActionListener(e -> loadNextQuestion());

        buttonPanel.add(submitButton);
        buttonPanel.add(nextButton);

        bottomPanel.add(scoreLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Init timer
        timerManager = new TimerManager(30, 
            () -> timerLabel.setText("Time: " + timerManager.getSecondsLeft()), 
            this::handleTimeout
        );

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel resultTitle = new JLabel("Quiz Completed!");
        resultTitle.setFont(new Font("Arial", Font.BOLD, 32));
        gbc.gridy = 0; panel.add(resultTitle, gbc);

        JLabel finalScoreLabel = new JLabel("Your final score is: ");
        finalScoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        gbc.gridy = 1; panel.add(finalScoreLabel, gbc);
        
        // Give it a name to find it later
        finalScoreLabel.setName("FinalScoreLabel"); 

        JButton replayBtn = new JButton("Play Again");
        replayBtn.setFont(new Font("Arial", Font.BOLD, 18));
        replayBtn.addActionListener(e -> cardLayout.show(mainPanel, "Start"));
        gbc.gridy = 2; panel.add(replayBtn, gbc);

        JButton homeBtn = new JButton("Leaderboard");
        homeBtn.setFont(new Font("Arial", Font.BOLD, 18));
        homeBtn.addActionListener(e -> showLeaderboard());
        gbc.gridy = 3; panel.add(homeBtn, gbc);

        return panel;
    }

    private JPanel createLeaderboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Top 10 High Scores", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);

        JTextArea scoresArea = new JTextArea();
        scoresArea.setName("ScoresArea");
        scoresArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        scoresArea.setEditable(false);
        panel.add(new JScrollPane(scoresArea), BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Main Menu");
        backBtn.setFont(new Font("Arial", Font.BOLD, 18));
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Start"));
        panel.add(backBtn, BorderLayout.SOUTH);

        return panel;
    }

    private void startQuiz(String difficulty) {
        quizManager = new QuizManager(questionBank, difficulty, scoreManager);
        if (quizManager.getTotalQuestions() == 0) {
            JOptionPane.showMessageDialog(this, "No questions found for this difficulty. Please check questions.json");
            return;
        }
        cardLayout.show(mainPanel, "Quiz");
        scoreLabel.setText("Score: " + scoreManager.getCurrentScore());
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        if (quizManager.hasNextQuestion()) {
            currentQuestion = quizManager.getNextQuestion();
            questionLabel.setText("<html><body style='width: 500px'>" + 
                    "Q" + quizManager.getCurrentQuestionNumber() + ": " + 
                    currentQuestion.getQuestionText() + "</body></html>");
            
            List<String> options = currentQuestion.getOptions();
            optionsGroup.clearSelection();
            for (int i = 0; i < 4; i++) {
                if (i < options.size()) {
                    optionButtons[i].setText(options.get(i));
                    optionButtons[i].setVisible(true);
                    optionButtons[i].setForeground(Color.BLACK);
                    optionButtons[i].setEnabled(true);
                } else {
                    optionButtons[i].setVisible(false);
                }
            }

            topicLabel.setText("Topic: " + currentQuestion.getTopicName());
            difficultyLabel.setText("Difficulty: " + currentQuestion.getDifficultyLevel());
            explanationLabel.setText("");
            
            submitButton.setEnabled(true);
            nextButton.setEnabled(false);

            timerManager.resetTimer(30);
            timerManager.startTimer();
        } else {
            finishQuiz();
        }
    }

    private void submitAnswer() {
        timerManager.stopTimer();
        String selectedOption = null;
        for (JRadioButton rb : optionButtons) {
            if (rb.isSelected()) {
                selectedOption = rb.getText();
                break;
            }
        }

        if (selectedOption == null) {
            JOptionPane.showMessageDialog(this, "Please select an option!");
            timerManager.startTimer();
            return;
        }

        processAnswer(selectedOption);
    }

    private void handleTimeout() {
        JOptionPane.showMessageDialog(this, "Time's up!");
        processAnswer(null); // null means no answer
    }

    private void processAnswer(String selectedOption) {
        submitButton.setEnabled(false);
        nextButton.setEnabled(true);
        for (JRadioButton rb : optionButtons) {
            rb.setEnabled(false);
            if (rb.getText().equals(currentQuestion.getCorrectAnswer())) {
                rb.setForeground(new Color(0, 150, 0)); // Green for correct
            } else if (rb.isSelected() && !rb.getText().equals(currentQuestion.getCorrectAnswer())) {
                rb.setForeground(Color.RED); // Red for incorrect
            }
        }

        if (selectedOption != null && quizManager.checkAnswer(currentQuestion, selectedOption)) {
            explanationLabel.setText("<html><body style='width: 500px'>Correct! " + 
                    currentQuestion.getExplanation() + "</body></html>");
        } else {
            explanationLabel.setText("<html><body style='width: 500px; color: red'>Incorrect! The correct answer is: " + 
                    currentQuestion.getCorrectAnswer() + ". " + currentQuestion.getExplanation() + "</body></html>");
        }
        
        scoreLabel.setText("Score: " + scoreManager.getCurrentScore());
    }

    private void finishQuiz() {
        scoreManager.saveScore();
        
        // Find FinalScoreLabel
        JPanel resultPanel = (JPanel) mainPanel.getComponent(2);
        for (Component c : resultPanel.getComponents()) {
            if ("FinalScoreLabel".equals(c.getName())) {
                ((JLabel) c).setText("Your final score is: " + scoreManager.getCurrentScore() + " / " + (quizManager.getTotalQuestions() * 10));
            }
        }
        cardLayout.show(mainPanel, "Result");
    }

    private void showLeaderboard() {
        // Find ScoresArea
        JPanel lbPanel = (JPanel) mainPanel.getComponent(3);
        JTextArea scoresArea = null;
        for (Component c : lbPanel.getComponents()) {
            if (c instanceof JScrollPane) {
                scoresArea = (JTextArea) ((JScrollPane) c).getViewport().getView();
                break;
            }
        }
        
        if (scoresArea != null) {
            List<Integer> topScores = leaderboardManager.getTopScores();
            StringBuilder sb = new StringBuilder();
            sb.append("Rank\tScore\n");
            sb.append("----------------\n");
            for (int i = 0; i < topScores.size(); i++) {
                sb.append((i + 1)).append("\t").append(topScores.get(i)).append("\n");
            }
            scoresArea.setText(sb.toString());
        }

        cardLayout.show(mainPanel, "Leaderboard");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
