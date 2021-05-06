package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class GubpProjectRunner {

    public static void run() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("python", "-m", "gupb");
        File workingFolder = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\gupbapp\\GUPB");
        pb.directory(workingFolder);
        Process proc = pb.start();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        System.out.println("Here is the standard output of the command - information on bots placement:\n");
        String s;
        String placementJson = "{\"id\":0,\"progress\":null,\"bots\":[";
        String[] placementJsonValues;
        while ((s = stdInput.readLine()) != null) {
            placementJsonValues = s.split("\\s+");
            if (!"{\"id\":0,\"progress\":null,\"bots\":[".equals(placementJson)) {
                placementJson += ",";
            }
            placementJson = placementJson + "{\"place\":" + prepareJsonValue(placementJsonValues[0])
                    + ",\"name\":\"" + prepareJsonValue(placementJsonValues[1]) + "\""
                    + ",\"points\":" + prepareJsonValue(placementJsonValues[2]) + "}";
            System.out.println(s);
        }
        placementJson += "],\"showClearLogsButton\":true}";
        System.out.println("Here is the standard error of the command (if any) - information on queue progress:\n");
        StringBuilder statusJsonString = new StringBuilder();
        String[] statusJsonValues;
        int statusesNo = 0;
        StatusLogsFileService.clearStatusLogFile();
        while ((s = stdError.readLine()) != null) {
            if (s.startsWith("Playing games:")) {
                statusJsonValues = s.split("\\s+");
                String progress = statusJsonValues[2].replaceAll("\\|#*", "");
                statusJsonString.append("{\"id\":")
                        .append(++statusesNo)
                        .append(",\"progress\":\"")
                        .append(progress)
                        .append("\",\"bots\":[],\"showClearLogsButton\":false}");
                StatusLogsFileService.appendToFile(statusJsonString.toString());
                statusJsonString.setLength(0);
            }
            System.out.println(s);
        }
        StatusLogsFileService.appendToFile(placementJson);
    }

    private static String prepareJsonValue(String value) {
        return value.substring(0, value.length() - 1);
    }

}
