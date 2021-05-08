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

    private static String queuePath;

    public static List<String> run(QueueEntity queue, GameEntity game) throws IOException {
        // TODO: changed hardcoded variables
        //String envPath = "C:\\Users\\kwier\\Desktop\\studia\\Semestr VI\\GUPB\\venv\\Scripts\\python";

        QueueFolderCreator creator = new QueueFolderCreator(game.getControllerRelativePath(), game.getConfigRelativePath());
        queuePath = creator.createFor(queue, game);

        Path folder = Paths.get(queuePath, game.getGameRelativePath());
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
        System.out.println("Here is the standard error of the command (if any) - information about queue progress:\n");
        List<String> results = new ArrayList<>();
        while ((logs = stdError.readLine()) != null)
            System.out.println(logs);
        System.out.println("Here is the standard output of the command - information about bots placement:\n");
        while ((logs = stdInput.readLine()) != null) {
            System.out.println(logs);
            results.add(logs);
        }

        setQueueLogs(queue, game.getResultRelativePath());

        return results;
    }

    private static void setQueueLogs(QueueEntity queue, String resultRelativePath) {
        File resultsDir = new File(String.valueOf(Paths.get(queuePath, resultRelativePath)));
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
