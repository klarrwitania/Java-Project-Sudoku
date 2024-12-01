package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;

public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L; // to prevent serial warning
    public static final Font FONT = new Font("Poppins", Font.BOLD, 15);
    public static final Color BUTTON_BG_COLOR = new Color(226, 149, 120); // #E29578 atomic tangerine
    public static final Color INVALID_BG_COLOR = new Color(218, 116, 79); // #DA744F burnt sienna
    public static final Color INV_BUTTON_TEXT_COLOR = new Color(218, 116, 79); // #DA744F burnt sienna
    public static final Color INV_BUTTON_BG_COLOR = new Color(255, 221, 210); // #edf6f9 alice blue
    public static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    public static final Color LABEL_TEXT_COLOR = Color.BLACK;

    JButton btnReset = new JButton("RESET");
    JButton btnEasy = new JButton("Easy");
    JButton btnIntermediate = new JButton("Intermediate");
    JButton btnDifficult = new JButton("Difficult");
    JLabel lblTimer = new JLabel("Time: 10:00");

    public Timer timer;
    int remainingTime = 600;
    int timeTakenInSeconds = 0;

    GameBoardPanel board;

    private Clip wrongGuessSoundClip;
    String filePathWrong = "D:\\Klarissa\\courses in NTU\\IM1003\\GUI\\Java Project Sudoku\\wrong.wav";

    private void loadSoundClips() {
        try {
            // Load wrong guess sound
            File audioFileWrong = new File(filePathWrong);
            AudioInputStream wrongGuessAudioInputStream = AudioSystem.getAudioInputStream(audioFileWrong);
            wrongGuessSoundClip = AudioSystem.getClip();
            wrongGuessSoundClip.open(wrongGuessAudioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playWrongGuessSound() {
        if (wrongGuessSoundClip != null) {
            wrongGuessSoundClip.setFramePosition(0); // Reset to start
            wrongGuessSoundClip.start();
        }
    }

    // Constructor
    public SudokuMain() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout()); // five regions: North, South, East, West, Center

        // Create a new panel for the north region
        JPanel navPanel = new JPanel(new FlowLayout()); // left to right, to the next row if neccessary
        styleButton(btnEasy);
        navPanel.add(btnEasy);
        styleButton(btnIntermediate);
        navPanel.add(btnIntermediate);
        styleButton(btnDifficult);
        navPanel.add(btnDifficult);
        cp.add(navPanel, BorderLayout.NORTH); // place it on top (north)

        // Create a new panel for the south region
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS)); // arranges components in a single column or
                                                                           // row, in this case vertical arrangement

        // Add the timer panel to the southPanel
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2));
        styleLabel(lblTimer);
        timerPanel.add(lblTimer); // Add the timer label
        southPanel.add(timerPanel);

        // Add a button panel to reset the game board
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(-5, 0, 0, 0));
        styleButton(btnReset);
        buttonPanel.add(btnReset);
        southPanel.add(buttonPanel);

        cp.add(southPanel, BorderLayout.SOUTH); // place it at the bottom (south)

        // ActionListener for button
        btnEasy.addActionListener(e -> navigateToPuzzle("easy"));
        btnIntermediate.addActionListener(e -> navigateToPuzzle("intermediate"));
        btnDifficult.addActionListener(e -> navigateToPuzzle("difficult"));

        btnReset.addActionListener(e -> resetGame(board.getCurrentLevel()));

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                timeTakenInSeconds++;
                int minutes = remainingTime / 60;
                int seconds = remainingTime % 60;
                lblTimer.setText(String.format("Time: %02d:%02d", minutes, seconds));
                if (remainingTime <= 0) {
                    playWrongGuessSound();
                    timer.stop(); // if not will be negative, and the panel keeps showing up

                    playWrongGuessSound(); // play sound for wrong guess
                    JPanel panel = new JPanel(); // main container for dialog's content
                    panel.setBackground(INVALID_BG_COLOR);
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // vertical arrangement
                    ImageIcon icon = new ImageIcon("alarm.png");

                    JLabel label = new JLabel("<html><b>Game Over!</b><br>You Lose</html>",
                            icon, SwingConstants.CENTER); // centered the icon and text
                    label.setFont(FONT);
                    label.setForeground(BUTTON_TEXT_COLOR);

                    JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    labelPanel.setBackground(INVALID_BG_COLOR);
                    labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 55, 0, 55)); // add padding around the
                                                                                          // label
                    labelPanel.add(label); // add the label to a panel with centered flow layout
                    panel.add(labelPanel); // add the label panel to the main panel

                    JDialog dialog = new JDialog();
                    dialog.setTitle("TIME'S UP!");
                    dialog.setModal(true);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                    // Create the OK button
                    JButton okButton = new JButton("Try Again");
                    okButton.setAlignmentX(Component.CENTER_ALIGNMENT); // centered in horizontal alignment
                    okButton.addActionListener(event -> {
                        dialog.dispose();
                        resetGame(board.getCurrentLevel());
                    });
                    /*
                     * long form:
                     * okButton.addActionListener(new ActionListener() {
                     * 
                     * @Override
                     * public void actionPerformed(ActionEvent e) {
                     * dialog.dispose();
                     * resetGame(board.getCurrentLevel());
                     * }
                     * });
                     */

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    buttonPanel.setBackground(INVALID_BG_COLOR);
                    buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 55, 10, 55)); // Add padding around the
                                                                                           // button
                    buttonPanel.add(okButton); // Add the OK button to a panel with centered flow layout
                    panel.add(buttonPanel); // Add the button panel to the main panel

                    styleInvButton(okButton);

                    dialog.getContentPane().add(panel);
                    dialog.pack();
                    dialog.setLocationRelativeTo(null); // Center the dialog on the screen
                    dialog.setVisible(true);
                }
            }
        });
        timer.start();

        board = new GameBoardPanel(this, timer); // Create GameBoardPanel after initializing the timer
        cp.add(board, BorderLayout.CENTER);

        // Initialize the game board to start the game (start with easy)
        board.newEasyGame();

        pack();
        setLocationRelativeTo(null); // to center
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits the application when user close the window
        setTitle("Sudoku");
        setVisible(true);

        loadSoundClips();
    }

    private void styleButton(JButton button) {
        button.setFont(FONT);
        button.setBackground(BUTTON_BG_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 30));
    }

    private void styleInvButton(JButton button) {
        button.setFont(FONT);
        button.setBackground(INV_BUTTON_BG_COLOR);
        button.setForeground(INV_BUTTON_TEXT_COLOR);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 30));
    }

    private void styleLabel(JLabel label) {
        label.setFont(FONT);
        label.setForeground(LABEL_TEXT_COLOR);
    }

    private void resetGame(String level) {
        switch (level) {
            case "easy":
                board.newEasyGame();
                break;
            case "intermediate":
                board.newIntermediateGame();
                break;
            case "difficult":
                board.newDifficultGame();
                break;
            default:
                System.out.println("Invalid level.");
        }
        remainingTime = 600;
        timer.restart(); // Restart the timer
    }

    private void navigateToPuzzle(String puzzleType) {
        switch (puzzleType) {
            case "easy":
                board.newEasyGame();
                break;
            case "intermediate":
                board.newIntermediateGame();
                break;
            case "difficult":
                board.newDifficultGame();
                break;
            default:
                System.out.println("Invalid puzzle type.");
        }
        remainingTime = 600;
    }

    public String formatTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}