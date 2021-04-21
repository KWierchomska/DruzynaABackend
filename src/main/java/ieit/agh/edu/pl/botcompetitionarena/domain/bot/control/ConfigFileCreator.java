package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigFileCreator {

    public static void create(List<String> botProjectNames) {
        try {
            File file = new File("src\\main\\resources\\gupbapp\\GUPB\\gupb\\default_config6.py");
            if (file.createNewFile()) {
                System.out.println("File: " + file.getName() + " created");
            } else {
                System.out.println("File " + file.getName() + " already exist. Please remove it first.");
                return;
            }
            FileWriter fileWriter = new FileWriter("src\\main\\resources\\gupbapp\\GUPB\\gupb\\default_config6.py");
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
                    "        " + String.join(".BotController(\"nazwa_bota\"),\n        ", botProjectNames) + "\n" +
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
