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


public class GubpProjectRunner {

    public static void run(QueueEntity queue, GameEntity game) throws IOException {
        // TODO: install requirements.txt automatically, create env
        String envPath = "/home/zbsss/anaconda3/envs/gupb/bin/python"; // TODO
        ProcessBuilder pb = new ProcessBuilder("python -m gupb");

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
        System.out.println("Here is the standard output of the command - information about bots placement:\n");
        String s;
        while ((s = stdInput.readLine()) != null)
            System.out.println(s);
        System.out.println("Here is the standard error of the command (if any) - information about queue progress:\n");
        while ((s = stdError.readLine()) != null)
            System.out.println(s);
    }

}
