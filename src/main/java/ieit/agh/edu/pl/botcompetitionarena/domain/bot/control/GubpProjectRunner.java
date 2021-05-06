package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.control.QueueFolderCreator;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.zeroturnaround.zip.ZipUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GubpProjectRunner {

    private final static String CONTROLLER_RELATIVE_PATH = "GUPB-master/gupb/controller"; //TODO
    private final static String CONFIG_RELATIVE_PATH = "GUPB-master/gupb/default_config.py"; //TODO
    private final static String GAME_RELATIVE_PATH = "GUPB-master/"; //TODO
    private final static String RESULTS_RELATIVE_PATH = "GUPB-master/results";
    private static String queuePath;
    public static List<String> run(QueueEntity queue, GameEntity game) throws IOException {
        // TODO: changed hardcoded variables
        //String envPath = "C:\\Users\\kwier\\Desktop\\studia\\Semestr VI\\GUPB\\venv\\Scripts\\python";
        QueueFolderCreator creator = new QueueFolderCreator(CONTROLLER_RELATIVE_PATH, CONFIG_RELATIVE_PATH);
        queuePath = creator.createFor(queue, game);
        Path folder = Paths.get(queuePath, GAME_RELATIVE_PATH);
        System.out.println(folder);
        File workingFolder = new File(folder.toString());
        ProcessBuilder requirementsProcessBuilder =
                new ProcessBuilder("python", "-m", "pip", "install", "-r", "requirements.txt");
        requirementsProcessBuilder.directory(workingFolder);
        Process requirementsProcess = requirementsProcessBuilder.start();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(requirementsProcess.getInputStream()));
        String logs;
        System.out.println("Here is the standard output of the command - " +
                "information about requirements installation:\n");
        while ((logs = stdInput.readLine()) != null) {
            System.out.println(logs);
        }
        ProcessBuilder gupbProcessBuilder = new ProcessBuilder("python", "-m", "gupb");
        gupbProcessBuilder.directory(workingFolder);
        Process gupbProcess = gupbProcessBuilder.start();
        stdInput = new BufferedReader(new InputStreamReader(gupbProcess.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(gupbProcess.getErrorStream()));
        System.out.println("Here is the standard output of the command - information on bots placement:\n");
        List<String> results = new ArrayList<>();
        String placementJson = "{\"id\":0,\"progress\":null,\"bots\":[";
        String[] placementJsonValues;
        while ((logs = stdInput.readLine()) != null) {
            placementJsonValues = logs.split("\\s+");
            if (!"{\"id\":0,\"progress\":null,\"bots\":[".equals(placementJson)) {
                placementJson += ",";
            }
            placementJson = placementJson + "{\"place\":" + prepareJsonValue(placementJsonValues[0])
                    + ",\"name\":\"" + prepareJsonValue(placementJsonValues[1]) + "\""
                    + ",\"points\":" + prepareJsonValue(placementJsonValues[2]) + "}";
            System.out.println(logs);
            results.add(logs);
        }
        placementJson += "],\"showClearLogsButton\":true}";
        System.out.println("Here is the standard error of the command (if any) - information on queue progress:\n");
        StringBuilder statusJsonString = new StringBuilder();
        String[] statusJsonValues;
        int statusesNo = 0;
        StatusLogsFileService.clearStatusLogFile();
        while ((logs = stdError.readLine()) != null) {
            if (logs.startsWith("Playing games:")) {
                statusJsonValues = logs.split("\\s+");
                String progress = statusJsonValues[2].replaceAll("\\|#*", "");
                statusJsonString.append("{\"id\":")
                        .append(++statusesNo)
                        .append(",\"progress\":\"")
                        .append(progress)
                        .append("\",\"bots\":[],\"showClearLogsButton\":false}");
                StatusLogsFileService.appendToFile(statusJsonString.toString());
                statusJsonString.setLength(0);
            }
            System.out.println(logs);
        }
        StatusLogsFileService.appendToFile(placementJson);
        setQueueLogs(queue);
        return results;
    }

    private static String prepareJsonValue(String value) {
        return value.substring(0, value.length() - 1);
    }

    private static void setQueueLogs(QueueEntity queue) {
        File resultsDir = new File(String.valueOf(Paths.get(queuePath, RESULTS_RELATIVE_PATH)));
        Optional<File> result = Arrays.stream(Objects.requireNonNull(resultsDir.listFiles(File::isFile)))
                .max(Comparator.comparingLong(File::lastModified));
        result.ifPresent(file -> {
            String zipFile = file.getAbsolutePath().concat(".zip");
            ZipUtil.packEntry(new File(file.getAbsolutePath()), new File(zipFile));
            try {
                queue.setLog(Files.readAllBytes(Paths.get(zipFile)));
            } catch (IOException e) {
                System.out.println("Could not upload logs to queue.");
                e.printStackTrace();
            }
        });
    }
}
