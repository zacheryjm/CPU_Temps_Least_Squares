import java.io.*;
import java.util.List;

public class Application {

    /**
     * The main function that drives the application
     *
     * @param args used to pass in a single filename
     *
     * @throws ArrayIndexOutOfBoundsException If args contains no values
     * @throws FileNotFoundException If no file is provided by the user
     */
    public static void main(String[] args) {
        BufferedReader tFileStream = null;

        // Parse command line argument 1
        try {
            tFileStream = new BufferedReader(new FileReader(new File(args[0])));
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array out of bounds");
            System.out.println(e);
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find file");
            System.out.println(e);
        }

        TemperatureParser temperatureParser = new TemperatureParser();
        List<TemperatureParser.CoreTempReading> allTheTemps = temperatureParser.parseRawTemps(tFileStream);

        SolverDriver.getSolutions(allTheTemps);

    }
}
