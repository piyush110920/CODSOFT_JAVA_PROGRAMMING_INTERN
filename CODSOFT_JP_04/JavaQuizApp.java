import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class JavaQuizApp {
    private JFrame frame;
    private JPanel panel;
    private JLabel questionLabel;
    private JRadioButton option1, option2, option3, option4;
    private ButtonGroup optionsGroup;
    private JButton submitButton;
    private JLabel timerLabel;
    
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int totalQuestions = 5;
    private javax.swing.Timer timer;
    private int timeLeft = 10;

    private List<Question> questions = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JavaQuizApp window = new JavaQuizApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public JavaQuizApp() {
        initialize();
        loadJavaQuestions();
    }

    private void initialize() {
        frame = new JFrame("Java Quiz Application");
        frame.setBounds(100, 100, 500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));
        
        questionLabel = new JLabel("Question?");
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(questionLabel);

        option1 = new JRadioButton("Option 1");
        option2 = new JRadioButton("Option 2");
        option3 = new JRadioButton("Option 3");
        option4 = new JRadioButton("Option 4");

        optionsGroup = new ButtonGroup();
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsGroup.add(option4);

        panel.add(option1);
        panel.add(option2);
        panel.add(option3);
        panel.add(option4);
        
        submitButton = new JButton("Submit Answer");
        submitButton.addActionListener(this::submitAnswer);
        panel.add(submitButton);

        timerLabel = new JLabel("Time Left: 10");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(timerLabel);

        frame.getContentPane().add(panel);

        timer = new javax.swing.Timer(1000, e -> updateTimer());
    }

    private void loadJavaQuestions() {
        questions.add(new Question("Which of these is the entry point for a Java program?", 
                "main() method", "run() method", "start() method", "execute() method", "main() method"));
        questions.add(new Question("Which package contains the Scanner class in Java?", 
                "java.util", "java.lang", "java.io", "java.text", "java.util"));
        questions.add(new Question("What does JVM stand for?", 
                "Java Virtual Memory", "Java Version Manager", "Java Virtual Machine", "Java Verified Module", "Java Virtual Machine"));
        questions.add(new Question("Which keyword is used to declare a constant in Java?", 
                "const", "final", "constant", "static", "final"));
        questions.add(new Question("Which of these is not a primitive data type in Java?", 
                "int", "double", "String", "char", "String"));
        
        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < totalQuestions) {
            Question q = questions.get(currentQuestionIndex);
            questionLabel.setText(q.getQuestionText());
            option1.setText(q.getOption1());
            option2.setText(q.getOption2());
            option3.setText(q.getOption3());
            option4.setText(q.getOption4());

            optionsGroup.clearSelection();
            timeLeft = 10;
            timerLabel.setText("Time Left: " + timeLeft);
            timer.start();
        } else {
            showResult();
        }
    }

    private void submitAnswer(ActionEvent e) {
        timer.stop();
        Question q = questions.get(currentQuestionIndex);
        String selectedAnswer = getSelectedAnswer();

        if (selectedAnswer != null && selectedAnswer.equals(q.getCorrectAnswer())) {
            score++;
        }

        currentQuestionIndex++;
        showNextQuestion();
    }

    private String getSelectedAnswer() {
        if (option1.isSelected()) return option1.getText();
        if (option2.isSelected()) return option2.getText();
        if (option3.isSelected()) return option3.getText();
        if (option4.isSelected()) return option4.getText();
        return null;
    }

    private void updateTimer() {
        if (timeLeft > 0) {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft);
        } else {
            timer.stop();
            Question q = questions.get(currentQuestionIndex);
            String selectedAnswer = getSelectedAnswer();

            if (selectedAnswer != null && selectedAnswer.equals(q.getCorrectAnswer())) {
                score++;
            }

            currentQuestionIndex++;
            showNextQuestion();
        }
    }

    private void showResult() {
        JOptionPane.showMessageDialog(frame, "Quiz Completed!\nScore: " + score + "/" + totalQuestions);
        System.exit(0);
    }

    class Question {
        private String questionText;
        private String option1, option2, option3, option4;
        private String correctAnswer;

        public Question(String questionText, String option1, String option2, String option3, String option4, String correctAnswer) {
            this.questionText = questionText;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestionText() {
            return questionText;
        }

        public String getOption1() {
            return option1;
        }

        public String getOption2() {
            return option2;
        }

        public String getOption3() {
            return option3;
        }

        public String getOption4() {
            return option4;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
