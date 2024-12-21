package com.mcq;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuizManager quizManager = new QuizManager();
        int totalQuestions = quizManager.getTotalQuestions();
        int questionIndex = 0;

        System.out.println("Welcome to the Multiple Choice Question Application!");
        while (questionIndex < totalQuestions) {
            Question currentQuestion = quizManager.getQuestion(questionIndex);

            System.out.println("Question " + (questionIndex + 1) + ": " + currentQuestion.getQuestionText());
            String[] options = currentQuestion.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }

            System.out.print("Enter your answer (1-" + options.length + "): ");
            int answer = scanner.nextInt();

            if (answer >= 1 && answer <= options.length) {
                quizManager.answerQuestion(questionIndex, answer - 1);  
            } else {
                System.out.println("Invalid answer. Please enter a number between 1 and " + options.length);
                continue;
            }
            questionIndex++;
        }
        System.out.println("\nQuiz Complete!");
        System.out.println("Your Score: " + quizManager.getScore() + " out of " + totalQuestions);
    }
}
