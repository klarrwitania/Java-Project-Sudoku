package sudoku;

/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
    // All variables have package access
    // The numbers on the puzzle
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    // Constructor
    public Puzzle() {
        super();
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be
    // used
    // to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void easyPuzzle(int cellsToGuess) {
        // I hardcode a puzzle here for illustration and testing.
        int[][] hardcodedNumbers = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };

        // Copy from hardcodedNumbers into the array "numbers"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = hardcodedNumbers[row][col];
            }
        }

        // Need to use input parameter cellsToGuess!
        // Hardcoded for testing, only 2 cells of "8" is NOT GIVEN
        boolean[][] hardcodedIsGiven = { { true, true, true, true, true, false, true, true, true },
                { true, true, true, true, true, true, true, true, false },
                { true, true, true, true, true, true, true, true, true },
                { true, true, true, true, true, true, true, true, true },
                { true, true, true, true, true, true, true, true, true },
                { true, true, true, true, true, true, true, true, true },
                { true, true, true, true, true, true, true, true, true },
                { true, true, true, true, true, true, true, true, true },
                { true, true, true, true, true, true, true, true, true } };

        // Copy from hardcodedIsGiven into array "isGiven"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                isGiven[row][col] = hardcodedIsGiven[row][col];
            }
        }
    }

    public void intermediatePuzzle(int cellsToGuess) {
        // I hardcode a puzzle here for illustration and testing.
        int[][] hardcodedNumbers = { { 1, 3, 5, 6, 7, 4, 9, 8, 2 },
                { 4, 7, 6, 8, 2, 9, 5, 1, 3 },
                { 2, 8, 9, 5, 1, 3, 7, 6, 4 },
                { 6, 2, 4, 1, 9, 5, 8, 3, 7 },
                { 8, 9, 7, 2, 3, 6, 1, 4, 5 },
                { 3, 5, 1, 7, 4, 8, 6, 2, 9 },
                { 5, 4, 3, 9, 6, 1, 2, 7, 8 },
                { 9, 1, 2, 4, 8, 7, 3, 5, 6 },
                { 7, 6, 8, 3, 5, 2, 4, 9, 1 } };

        // Copy from hardcodedNumbers into the array "numbers"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = hardcodedNumbers[row][col];
            }
        }

        // Need to use input parameter cellsToGuess!
        // Number of cells not given (cellsToGuess:24)
        boolean[][] hardcodedIsGiven = { { false, true, true, true, true, true, false, true, false },
                { true, true, false, true, true, true, true, false, true },
                { false, true, true, false, true, true, false, true, false },
                { false, true, true, false, true, true, true, false, true },
                { true, true, false, true, false, true, false, true, true },
                { true, false, true, true, true, true, true, true, false },
                { false, true, true, true, false, true, true, true, true },
                { true, false, true, true, true, true, false, true, true },
                { true, true, false, false, true, true, true, true, false } };

        // Copy from hardcodedIsGiven into array "isGiven"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                isGiven[row][col] = hardcodedIsGiven[row][col];
            }
        }
    }

    public void difficultPuzzle(int cellsToGuess) {
        // I hardcode a puzzle here for illustration and testing.
        int[][] hardcodedNumbers = { { 8, 7, 6, 4, 9, 3, 2, 5, 1 },
                { 3, 4, 5, 7, 1, 2, 9, 6, 8 },
                { 2, 9, 1, 5, 6, 8, 4, 7, 3 },
                { 9, 8, 2, 1, 3, 5, 7, 4, 6 },
                { 7, 5, 4, 8, 2, 6, 3, 1, 9 },
                { 1, 6, 3, 9, 4, 7, 8, 2, 5 },
                { 4, 1, 7, 3, 5, 9, 6, 8, 2 },
                { 6, 3, 8, 2, 7, 1, 5, 9, 4 },
                { 5, 2, 9, 6, 8, 4, 1, 3, 7 }, };

        // Copy from hardcodedNumbers into the array "numbers"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = hardcodedNumbers[row][col];
            }
        }

        // Need to use input parameter cellsToGuess!
        // Number of cells not given (cellsToGuess:38)
        boolean[][] hardcodedIsGiven = { { false, false, true, true, true, true, true, false, false },
                { false, false, false, true, false, true, true, false, true },
                { false, true, false, true, true, true, false, true, false },
                { true, false, false, false, true, false, false, false, true },
                { true, true, false, false, false, true, false, true, true },
                { true, false, false, true, true, true, false, false, true },
                { false, true, true, true, false, true, false, true, true },
                { false, false, false, true, true, true, false, true, true },
                { true, false, true, false, true, true, true, false, false } };

        // Copy from hardcodedIsGiven into array "isGiven"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                isGiven[row][col] = hardcodedIsGiven[row][col];
            }
        }
    }

    // (For advanced students) use singleton design pattern for this class
}