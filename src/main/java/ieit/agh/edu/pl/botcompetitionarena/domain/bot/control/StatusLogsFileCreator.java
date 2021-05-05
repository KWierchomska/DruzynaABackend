package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class StatusLogsFileCreator {

    private static final String STATUS_LOGS_FILE_RELATIVE_PATH= "src\\main\\resources\\status.log";

    public static void appendToFile(String jsonString) throws IOException {
        System.out.println("Previous status.log deletion successful");
        FileWriter fileWriter = new FileWriter(STATUS_LOGS_FILE_RELATIVE_PATH, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonString);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    public static void clearStatusLogFile() {
        try {
            Files.deleteIfExists(Paths.get(STATUS_LOGS_FILE_RELATIVE_PATH));
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists when searching for status.log file");
        } catch (IOException e) {
            System.out.println("Invalid permissions to delete old and create new status.log file");
        }
    }

}
