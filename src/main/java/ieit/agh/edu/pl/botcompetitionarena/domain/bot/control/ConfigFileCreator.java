package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigFileCreator {

    public static void create(List<String> botProjectNames) {
        try
        {
            Files.deleteIfExists(Paths.get("src\\main\\resources\\gupbapp\\GUPB\\gupb\\default_config.py"));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists when searching for default_config.py file");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions to delete old and create new default_config.py file");
        }
        System.out.println("Previous config file deletion successful");
        try {
            File file = new File("src\\main\\resources\\gupbapp\\GUPB\\gupb\\default_config.py");
            if (file.createNewFile()) {
                System.out.println("File: " + file.getName() + " created");
            } else {
                System.out.println("File " + file.getName() + " already exist. Please remove it first.");
                return;
            }
            FileWriter fileWriter = new FileWriter("src\\main\\resources\\gupbapp\\GUPB\\gupb\\default_config.py");
            fileWriter.write(
                    botProjectNames.stream().map(projectName -> "from gupb.controller import " + projectName)
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
                    "        " + String.join(".BotController(\"nazwa_bota\"),\n        ", botProjectNames) + ".BotController(\"nazwa_bota\"),\n" +
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

}
