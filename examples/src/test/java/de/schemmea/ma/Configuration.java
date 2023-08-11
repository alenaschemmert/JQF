package de.schemmea.ma;

public final class Configuration {
    public static final String SOURCE_PATH = "/nextflow/";
    public static final String TEMPLATES = "templates/";
    public static final String OUTPUT_PATH = "generatedflows/";
    public static final String ERROR_DIR = "errorDir/";
    public static final String DATA = "data/";
    public static final String TEMPLATE_SOURCE_PATH = SOURCE_PATH + TEMPLATES;
    public static final String EXCEPTION_LOG_FILE = ERROR_DIR + "/exception_log.csv";
    public static final String DATA_SOURCE_PATH = SOURCE_PATH + DATA;
    public static final String OUTPUT_TEMPLATE_PATH = OUTPUT_PATH + TEMPLATES;
    public static final String OUTPUT_DATA_PATH = OUTPUT_PATH + DATA;

    public static final String newline = System.getProperty("line.separator");

}
