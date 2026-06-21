# 🎓 Professional DSA Quiz Game

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Java_Swing-007396?style=for-the-badge&logo=java&logoColor=white)
![JSON](https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white)

A highly robust, gamified Desktop Application built in **Pure Java** to help computer science students, graduates, and professionals prepare for coding interviews and placement exams (like TCS NQT, Infosys, Amazon, Google, etc.). 

By simulating a timed, high-pressure environment with diverse Data Structures and Algorithms (DSA) topics, this game ensures you are well-prepared for any technical assessment.

---

## ✨ Key Features

- **🧠 Tiered Difficulty Levels**: Choose from **Easy**, **Medium**, or **Hard** modes based on your preparation level.
- **⏱️ Timed Rounds**: Each question comes with a strict **30-second countdown timer** to test your speed and accuracy.
- **🔀 Dynamic Rounds**: Every round consists of 10 completely random and unique questions pulled from a local JSON database.
- **📈 Global Leaderboard**: Track your progress over time! The game saves and displays the Top 10 highest scores locally.
- **💡 Instant Explanations**: Immediate feedback is provided after answering. Learn *why* an answer is correct with detailed, concept-focused explanations.
- **🚫 Zero Dependencies**: Built completely from scratch! Uses a highly optimized, custom-built JSON parser to process the question bank, meaning **no Maven, no Gradle, and no external libraries** are required.

---

## 🛠️ Tech Stack & Architecture

- **Language:** Java (JDK 8 or higher)
- **GUI Framework:** Java Swing (`JFrame`, `CardLayout`, `Timer`)
- **Data Storage:** JSON (`questions.json`) for dynamic question pooling. File I/O (`leaderboard.txt`) for persistent score tracking.
- **Design Pattern:** Modular Object-Oriented Programming (OOP).

### Core Components
| Class Name | Responsibility |
| :--- | :--- |
| `Main.java` | The application entry point and main GUI Controller using `CardLayout`. |
| `Question.java` | A POJO model representing a single quiz question and its attributes. |
| `QuestionBank.java` | Loads `questions.json` using a custom lightweight JSON parser and shuffles questions dynamically. |
| `QuizManager.java` | Contains the core game loop, tracks current progress, and validates answers. |
| `TimerManager.java` | Encapsulates the `javax.swing.Timer` to safely handle countdowns on the Event Dispatch Thread (EDT). |
| `ScoreManager.java` | Manages point calculation during an active round. |
| `LeaderboardManager.java`| Reads from and writes to `leaderboard.txt` to persist high scores across sessions. |

---

## 📂 Project Structure

```text
📦 Quiz Game
 ┣ 📂 src
 ┃ ┗ 📂 com
 ┃   ┗ 📂 quizgame
 ┃     ┣ 📜 LeaderboardManager.java
 ┃     ┣ 📜 Main.java
 ┃     ┣ 📜 Question.java
 ┃     ┣ 📜 QuestionBank.java
 ┃     ┣ 📜 QuizManager.java
 ┃     ┣ 📜 ScoreManager.java
 ┃     ┗ 📜 TimerManager.java
 ┣ 📜 questions.json        # The 100-question DSA database
 ┣ 📜 leaderboard.txt       # Auto-generated file tracking high scores
 ┣ 📜 Project_Report.md     # Detailed documentation for academic submission
 ┗ 📜 README.md             # This documentation file
```

---

## 🚀 How to Install and Run

Since this project requires absolutely no external dependencies, running it is incredibly straightforward.

### Prerequisites
- Ensure you have **Java Development Kit (JDK) 8+** installed on your machine.
- Verify your installation by running `java -version` and `javac -version` in your terminal.

### Compilation and Execution
1. **Clone or Download** this repository and navigate to the root directory.
   ```bash
   cd "path/to/Quiz Game"
   ```
2. **Create a directory** to hold the compiled bytecode (if it doesn't already exist).
   ```bash
   mkdir bin
   ```
3. **Compile the Java source files** into the `bin` directory.
   ```bash
   javac -d bin src/com/quizgame/*.java
   ```
4. **Run the Application**. Ensure you are running this command from the root folder so the app can locate `questions.json`.
   ```bash
   java -cp bin com.quizgame.Main
   ```

---

## 🎮 How to Play
1. **Start Screen:** Select your desired difficulty (Easy, Medium, Hard) and click **Start Quiz**.
2. **Gameplay:** Read the question carefully. You have 30 seconds to click the correct option and hit **Submit Answer**. 
3. **Feedback:** After submitting, the correct answer turns Green. If you were wrong, your choice turns Red. Read the provided explanation at the bottom.
4. **Next Question:** Click **Next Question** to proceed.
5. **Results:** After 10 questions, your final score is displayed. Your score will automatically be saved to the leaderboard if it is among the Top 10.
6. **Leaderboard:** Access the Leaderboard from the Start Screen or Results Screen to see high scores.

---

## 🚀 Future Enhancements
- Database integration (SQLite or MySQL) to replace local JSON and TXT files.
- Dedicated user login system for individual score tracking.
- Web-based version using Spring Boot and React.

---
*Developed as a comprehensive project demonstrating advanced Java OOP, UI design, and algorithm implementations.*
