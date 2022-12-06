package server;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_RED = "\u001B[31m";

    public static final String ANSI_YELLOW = "\u001B[33m";

    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";

    public static final String ANSI_BLUE = "\u001B[34m";

    public static final String ANSI_PURPLE = "\u001B[35m";

    public static final String ANSI_CYAN = "\u001B[94m";

    public static final String ANSI_WHITE = "\u001B[97m";

    public static void initLogging() {
        InputStream stream = LogFormatter.class.getClassLoader().getResourceAsStream("logging.properties");

        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();

        builder.append(ANSI_YELLOW);

        builder.append("[");
        builder.append(ANSI_PURPLE);
        builder.append(calcDate(record.getMillis()));
        builder.append(ANSI_YELLOW);
        builder.append("]");

        builder.append(" [");
        builder.append(ANSI_CYAN);
        builder.append(record.getSourceClassName());
        builder.append(ANSI_YELLOW);
        builder.append("]");

        builder.append(" [");
        if (record.getLevel() == Level.INFO) {
            builder.append(ANSI_BLUE);
        } else if (record.getLevel() == Level.WARNING) {
            builder.append(ANSI_BRIGHT_YELLOW);
        } else if (record.getLevel() == Level.SEVERE) {
            builder.append(ANSI_RED);
        }
        builder.append(record.getLevel().getName());
        builder.append(ANSI_YELLOW);
        builder.append("] ");

        builder.append(ANSI_WHITE);
        builder.append(record.getMessage());

        Object[] params = record.getParameters();

        if (params != null) {
            builder.append("\t");
            for (int i = 0; i < params.length; i++) {
                builder.append(params[i]);
                if (i < params.length - 1)
                    builder.append(", ");
            }
        }

        builder.append("\n");
        builder.append(ANSI_RESET);
        return builder.toString();
    }

    private String calcDate(long ms) {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(ms));
    }
}