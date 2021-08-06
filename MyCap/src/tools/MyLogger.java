package tools;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {

    private final Logger log;
    FileHandler fileHandler;

    public MyLogger(String name) throws IOException {
        this.log = Logger.getLogger(name);
        log.setLevel(Level.ALL);
        fileHandler = new FileHandler(name + ".log");
        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(new LogFormatter());
        log.addHandler(fileHandler);
    }

    public void info(String msg) {
        log.info(msg);
    }

    public void warning(String msg) {
        log.warning(msg);
    }

    public void removeFileLocked() {
        log.removeHandler(fileHandler);
        FileHelper.deleteFile(log.getName() + ".log.lck");
    }


}
