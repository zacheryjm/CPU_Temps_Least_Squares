public class NewtonSolver {

    /**
     * Finds the y-intercept and slope for values
     * at given index in time and temps arrays
     *
     * @param i The index for the time/temp data
     * @param temps The temperature data
     * @param time The time steps
     * @return An array with y-intercept and slope
     */
    public static double [] solve(int i, double[] temps, double[] time) {
        double slope = findSlope(i, temps, time);
        double yInt = findYint(i, temps, time, slope);

        return new double[]{yInt, slope};

    }

    /**
     * Finds the slope for the points at the given index
     *
     * @param i The index for the time/temp data
     * @param temps The temperature data
     * @param time The time steps
     * @return The slope
     */
    private static double findSlope(int i, double[] temps, double[] time) {

        double slope = (temps[i + 1] - temps[i]) / (time[i + 1] - time[i]);
        return slope;
    }

    /**
     * Finds the slope for the points at the given index
     *
     * @param i The index for the time/temp data
     * @param temps The temperature data
     * @param time The time steps
     * @param slope The slope for given line
     * @return The y-intercept
     */
    private static double findYint(int i, double[] temps, double[] time, double slope) {

        double yInt = (temps[i] - (slope * time[i]));
        return yInt;
    }
}
