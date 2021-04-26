package ieit.agh.edu.pl.botcompetitionarena.domain.queue.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.bot.control.ConfigFileCreator;
import ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity.BotEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.entity.BotQueueAssignmentEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import net.lingala.zip4j.ZipFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class QueueFolderCreator {
    private final String basePath;
    private final String controllersRelativePath;
    private final String configRelativePath;


    /**
     * @param allGamesFolder by default /home/<user>/games,
     *                       folder where all running queues are stored
     */
    public QueueFolderCreator(String allGamesFolder, String controllersRelativePath, String configRelativePath) {
        this.basePath = allGamesFolder;
        this.controllersRelativePath = controllersRelativePath;
        this.configRelativePath = configRelativePath;
    }

    public QueueFolderCreator(String controllersRelativePath, String configRelativePath) {
        this.basePath = Paths.get(System.getProperty("user.home"), "games").toString();
        this.controllersRelativePath = controllersRelativePath;
        this.configRelativePath = configRelativePath;
    }

    public String createFor(QueueEntity queue) throws IOException {
        GameEntity game = queue.getGame();

        // main directory for this queue
        System.out.println("CREATING FOLDERS");
        Path queueFolder = Paths.get(basePath, fileName(queue.getName()));
        Files.createDirectories(queueFolder);

        // write game zip to folder, unzip, remove zip
        System.out.println("WRITING GAME TO:" + queueFolder);
        Path gameZipPath = Paths.get(queueFolder.toString(), fileName(game.getName()) + ".zip");
        unzipAndCleanUp(gameZipPath, queueFolder, game.getPayload());

        // load bots
        Path controllersPath = Paths.get(queueFolder.toString(), controllersRelativePath);
        List<BotEntity> bots = queue.getBots().stream()
                .map(BotQueueAssignmentEntity::getBot)
                .collect(Collectors.toList());

        for (BotEntity bot : bots) {
            Path botPath = Paths.get(controllersPath.toString(),
                    fileName(bot.getName()) + ".zip");
            System.out.println("WRITING BOT TO: " + botPath);
            unzipAndCleanUp(botPath, controllersPath, bot.getPayload());
        }

        // write config
        Path configPath = Paths.get(queueFolder.toString(), configRelativePath);
        System.out.println("WRITING CONFIG TO: " + configPath);
        ConfigFileCreator.create(
                configPath.toString(),
                bots.stream().map(BotEntity::getName).collect(Collectors.toList())
        );

        return queueFolder.toString();
    }

    private void unzipAndCleanUp(Path zipPath, Path destination, byte[] content) throws IOException {
        Files.write(zipPath, content);
        new ZipFile(zipPath.toString()).extractAll(destination.toString());
        Files.delete(zipPath);
    }

    private String fileName(String name) {
        return name.replaceAll("\\W+", "_");
    }
}
