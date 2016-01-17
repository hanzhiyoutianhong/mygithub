package cc.linkedme.commons.log;

public class LogCollectorFactory {

    private static final ScribeLogCollector scribeLog = new ScribeLogCollector();

    public static LogCollector getLogCollector() {
        return scribeLog;
    }
}
