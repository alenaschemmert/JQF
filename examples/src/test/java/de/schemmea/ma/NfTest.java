package de.schemmea.ma;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import nextflow.cli.CmdRun;
import nextflow.cli.Launcher;
import nextflow.plugin.Plugins;

import org.junit.Assume;
import org.junit.runner.RunWith;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static de.schemmea.ma.Util.getFileName;

@RunWith(JQF.class)
public class NfTest {


    private void serializeInputStream(InputStream in, String filename) throws IOException {
        Path path = Paths.get(filename);
        try (BufferedWriter out = Files.newBufferedWriter(path)) {
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
        }
    }

    @Fuzz
    public void testAFL(InputStream inputStream) throws IOException {
        /*
         * install afl
         * https://medium.com/@ayushpriya10/fuzzing-applications-with-american-fuzzy-lop-afl-54facc65d102
         * https://www.dannyvanheumen.nl/post/java-fuzzing-with-afl-and-jqf/
         */
        String filename = getFileName();
        try {
            serializeInputStream(inputStream, filename);

            List<String> args2 = List.of(filename);
            String[] orig_args2 = new String[]{"run", filename};

            Launcher launcher = new Launcher().command(orig_args2);//.run();

            CmdRun myRunner = new CmdRun();
            myRunner.setArgs(args2);
            myRunner.setLauncher(launcher);

            myRunner.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Throwable t) {
            Assume.assumeNoException(t);
        } finally {

            //instead of @After
            Plugins.stop();
            Files.delete(Paths.get(filename));
            //nextflow clean -f does not work?!
            //  int status = new Launcher().command(new String[]{"clean", "-f"}).run();
        }
    }


    @Fuzz
    public void testTest() throws IOException {

        String filename = "/home/alena/source/JQF/examples/target/seeds/nextflow/hello.nf";

        testAFL(Files.newInputStream(Paths.get(filename)));
    }

}

