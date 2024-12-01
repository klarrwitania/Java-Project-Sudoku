package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*; // for playing sound
import java.io.*; // for playing sound File

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L; // to prevent serial warning

    public static final int CELL_SIZE = 60; // Cell width/height in pixels
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final Font FONT = new Font("Poppins", Font.BOLD, 15);
    public static final Color SUCCESS_BG_COLOR = new Color(0, 109, 119); // #006d77 caribbean current
    public static final Color INVALID_BG_COLOR = new Color(218, 116, 79); // #DA744F burnt sienna
    public static final Color TEXT_COLOR = Color.WHITE;
    public static final Color BUTTON_TEXT_COLOR = new Color(218, 116, 79); // #DA744F burnt sienna
    public static final Color BUTTON_BG_COLOR = new Color(255, 221, 210); // #edf6f9 alice blue
    // Define properties
    /** The game board composes of 9x9 Cells (customized JTextFields) */
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    /** It also contains a Puzzle with array numbers and isGiven */
    private Puzzle puzzle = new Puzzle();
    private SudokuMain sudokuMain; // Reference to SudokuMain

    private String currentLevel = "easy"; // initialize current level to easy

    private Timer timer;

    private Clip correctGuessSoundClip;
    private Clip wrongGuessSoundClip;
    private Clip winningGuessSoundClip;

    /** Constructor */
    public GameBoardPanel(SudokuMain sudokuMain, Timer timer) {
        this.sudokuMain = sudokuMain;
        this.timer = timer;

        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE)); // JPanel

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]); // JPanel
            }
        }

        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        // Cells (JTextFields)
        CellInputListener listener = new CellInputListener();

        // [TODO 4] Adds this common listener to all editable cells
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells.length; col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener); // For all editable rows and cols
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        loadSoundClips();
    }

    String filePathCorrect = "D:\\Klarissa\\courses in NTU\\IM1003\\GUI\\Java Project Sudoku\\correct.wav";
    String filePathWrong = "D:\\Klarissa\\courses in NTU\\IM1003\\GUI\\Java Project Sudoku\\wrong.wav";
    String filePathWinning = "D:\\Klarissa\\courses in NTU\\IM1003\\GUI\\Java Project Sudoku\\winning.wav";

    private void loadSoundClips() {
        try {
            // Load correct guess sound
            File audioFileCorrect = new File(filePathCorrect);
            AudioInputStream correctGuessAudioInputStream = AudioSystem.getAudioInputStream(audioFileCorrect);
            correctGuessSoundClip = AudioSystem.getClip();
            correctGuessSoundClip.open(correctGuessAudioInputStream);

            // Load wrong guess sound
            File audioFileWrong = new File(filePathWrong);
            AudioInputStream wrongGuessAudioInputStream = AudioSystem.getAudioInputStream(audioFileWrong);
            wrongGuessSoundClip = AudioSystem.getClip();
            wrongGuessSoundClip.open(wrongGuessAudioInputStream);

            // Load winning sound
            File audioFileWinning = new File(filePathWinning);
            AudioInputStream winningGuessAudioInputStream = AudioSystem.getAudioInputStream(audioFileWinning);
            winningGuessSoundClip = AudioSystem.getClip();
            winningGuessSoundClip.open(winningGuessAudioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playCorrectGuessSound() {
        if (correctGuessSoundClip != null) {
            correctGuessSoundClip.setFramePosition(0); // Reset to start
            correctGuessSoundClip.start();
        }
    }

    private void playWrongGuessSound() {
        if (wrongGuessSoundClip != null) {
            wrongGuessSoundClip.setFramePosition(0); // Reset to start
            wrongGuessSoundClip.start();
        }
    }

    private void playWinningSound() {
        if (winningGuessSoundClip != null) {
            winningGuessSoundClip.setFramePosition(0); // Reset to start
            winningGuessSoundClip.start();
        }
    }

    public void newEasyGame() {
        // Generate a new easy puzzle
        currentLevel = "easy";
        puzzle.easyPuzzle(2);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    public void newIntermediateGame() {
        // Generate a new intermediate puzzle
        currentLevel = "intermediate";
        puzzle.intermediatePuzzle(24);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    public void newDifficultGame() {
        // Generate a new difficult puzzle
        currentLevel = "difficult";
        puzzle.difficultPuzzle(38);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get a reference of the JTextField that triggers this action event
            Cell sourceCell = (Cell) e.getSource();

            try { // try and catch to message if there is invalid input
                  // Retrieve the int entered
                int numberIn = Integer.parseInt(sourceCell.getText());
                // For debugging
                System.out.println("You entered " + numberIn);

                if (numberIn >= 1 && numberIn <= 9) {

                    if (numberIn == sourceCell.number) {
                        sourceCell.status = CellStatus.CORRECT_GUESS;
                        playCorrectGuessSound(); // Play sound for correct guess
                    } else {
                        sourceCell.status = CellStatus.WRONG_GUESS;
                        playWrongGuessSound(); // Play sound for wrong guess
                    }
                    sourceCell.paint(); // re-paint this cell based on its status

                    if (isSolved()) {
                        playWinningSound(); // Play sound for winning
                        timer.stop();

                        JPanel panel = new JPanel();
                        panel.setBackground(SUCCESS_BG_COLOR);
                        panel.setLayout(new BorderLayout());
                        ImageIcon icon = new ImageIcon(
                                "congratulations.png");

                        // Create label
                        JLabel label = new JLabel("<html><b>Congratulations!</b><br>Time Taken: " +
                                sudokuMain.formatTime(sudokuMain.timeTakenInSeconds) + "</html>",
                                icon, SwingConstants.CENTER);
                        label.setFont(FONT);
                        label.setForeground(TEXT_COLOR);
                        label.setPreferredSize(new Dimension(300, 120));

                        // Add the label to the center of the panel
                        panel.add(label, BorderLayout.CENTER);

                        // Create and configure the custom dialog
                        JDialog dialog = new JDialog(); // JOptionPane.showMessageDialog hard to design the interface
                        dialog.setTitle("SUCCESS!");
                        dialog.setModal(true); // to block input to other windows in the same application until it's
                                               // closed
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // when user close the window, it
                                                                                   // will be removed from memory
                        dialog.getContentPane().add(panel); // to add panel to the content pane of the dialog
                        dialog.pack(); // to follow the dimension size
                        dialog.setLocationRelativeTo(null); // to center the dialog on the screen
                        dialog.setVisible(true); // to show the dialog

                        sudokuMain.timeTakenInSeconds = 0; // reset the timer
                    }
                } else {
                    // If Number is not 1-9
                    sourceCell.status = CellStatus.WRONG_GUESS;
                    playWrongGuessSound(); // Play sound for wrong guess
                    sourceCell.paint();
                    JPanel panel = new JPanel();
                    panel.setBackground(INVALID_BG_COLOR);
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    ImageIcon icon = new ImageIcon("error.png");

                    JLabel label = new JLabel("<html><b>Invalid Input!</b><br>Enter Number 1-9 Only</html>",
                            icon, SwingConstants.CENTER);
                    label.setFont(FONT);
                    label.setForeground(TEXT_COLOR);

                    JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    labelPanel.setBackground(INVALID_BG_COLOR);
                    labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20)); // Add padding around the
                                                                                          // label
                    labelPanel.add(label); // Add the label to a panel with centered flow layout
                    panel.add(labelPanel); // Add the label panel to the main panel

                    JDialog dialog = new JDialog();
                    dialog.setTitle("ERROR!");
                    dialog.setModal(true); // cannot ignore the dialog, have to close or press OK to close it
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // when close, remove from memory

                    // Create the OK button
                    JButton okButton = new JButton("OK");
                    okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    okButton.addActionListener(event -> dialog.dispose()); // action listener, when OK is click, dialog
                                                                           // will dispose
                    /*
                     * long form:
                     * okButton.addActionListener(new ActionListener() {
                     * 
                     * @Override
                     * public void actionPerformed(ActionEvent e) {
                     * dialog.dispose(); // close the dialog when OK is clicked
                     * }
                     */

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    buttonPanel.setBackground(INVALID_BG_COLOR);
                    buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20)); // Add padding around the
                                                                                           // button
                    buttonPanel.add(okButton); // Add the OK button to a panel with centered flow layout
                    panel.add(buttonPanel); // Add the button panel to the main panel

                    styleButton(okButton);

                    dialog.getContentPane().add(panel);
                    dialog.pack();
                    dialog.setLocationRelativeTo(null); // Center the dialog on the screen
                    dialog.setVisible(true);
                }

            } catch (NumberFormatException ex) {
                // If input is not number
                sourceCell.status = CellStatus.WRONG_GUESS;
                playWrongGuessSound(); // Play sound for wrong guess
                sourceCell.paint();
                JPanel panel = new JPanel();
                panel.setBackground(INVALID_BG_COLOR);
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                ImageIcon icon = new ImageIcon("error.png");

                JLabel label = new JLabel("<html><b>Invalid Input!</b><br>Enter Number Only</html>",
                        icon, SwingConstants.CENTER);
                label.setFont(FONT);
                label.setForeground(TEXT_COLOR);

                JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                labelPanel.setBackground(INVALID_BG_COLOR);
                labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 35, 0, 35)); // Add padding around the
                                                                                      // label
                labelPanel.add(label); // Add the label to a panel with centered flow layout
                panel.add(labelPanel); // Add the label panel to the main panel

                JDialog dialog = new JDialog();
                dialog.setTitle("ERROR!");
                dialog.setModal(true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                // Create the OK button
                JButton okButton = new JButton("OK");
                okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                okButton.addActionListener(event -> dialog.dispose());

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setBackground(INVALID_BG_COLOR);
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 35, 10, 35)); // Add padding around the button
                buttonPanel.add(okButton); // Add the OK button to a panel with centered flow layout
                panel.add(buttonPanel); // Add the button panel to the main panel

                styleButton(okButton);

                dialog.getContentPane().add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(null); // Center the dialog on the screen
                dialog.setVisible(true);
            }
        }

        private void styleButton(JButton button) {
            button.setFont(FONT);
            button.setBackground(BUTTON_BG_COLOR);
            button.setForeground(BUTTON_TEXT_COLOR);
            button.setBorderPainted(false);
            button.setPreferredSize(new Dimension(60, 30));
        }
    }
}