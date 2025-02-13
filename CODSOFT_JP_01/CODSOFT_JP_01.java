import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
public class CODSOFT_JP_01 {
    private int numberToGuess;
    private int numberOfAttempts;
    private JTextField guessInput;
    private JTextArea feedbackArea;
    private JButton guessButton;
    public CODSOFT_JP_01() {
        JFrame frame = new JFrame("Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);
        frame.setLayout(null);
        JLabel promptLabel = new JLabel("Guess a number between 1 and 100:");
        promptLabel.setBounds(20, 20, 250, 25);
        frame.add(promptLabel);
        guessInput = new JTextField();
        guessInput.setBounds(20, 50, 100, 25);
        frame.add(guessInput);
        guessButton = new JButton("Guess");
        guessButton.setBounds(130, 50, 80, 25);
        frame.add(guessButton);
        feedbackArea = new JTextArea();
        feedbackArea.setBounds(20, 80, 500, 200);
        feedbackArea.setEditable(false);
        frame.add(feedbackArea);
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });
        frame.setVisible(true);
        resetGame();
    }
    private void resetGame() {
        Random random = new Random();
        numberToGuess = random.nextInt(100) + 1;
        numberOfAttempts = 0;
        feedbackArea.setText(""); 
    }
    private void handleGuess() {
        try {
            int userGuess = Integer.parseInt(guessInput.getText());
            numberOfAttempts++;

            if (userGuess < numberToGuess) {
                feedbackArea.setText("Too low! Try again.");
            } else if (userGuess > numberToGuess) {
                feedbackArea.setText("Too high! Try again.");
            } else {
                feedbackArea.setText("Congratulations! You've guessed the number " + numberToGuess + " in " + numberOfAttempts + " attempts.");
               
                Timer timer = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        resetGame();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        } catch (NumberFormatException e) {
            feedbackArea.setText("Please enter a valid number.");
        }
        guessInput.setText(""); 
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PRODIGY_SD_02());
    }
}