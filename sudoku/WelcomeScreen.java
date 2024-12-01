package sudoku;

import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.*;

public class WelcomeScreen {
    public WelcomeScreen() {
        JFrame frame = new JFrame("Welcome to Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel(new ImageIcon("welcome_screen.png"));
        frame.add(welcomeLabel, BorderLayout.CENTER);
        welcomeLabel.setPreferredSize(new Dimension(540, 540));

        JButton startButton = new JButton("Start Game");
        startButton.setFont(SudokuMain.FONT);
        startButton.setBackground(SudokuMain.BUTTON_BG_COLOR);
        startButton.setForeground(SudokuMain.BUTTON_TEXT_COLOR);
        startButton.setPreferredSize(new Dimension(140, 30));
        startButton.addActionListener(event -> {
            frame.dispose(); // to close the window of welcome screen
            new SudokuMain();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack(); // to make the contents at or above their preferred sizes.
        frame.setLocationRelativeTo(null); // to make the content center
        frame.setVisible(true);

        // Play background music
        String filePath = "D:\\Klarissa\\courses in NTU\\IM1003\\GUI\\Java Project Sudoku\\background_music.wav";
        playBackgroundMusic(filePath);
    }

    private void playBackgroundMusic(String filePath) {
        try {

            File audioFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY); // repeat forever

            // Get the volume control for the clip
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // Set the volume level (-20.0f is quieter, 0.0f is normal, 6.0f is louder)
            gainControl.setValue(-10.0f);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WelcomeScreen();
            }
        });
    }
}