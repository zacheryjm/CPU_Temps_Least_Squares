import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SolverDriver {

    /**
     * Prints the results of piecewise interpolation and Least squares reg to a file for each core
     *
     * @param allTheTemps used to pass in all of the core temperature data
     *
     * @throws IOException If function fails to write to file
     */
    public static void getSolutions(List<TemperatureParser.CoreTempReading> allTheTemps) {

        int numCores = allTheTemps.get(0).readings.length;
        double[] times = getArrayForTimes(allTheTemps);

        for(int i = 0; i < numCores; i++) {
            double [] temperatureValues = getArrayForIndividualCore(i, allTheTemps);
            String filename = "Evaluation-core-" + i + ".txt";

            try {
                FileWriter myWriter = new FileWriter(filename);
                myWriter.write(solveLeastSquares(times, temperatureValues));
                myWriter.write("\n");

                for(int j = 0; j < times.length-1; j++) {
                    myWriter.write(solveInterpolation(times, temperatureValues, j));
                    myWriter.write("\n");
                }
                myWriter.close();

            } catch (IOException e) {
                System.out.println("Error writing to file");
                e.printStackTrace();
            }
        }

    }

    /**
     * Creates and returns an array that contains the temperatures for a given core
     *
     * @param core used to pass in which core the data pertains to
     * @param allTheTemps used to pass in all of the core temperature data
     * @return An Array of temperatures for the given core
     */
    private static double[] getArrayForIndividualCore (int core, List<TemperatureParser.CoreTempReading> allTheTemps) {

        double[] temps = new double[allTheTemps.size()];

        for (int i = 0; i < temps.length; i++) {
            temps[i] = allTheTemps.get(i).readings[core];
        }

        return temps;

    }

    /**
     * Creates and returns an array that contains the times the temperatures were taken
     *
     * @param allTheTemps used to pass in all of the core temperature data
     * @return An array containing all times a temperature was taken
     */
    private static double[] getArrayForTimes (List<TemperatureParser.CoreTempReading> allTheTemps) {

        double[] times = new double[allTheTemps.size()];

        for (int i = 0; i < times.length; i++) {
            times[i] = allTheTemps.get(i).step;
        }

        return times;

    }

    /**
     * Take an input of the times and temperature values for a core
     *
     * @param times Array of times in which temp readings were taken
     * @param values Array of temperatures for a core at each time interval
     * @return a string representation of least square approximation
     */
    private static String solveLeastSquares(double [] times, double [] values) {
        double [] solution = LeastSquaresSolver.solve(times, values);

        String formattedSolutionString = String.format("%8.0f <= x < %8.0f = %15.5f      + %15.5fx; Least Squares Approximation",
                times[0], times[times.length-1], solution[0], solution[1]);

        return formattedSolutionString;
    }

    /**
     * Take an input of the times and temperature values for a core
     *
     * @param times Array of times in which temp readings were taken
     * @param values Array of temperatures for a core at each time interval
     * @param index Index value representing the current time interval to calculate
     * @return a string representation of interpolation
     */
    private static String solveInterpolation(double[] times, double [] values, int index) {
        double [] solution = NewtonSolver.solve(index, times, values);

        String formattedSolutionString = String.format("%8.0f <= x < %8.0f = %15.5f      + %15.5fx; Interpolation",
                times[index], times[index+1], solution[0], solution[1]);

        return formattedSolutionString;
    }



}
