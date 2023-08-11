package de.schemmea.ma;

import java.io.File;
import java.nio.file.Paths;

public final class Util {


    public static String getFileName() {
        long date = System.currentTimeMillis();
        File generated = new File(Paths.get(Configuration.OUTPUT_PATH).toUri());
        if (!generated.exists()) {
            generated.mkdir();
        }

        return generated.getAbsolutePath() + "/out" + date + ".nf";
    }

}
