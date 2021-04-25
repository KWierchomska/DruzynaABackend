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
        System.out.println("Here is the standard output of the command - information about bots placement:\n");
        String s;
        while ((s = stdInput.readLine()) != null)
            System.out.println(s);
        System.out.println("Here is the standard error of the command (if any) - information about queue progress:\n");
        while ((s = stdError.readLine()) != null)
            System.out.println(s);
    }

}
