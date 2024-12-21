package com.mcq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

public class McqApplicationTests {
    
    private QuizManager quizManager;

    @BeforeEach
    public void setUp() {
        quizManager = new QuizManager();
        try {
            quizManager.loadQuestionsFromFile("/home/getepay/Documents/Question.txt");
        } catch (Exception e) {
            fail("Failed to load questions from file");
        }
    }

    @Test
    public void testLoadQuestionsFromFile() {
        assertEquals(10, quizManager.getTotalQuestions(), "There should be 10 questions loaded.");
        
        Question firstQuestion = quizManager.getQuestion(0);
        assertNotNull(firstQuestion, "First question should not be null.");
        assertEquals("What is the capital of France?", firstQuestion.getQuestionText(), "First question text is incorrect.");
        assertArrayEquals(new String[] {"Paris", "London", "Rome", "Berlin"}, firstQuestion.getOptions(), "Options for first question are incorrect.");
    }

    @Test
    public void testAnswerCorrect() {

        quizManager.answerQuestion(0, 0);
        assertEquals(1, quizManager.getScore(), "Score should be 1 after answering correctly.");
    }

    @Test
    public void testAnswerIncorrect() {
       
        quizManager.answerQuestion(0, 1); 
        assertEquals(0, quizManager.getScore(), "Score should be 0 after answering incorrectly.");
    }

    @Test
    public void testScoreAfterMultipleAnswers() {
        // Test answering multiple questions
        quizManager.answerQuestion(0, 0); // Correct answer for Q1
        quizManager.answerQuestion(1, 3); // Incorrect answer for Q2
        quizManager.answerQuestion(2, 0); // Incorrect answer for Q3
        quizManager.answerQuestion(3, 0); // Correct answer for Q4

        assertEquals(2, quizManager.getScore(), "Score should be 2 after multiple questions.");
    }

    @Test
    public void testInvalidAnswer() {

        quizManager.answerQuestion(0, 5); 
        assertEquals(0, quizManager.getScore(), "Score should remain 0 after invalid answer.");
    }

    @Test
    public void testAllAnswers() {
        for (int i = 0; i < quizManager.getTotalQuestions(); i++) {
            quizManager.answerQuestion(i, quizManager.getQuestion(i).getCorrectAnswerIndex());
        }
        assertEquals(10, quizManager.getScore(), "Score should be 10 if all answers are correct.");
    }
}
