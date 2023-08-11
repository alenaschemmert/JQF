package de.schemmea.ma;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import nextflow.cli.CmdRun;
import nextflow.cli.Launcher;
import nextflow.plugin.Plugins;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static de.schemmea.ma.Util.getFileName;

@RunWith(JQF.class)
class NfTest {


    static int iteration = 0;


    @Before
    public void setup() {

    }

    static File currentFile = null;

    @Fuzz
    public void testWorkflow(File inputFile) {
        System.out.println("ITERATION " + ++iteration + "\n");
        currentFile = inputFile;

        String filename = inputFile.getAbsolutePath();
        String[] orig_args2 = new String[]{"run", filename};
        List<String> args2 = List.of(filename);

        Launcher launcher = new Launcher().command(orig_args2);//.run();

        CmdRun myRunner = new CmdRun();
        myRunner.setArgs(args2);
        myRunner.setLauncher(launcher);

        myRunner.run();
    }

    @Fuzz
    public void testAFL(InputStream inputStream) {
        /**
         * install afl
         * https://medium.com/@ayushpriya10/fuzzing-applications-with-american-fuzzy-lop-afl-54facc65d102
         */
        String filename = getFileName();
        File file = new File(filename);

        byte[] buffer = new byte[1024];

        try (OutputStream outputStream = new FileOutputStream(file, false)) {
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            /**ignore**/
        }

        testWorkflow(file);
    }


    @After
    public void cleanUp() {
        //plugins won't stop after sriptcompilation exception
        Plugins.stop();

        if (currentFile != null) {
            currentFile.delete();
        }

        //nextflow clean -f
        int status = new Launcher().command(new String[]{"clean", "-f"}).run();
    }

}

