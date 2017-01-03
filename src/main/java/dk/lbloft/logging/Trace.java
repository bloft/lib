package dk.lbloft.logging;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;


public class Trace {
    private static Logger logger = LoggerFactory.getLogger(Trace.class);

    public static List<StackTraceElement> getStackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = stackTrace.length-1; i > 0; i--) {
            if("Trace".equals(stackTrace[i].getClassName())) {
                return Lists.newArrayList(Arrays.copyOfRange(stackTrace, i + 1, stackTrace.length));
            }
        }
        return null;
    }

    public static void printStackTrace() {
        System.out.println("StackTrace: ");
        for (StackTraceElement stackTraceElement : getStackTrace()) {
            System.out.println("  " + stackTraceElement);
        }
    }

    public static StackTraceElement currentFileAndLine() {
        return getStackTrace().get(0);
    }

    public static StackTraceElement calledFrom() {
        return getStackTrace().get(1);
    }
}