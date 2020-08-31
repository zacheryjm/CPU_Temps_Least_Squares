public class LeastSquaresSolver {

    /**
     * Finds solution for least squares regression for a given data set
     *
     * @param time An array of time values
     * @param temperatures An array of temperature values
     */
    public static double[] solve(double[] time, double[] temperatures) {

        double[] yMatrix = temperatures;
        double[][] xMatrix = buildXMatrix(time);
        double[][] xTransposeMatrix = buildXTransposeMatrix(xMatrix);

        double[][] xTx = multiplyXTX(xTransposeMatrix, xMatrix);
        double[] xTy = multiplyXTY(xTransposeMatrix, yMatrix);

        double[][] augmentedMatrix = buildAugmentedMatrix(xTx, xTy);

        for (int i = 0; i < augmentedMatrix.length; i++) {

            int rowToSwap = findLargestRowByColumn(augmentedMatrix, i);

            if (rowToSwap != i) {
                swapRows(i, rowToSwap, augmentedMatrix);
            }

            scaleRow(augmentedMatrix, i, augmentedMatrix[i][i]);
            eliminate(augmentedMatrix, i);

        }

        double[] solution = new double[augmentedMatrix.length];
        for (int k = 0; k < solution.length; k++) {
            solution[k] = augmentedMatrix[k][augmentedMatrix[0].length - 1];
        }

        return solution;
    }

    /**
     * Finds largest value in the current column and returns the index of that row
     *
     * @param augmentedMatrix The array created from XTX|XTY
     * @param column The column in which to find the largest row
     * @return largest row for given column
     */
    private static int findLargestRowByColumn(double[][] augmentedMatrix, int column) {
        double maxVal = augmentedMatrix[0][column];
        int startRow = column;
        int largestRow = startRow;

        for (int i = startRow; i < augmentedMatrix.length; i++) {
            if (augmentedMatrix[i][column] > maxVal) {
                maxVal = augmentedMatrix[i][column];
                largestRow = i;
            }
        }

        return largestRow;

    }

    /**
     * Swaps the given rows in the augmented matrix
     *
     * @param row1 int of first row to be swapped
     * @param row2 int of second row to be swapped
     * @param augmentedMatrix The array created from XTX|XTY
     */
    private static void swapRows(int row1, int row2, double[][] augmentedMatrix) {

        for (int j = 0; j < augmentedMatrix[0].length; j++) {
            double temp = augmentedMatrix[row1][j];
            augmentedMatrix[row1][j] = augmentedMatrix[row2][j];
            augmentedMatrix[row2][j] = temp;
        }
    }

    /**
     * Scales all values in the row based on the given scale factor
     *
     * @param augmentedMatrix The array created from XTX|XTY
     * @param rowToScale which row to be scaled
     * @param scaleFactor How much to scale the row by
     */
    private static void scaleRow(double[][] augmentedMatrix, int rowToScale, double scaleFactor) {

        for (int i = 0; i < augmentedMatrix[0].length; i++) {
            augmentedMatrix[rowToScale][i] = augmentedMatrix[rowToScale][i] / scaleFactor;
        }

    }

    /**
     * Eliminates all values in current column by scale factor and then updates all values in each
     * row by that row's scale factor
     *
     * @param augmentedMatrix The array created from XTX|XTY
     * @param startingColumn column in which to start
     */
    private static void eliminate(double[][] augmentedMatrix, int startingColumn) {
        int row = startingColumn;

        for (int i = 0; i < augmentedMatrix.length; i++) {
            if (i == startingColumn) {
                continue;
            }

            double scaleFactor = augmentedMatrix[i][startingColumn];

            for (int j = startingColumn; j < augmentedMatrix[0].length; j++) {

                augmentedMatrix[i][j] = augmentedMatrix[i][j] - (scaleFactor * augmentedMatrix[row][j]);
            }

            augmentedMatrix[i][startingColumn] = 0;
        }

    }

    /**
     * Builds the X matrix with given values
     *
     * @param xValues The time values given
     * @return the X matrix
     */
    private static double[][] buildXMatrix(double[] xValues) {

        double[][] matrix = new double[xValues.length][2];

        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = 1;
            matrix[i][1] = xValues[i];
        }

        return matrix;
    }

    /**
     * Builds the X transpose matrix from X matrix
     *
     * @param xMatrix The matrix to perform the transpose
     * @return the X transpose matrix
     */
    private static double[][] buildXTransposeMatrix(double[][] xMatrix) {

        double[][] matrix = new double[xMatrix[0].length][xMatrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = xMatrix[j][i];
            }
        }

        return matrix;
    }

    /**
     * Builds the Augmented matrix from XTX|XTY matricies
     *
     * @param xMatrix The XTX matrix
     * @param yMatrix The XTY matrix
     * @return the Augmented matrix
     */
    private static double[][] buildAugmentedMatrix(double[][] xMatrix, double[] yMatrix) {

        double[][] matrix = new double[xMatrix.length][xMatrix[0].length + 1];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length - 1; j++) {
                matrix[i][j] = xMatrix[i][j];
            }
            matrix[i][matrix[0].length - 1] = yMatrix[i];
        }

        return matrix;
    }

    /**
     * Builds the XTX matrix
     *
     * @param xTransposeMatrix The X transpose matrix
     * @param xMatrix The x matrix
     * @return the XTX matrix
     */
    private static double[][] multiplyXTX(double[][] xTransposeMatrix, double[][] xMatrix) {

        double[][] matrix = new double[xTransposeMatrix.length][xMatrix[0].length];

        for (int i = 0; i < xTransposeMatrix.length; i++) {
            for (int j = 0; j < xMatrix[0].length; j++) {
                for (int k = 0; k < xMatrix.length; k++) {
                    matrix[i][j] += xTransposeMatrix[i][k] * xMatrix[k][j];
                }
            }
        }

        return matrix;
    }

    /**
     * Builds the XTY matrix
     *
     * @param xTransposeMatrix The X transpose matrix
     * @param yMatrix The y matrix
     * @return the XTY matrix
     */
    private static double[] multiplyXTY(double[][] xTransposeMatrix, double[] yMatrix) {

        double[] matrix = new double[xTransposeMatrix.length];

        for (int i = 0; i < xTransposeMatrix.length; i++) {
            for (int j = 0; j < yMatrix.length; j++) {
                matrix[i] += xTransposeMatrix[i][j] * yMatrix[j];
            }
        }

        return matrix;
    }
}


