package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.control.QueueFolderCreator;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class GubpProjectRunner {

    public static List<String> run(QueueEntity queue, GameEntity game) throws IOException {
        // TODO: install requirements.txt automatically, create env
        String envPath = "C:\\Users\\kwier\\Desktop\\studia\\Semestr VI\\GUPB\\venv\\Scripts\\python"; // TODO
        ProcessBuilder pb = new ProcessBuilder("python", "-m", "pip", "install", "-r", "requirements.txt");

        String controllerRelativePath = "GUPB-master/gupb/controller"; //TODO
        String configRelativePath = "GUPB-master/gupb/default_config.py"; //TODO
        String gameRelativePath = "GUPB-master/"; //TODO

        QueueFolderCreator creator = new QueueFolderCreator(controllerRelativePath, configRelativePath);
        String queuePath = creator.createFor(queue, game);

        Path folder = Paths.get(queuePath, gameRelativePath);
        System.out.println(folder);
        File workingFolder = new File(folder.toString());

        pb.directory(workingFolder);
        Process proc = pb.start();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        System.out.println("Here is the standard error of the command (if any) - information about queue progress:\n");
        List<String> results = new ArrayList<>();
        String s;
        while ((s = stdError.readLine()) != null)
            System.out.println(s);
        System.out.println("Here is the standard output of the command - information about bots placement:\n");
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
            results.add(s);
        }
        System.out.println(proc.info());
        return results;
    }

}
