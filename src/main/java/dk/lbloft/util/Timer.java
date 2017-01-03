package dk.lbloft.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Timer {
    private static Logger logger = LoggerFactory.getLogger(Timer.class);

    private long startTime;

    private Timer(long time) {
        startTime = time;
    }

    public static Timer start() {
        return new Timer(System.currentTimeMillis());
    }

    public long getTime() {
        return System.currentTimeMillis() - startTime;
    }

    public void printTime() {
        logger.info("Elapsed Time: " + new TimeAndUnit(getTime()).toString());
    }
}