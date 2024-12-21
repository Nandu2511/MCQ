package com.mcq;
import java.io.*;
import java.util.ArrayList;

public class QuizManager {
    private ArrayList<Question> questions;
    private int score = 0;

    public QuizManager() {
        this.questions = new ArrayList<>();
        loadQuestionsFromFile("/home/Documents/Question.txt");  // Load questions from a .txt file
    }

    public void loadQuestionsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String questionText = "";
            ArrayList<String> options = new ArrayList<>();
            int correctAnswerIndex = -1;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Question")) {
                    // Add previous question before starting new one
                    if (!questionText.isEmpty()) {
                        questions.add(new Question(questionText, options.toArray(new String[0]), correctAnswerIndex));
                    }

                    // Start a new question
                    questionText = line.substring(10);  // Skip "Question X: "
                    options.clear();
                    correctAnswerIndex = -1;
                } else if (line.startsWith("Answer:")) {
                    correctAnswerIndex = Integer.parseInt(line.substring(8).trim()) - 1;  // Answer is 1-based, convert to 0-based
                } else if (line.startsWith("1.") || line.startsWith("2.") || line.startsWith("3.") || line.startsWith("4.")) {
                    options.add(line.substring(3).trim());
                }
            }
            
            // Add the last question to the list
            if (!questionText.isEmpty()) {
                questions.add(new Question(questionText, options.toArray(new String[0]), correctAnswerIndex));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void answerQuestion(int questionIndex, int answerIndex) {
        Question question = questions.get(questionIndex);
        if (question.getCorrectAnswerIndex() == answerIndex) {
            score++;
        }
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }
}
