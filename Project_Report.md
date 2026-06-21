# Project Report: Professional DSA Quiz Game

## 1. Abstract
The "Professional DSA Quiz Game" is a Java-based desktop application developed to assist students and professionals in preparing for technical interviews, placement drives (e.g., TCS NQT, Infosys, Amazon), and competitive programming. The application provides an interactive platform with categorized difficulty levels, mimicking the time constraints of real exams using a 30-second timer per question.

## 2. Introduction
In today's competitive tech industry, mastering Data Structures and Algorithms (DSA) is crucial. Candidates often struggle not just with the concepts, but with solving them under time pressure. This project addresses that need by providing a gamified learning experience. The application asks 10 questions per round, tracks scores, offers detailed explanations upon answering, and maintains a persistent leaderboard.

## 3. Technologies Used
- **Programming Language**: Java (JDK 8+)
- **User Interface**: Java Swing (AWT, JFrame, JPanel, CardLayout)
- **Data Storage**: JSON (JavaScript Object Notation) for the Question Bank, Text File (`leaderboard.txt`) for high score persistence.
- **Concepts Applied**: Object-Oriented Programming (OOP), Custom Parsing, Event-Driven Programming.

## 4. System Architecture
The application is built using a modular Object-Oriented design, separating concerns into individual classes:
- **`Main.java`**: Acts as the GUI Controller, utilizing a `CardLayout` to switch between the Start Menu, Quiz Interface, Result Screen, and Leaderboard.
- **`Question.java`**: A POJO (Plain Old Java Object) storing question text, options, answer, explanation, topic, and difficulty.
- **`QuestionBank.java`**: Responsible for reading `questions.json` and parsing it. To avoid third-party dependencies, a custom lightweight JSON parsing mechanism was implemented.
- **`QuizManager.java`**: Handles the core gameplay logic, validating answers, and fetching random questions.
- **`TimerManager.java`**: Encapsulates `javax.swing.Timer` to provide a countdown mechanism that alerts the UI on tick and timeout.
- **`ScoreManager.java`**: Computes the score dynamically during a round.
- **`LeaderboardManager.java`**: Persists high scores to disk, ensuring data remains across multiple application sessions.

## 5. Modules Description

### 5.1. Question Bank & Data Management
The core data lies in `questions.json`, which contains ~100 distinct questions categorized into Easy, Medium, and Hard. Topics range from basic Arrays and Linear Search to advanced concepts like Dynamic Programming, Graphs, Tries, and Segment Trees. The `QuestionBank` class filters and randomly shuffles questions to ensure every round is unique.

### 5.2. Quiz Engine
Once a difficulty is selected, the `QuizManager` takes over, serving 10 questions. For each question, a 30-second timer runs. If the user selects an answer, the timer stops, the correctness is evaluated, and the background color updates (Green for correct, Red for incorrect) along with a detailed explanation to reinforce learning. If the timer expires, an automatic timeout is triggered.

### 5.3. Leaderboard System
The `LeaderboardManager` reads and writes to `leaderboard.txt`. It sorts all recorded scores in descending order and retains only the top 10. The UI reads this list to display a formatted ranking table.

## 6. Challenges and Solutions
- **Challenge:** Avoiding third-party libraries (like Maven/Gson) to keep the project portable and straightforward to compile.
- **Solution:** Designed a custom, lightweight string manipulation-based JSON parser within `QuestionBank.java` that effectively maps the well-formed JSON objects to Java instances.
- **Challenge:** Managing real-time UI updates for the timer without freezing the application.
- **Solution:** Used `javax.swing.Timer`, which inherently executes on the Event Dispatch Thread (EDT), ensuring thread safety and smooth UI updates.

## 7. Future Enhancements
- Integration of a robust relational database (like MySQL or SQLite) instead of JSON and Text files.
- Addition of user accounts to track individual progress over time rather than a global leaderboard.
- A dedicated module for coding snippets where users can type output logic.

## 8. Conclusion
The Professional DSA Quiz Game successfully achieves its goal of providing an engaging, timed environment for practicing computer science fundamentals. By adhering to clean OOP principles and a decoupled architecture, the codebase is highly maintainable and serves as an excellent foundation for further feature expansion.
