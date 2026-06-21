# Professional DSA Quiz Game

A robust, Java-based Desktop Application for students preparing for coding interviews, aptitude tests, and placement exams. This game features a clean Graphical User Interface (GUI) built with Java Swing and allows users to practice Data Structures and Algorithms questions across different difficulty levels.

## Features

- **Difficulty Selection:** Choose between Easy, Medium, and Hard difficulty levels.
- **Random Question Selection:** Each round consists of 10 randomly selected, unique questions from the question bank.
- **Timer:** A 30-second countdown timer for each question to simulate a real exam environment.
- **Score Tracking:** Earn 10 points for every correct answer.
- **Leaderboard:** Tracks and displays the top 10 high scores across all sessions.
- **Replay Option:** Allows users to play again without restarting the application.
- **Detailed Explanations:** Get immediate feedback and a detailed explanation after answering each question.
- **Custom JSON Parser:** Uses a built-in, lightweight JSON parser (No external dependencies like Maven/Gradle or Gson required).

## Architecture

The project follows clean Object-Oriented Programming (OOP) principles with the following core classes:
- `Question`: POJO representing a single question.
- `QuestionBank`: Handles loading and parsing questions from the JSON file.
- `QuizManager`: Manages the logic, round generation, and answer validation.
- `TimerManager`: A robust wrapper around `javax.swing.Timer` to handle question countdowns.
- `ScoreManager`: Tracks current score and integrates with the leaderboard.
- `LeaderboardManager`: Handles reading/writing high scores to `leaderboard.txt`.
- `Main`: The entry point and GUI controller managing the `CardLayout`.

## Prerequisites

- Java Development Kit (JDK) 8 or higher.

## How to Run

1. Open your terminal or command prompt.
2. Navigate to the project root directory (`e:\Quiz Game`).
3. Compile the Java files:
   ```bash
   javac -d bin src/com/quizgame/*.java
   ```
4. Run the application (Make sure you are in the directory containing `questions.json`):
   ```bash
   java -cp bin com.quizgame.Main
   ```

## Included Question Bank
The `questions.json` contains a comprehensive set of interview-style questions mimicking patterns from TCS NQT, Infosys, Wipro, Amazon, Google, and Microsoft. Topics cover Arrays, Strings, Binary Search, Trees, Graphs, DP, Sliding Window, and more!
