package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.control.QueueFolderCreator;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.control.QueueRepository;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeroturnaround.zip.ZipUtil;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class GubpProjectRunner {

    private static QueueRepository repository;

    @Autowired
    private QueueRepository queueRepository;

    @PostConstruct
    public void init() {
        this.repository = queueRepository;
    }

    private static String queuePath;

    public static List<String> run(QueueEntity queue, GameEntity game) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        QueueFolderCreator creator = new QueueFolderCreator(game.getControllerRelativePath(),
                game.getConfigRelativePath());
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

        ProcessBuilder gupbProcessBuilder = new ProcessBuilder(game.getRunCommand().split(" "));
        gupbProcessBuilder.directory(workingFolder);
        Process gupbProcess = gupbProcessBuilder.start();
        stdInput = new BufferedReader(new InputStreamReader(gupbProcess.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(gupbProcess.getErrorStream()));
        System.out.println("Here is the standard error of the command (if any) - information about queue progress:\n");
        List<String> results = new ArrayList<>();
        while ((logs = stdError.readLine()) != null) {
            System.out.println(logs);
            String finalLogs = logs;
            Runnable task = () -> {
                queue.setLastStatus(finalLogs);
                repository.saveAndFlush(queue);
            };
            executorService.execute(task);
        }
        System.out.println("Here is the standard output of the command - information about bots placement:\n");
        while ((logs = stdInput.readLine()) != null) {
            String[] splittedLogs = logs.split("\\s+");
            String botId = splittedLogs[1].replace(":", "");
            String botPlacement = splittedLogs[0].replace(".", "");
            String finalLogs = logs;
            queue.getBots().forEach(botQueueAssignmentEntity -> {
                if (botQueueAssignmentEntity.getBot().getId().equals(Long.parseLong(botId))) {
                    results.add(finalLogs.replace(botId + ":",
                            botQueueAssignmentEntity.getBot().getName() + ":"));
                    botQueueAssignmentEntity.setPlace(Integer.parseInt(botPlacement));
                }
            });
        }
        System.out.println(results);

        queue.setLastStatus(results.toString());

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
