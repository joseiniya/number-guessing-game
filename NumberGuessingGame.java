import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class NumberGuessingGame extends JFrame {

    // FRONTEND COMPONENTS
    private JTextField minField, maxField, guessField;
    private JLabel messageLabel, chancesLabel;
    private JButton startButton, guessButton, againButton;

    // BACKEND VARIABLES
    private int min, max, number;
    private int chances;
    private boolean gameStarted;

    private Random rand = new Random();

    // CONSTRUCTOR
    public NumberGuessingGame() {

        setTitle("NUMBER GUESSING GAME");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // MAIN PANEL
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(25, 25, 45));

        // TITLE
        JLabel title = new JLabel("NUMBER GUESSING GAME");
        title.setBounds(65, 25, 380, 40);
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setForeground(Color.WHITE);
        panel.add(title);

        // INSTRUCTIONS
        JLabel instruction = new JLabel("Guess the secret number in 5 chances!");
        instruction.setBounds(85, 75, 350, 25);
        instruction.setFont(new Font("Arial", Font.PLAIN, 14));
        instruction.setForeground(Color.LIGHT_GRAY);
        panel.add(instruction);

        // LOWER LIMIT
        JLabel minLabel = new JLabel("Lower Limit:");
        minLabel.setBounds(70, 130, 120, 30);
        minLabel.setForeground(Color.WHITE);
        panel.add(minLabel);

        minField = new JTextField();
        minField.setBounds(200, 130, 220, 30);
        panel.add(minField);

        // UPPER LIMIT
        JLabel maxLabel = new JLabel("Upper Limit:");
        maxLabel.setBounds(70, 175, 120, 30);
        maxLabel.setForeground(Color.WHITE);
        panel.add(maxLabel);

        maxField = new JTextField();
        maxField.setBounds(200, 175, 220, 30);
        panel.add(maxField);

        // START BUTTON
        startButton = new JButton("START GAME");
        startButton.setBounds(150, 225, 200, 40);
        startButton.setBackground(new Color(90, 80, 220));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        panel.add(startButton);

        // GUESS INPUT
        JLabel guessLabel = new JLabel("Enter Your Guess:");
        guessLabel.setBounds(70, 295, 130, 30);
        guessLabel.setForeground(Color.WHITE);
        panel.add(guessLabel);

        guessField = new JTextField();
        guessField.setBounds(200, 295, 220, 30);
        guessField.setEnabled(false);
        panel.add(guessField);

        // GUESS BUTTON
        guessButton = new JButton("SUBMIT GUESS");
        guessButton.setBounds(150, 340, 200, 40);
        guessButton.setBackground(new Color(40, 170, 100));
        guessButton.setForeground(Color.WHITE);
        guessButton.setFocusPainted(false);
        guessButton.setEnabled(false);
        panel.add(guessButton);

        // MESSAGE
        messageLabel = new JLabel("Set your limits and start playing!");
        messageLabel.setBounds(55, 400, 390, 30);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 15));
        messageLabel.setForeground(Color.YELLOW);
        panel.add(messageLabel);

        // CHANCES
        chancesLabel = new JLabel("Chances Left: 5");
        chancesLabel.setBounds(150, 435, 200, 25);
        chancesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chancesLabel.setForeground(Color.WHITE);
        panel.add(chancesLabel);

        // PLAY AGAIN BUTTON
        againButton = new JButton("PLAY AGAIN");
        againButton.setBounds(150, 475, 200, 35);
        againButton.setEnabled(false);
        panel.add(againButton);

        add(panel);

        // BUTTON ACTIONS

        startButton.addActionListener(e -> startGame());

        guessButton.addActionListener(e -> checkGuess());

        guessField.addActionListener(e -> checkGuess());

        againButton.addActionListener(e -> resetGame());
    }

    // =========================
    // BACKEND LOGIC
    // =========================

    // START GAME
    private void startGame() {

        try {

            min = Integer.parseInt(minField.getText());
            max = Integer.parseInt(maxField.getText());

            if (min >= max) {
                messageLabel.setText("Invalid Limits!");
                return;
            }

            // Generate random number
            number = (int) (min +
                    (long) (rand.nextDouble() *
                    ((long) max - min + 1)));

            chances = 5;
            gameStarted = true;

            guessField.setEnabled(true);
            guessButton.setEnabled(true);
            againButton.setEnabled(false);

            minField.setEnabled(false);
            maxField.setEnabled(false);
            startButton.setEnabled(false);

            chancesLabel.setText("Chances Left: " + chances);
            messageLabel.setText("Game Started! Make Your Guess.");

            guessField.setText("");
            guessField.requestFocus();

        } catch (NumberFormatException ex) {

            messageLabel.setText("Please Enter Valid Numbers!");

        }
    }

    // CHECK GUESS
    private void checkGuess() {

        if (!gameStarted) {
            return;
        }

        try {

            int guess = Integer.parseInt(guessField.getText());

            // Check range
            if (guess < min || guess > max) {

                messageLabel.setText("Guess Within The Given Range!");
                return;
            }

            // Reduce chances
            chances--;

            if (guess == number) {

                messageLabel.setText("CORRECT! YOU WON!");
                messageLabel.setForeground(Color.GREEN);

                endGame();

            }

            else if (guess > number) {

                messageLabel.setText("TOO HIGH! TRY AGAIN.");
                messageLabel.setForeground(Color.ORANGE);

            }

            else {

                messageLabel.setText("TOO LOW! TRY AGAIN.");
                messageLabel.setForeground(Color.ORANGE);

            }

            chancesLabel.setText("Chances Left: " + chances);

            // Check remaining chances
            if (chances == 0 && guess != number) {

                messageLabel.setText("GAME OVER! Number: " + number);
                messageLabel.setForeground(Color.RED);

                endGame();
            }

            guessField.setText("");
            guessField.requestFocus();

        } catch (NumberFormatException ex) {

            messageLabel.setText("Please Enter A Valid Guess!");

        }
    }

    // END GAME
    private void endGame() {

        gameStarted = false;

        guessField.setEnabled(false);
        guessButton.setEnabled(false);

        againButton.setEnabled(true);
    }

    // RESET GAME
    private void resetGame() {

        minField.setEnabled(true);
        maxField.setEnabled(true);
        startButton.setEnabled(true);

        minField.setText("");
        maxField.setText("");
        guessField.setText("");

        guessField.setEnabled(false);
        guessButton.setEnabled(false);
        againButton.setEnabled(false);

        chances = 5;

        chancesLabel.setText("Chances Left: 5");

        messageLabel.setText("Set your limits and start playing!");
        messageLabel.setForeground(Color.YELLOW);

        gameStarted = false;
    }

    // MAIN METHOD
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            NumberGuessingGame game = new NumberGuessingGame();

            game.setVisible(true);

        });
    }
}