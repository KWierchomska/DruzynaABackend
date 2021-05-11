package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity.BotEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigFileCreator {

    public static void create(String configPath, List<BotEntity> bots) {
        // "src\\main\\resources\\gupbapp\\GUPB\\gupb\\default_config.py"
        try {
            Files.deleteIfExists(Paths.get(configPath));
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists when searching for default_config.py file");
        } catch (IOException e) {
            System.out.println("Invalid permissions to delete old and create new default_config.py file");
        }
        System.out.println("Previous config file deletion successful");
        try {
            File file = new File(configPath);
            if (file.createNewFile()) {
                System.out.println("File: " + file.getName() + " created");
            } else {
                System.out.println("File " + file.getName() + " already exist. Please remove it first.");
                return;
            }
            FileWriter fileWriter = new FileWriter(configPath);
            fileWriter.write(
                    bots.stream().map(bot -> "from gupb.controller import " + bot.getName())
                            .collect(Collectors.joining("\n")) +
                            "\n\n" +
                            "CONFIGURATION = {\n" +
                            "    'arenas': [\n" +
                            "        'archipelago',\n" +
                            "        'wasteland',\n" +
                            "        'dungeon',\n" +
                            "        'fisher_island',\n" +
                            "    ],\n" +
                            "    'controllers': [\n" +
                            bots.stream().map(ConfigFileCreator::botName).collect(Collectors.joining("")) +
                            "    ],\n" +
                            "    'start_balancing': False,\n" +
                            "    'visualise': False,\n" +
                            "    'show_sight': None,\n" +
                            "    'runs_no': 2,\n" +
                            "    'profiling_metrics': [],  # possible metrics ['all', 'total', 'avg']\n" +
                            "}\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error when creating config file occurred");
            e.printStackTrace();
        }
    }

    public static void createReal(QueueEntity queue, String configPath, List<BotEntity> bots) {
        try {
            Files.deleteIfExists(Paths.get(configPath));
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists when searching for default_config.py file");
        } catch (IOException e) {
            System.out.println("Invalid permissions to delete old and create new default_config.py file");
        }
        System.out.println("Previous config file deletion successful");

        /*
        #BOTS# - oznacza gdzie wstawic boty
         */

        String config = new String(queue.getConfig());
        config = bots.stream().map(bot -> "from gupb.controller import " + bot.getName())
                .collect(Collectors.joining("\n")) +
                "\n" +
                config.replace("#BOTS#", bots.stream().map(ConfigFileCreator::botNameWithoutIndent)
                        .collect(Collectors.joining("")));

        try {
            Files.write(Paths.get(configPath), config.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("An error when creating config file occurred");
            e.printStackTrace();
        }
    }

    private static String botNameWithoutIndent(BotEntity bot) {
        return bot.getName() + ".BotController(\"" + bot.getId() + "\"),";
    }

    private static String botName(BotEntity bot) {
        return "        " + botNameWithoutIndent(bot) + "\n";
    }
}
