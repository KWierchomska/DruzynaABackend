package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class StatusLogsFileService {

    private static final String STATUS_LOGS_FILE_RELATIVE_PATH= "src\\main\\resources\\status.log";

    public static void appendToFile(String jsonString) throws IOException {
        System.out.println("Previous status.log deletion successful");
        FileWriter fileWriter = new FileWriter(STATUS_LOGS_FILE_RELATIVE_PATH, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonString);
        if (!"{\"id\":0,\"progress\":null,\"bots\":[".equals(jsonString.substring(0, 32))) {
            bufferedWriter.newLine();
        }
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

    public static String readLastLog() {
        File file = new File(STATUS_LOGS_FILE_RELATIVE_PATH);
        StringBuilder builder = new StringBuilder();
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            long fileLength = file.length() - 1;
            // Set the pointer at the last of the file
            randomAccessFile.seek(fileLength);
            for (long pointer = fileLength; pointer >= 0; pointer--) {
                randomAccessFile.seek(pointer);
                char c;
                // read from the last one char at the time
                c = (char) randomAccessFile.read();
                // break when end of the line
                if (c == '\n') {
                    break;
                }
                builder.append(c);
            }
            // Since line is read from the last so it
            // is in reverse so use reverse method to make it right
            builder.reverse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "{\"id\":0,\"progress\":\"0%\",\"bots\":[],\"showClearLogsButton\":false}";
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(randomAccessFile != null){
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

}
