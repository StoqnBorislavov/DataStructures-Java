package implementations;

public class TheMatrix {
    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {
        this.changeCharDfs(this.startRow, this.startCol);
    }

    private void changeCharDfs(Integer row, Integer col) {
        if (this.isInMatrix(row, col) && this.matrix[row][col] == this.toBeReplaced) {
            this.matrix[row][col] = this.fillChar;


            this.changeCharDfs(row - 1, col);

            this.changeCharDfs(row, col + 1);

            this.changeCharDfs(row + 1, col);

            this.changeCharDfs(row, col - 1);

        }

    }

    private boolean isInMatrix(Integer row, Integer col) {
        return row >= 0 && row < this.matrix.length && col >= 0 && col < this.matrix[row].length;
    }

    public String toOutputString() {
        StringBuilder builder = new StringBuilder();
        for (char[] chars : matrix) {
            for (char c : chars) {
                builder.append(c);
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString().trim();
    }
}
