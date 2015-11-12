package log;

import bot.BotStarter;
import bot.PathFinder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnunderwood on 7/11/15.
 */
public class LogConfig {
    private static LogConfig instance = null;
    private LogLevel rootLevel = LogLevel.DEBUG;
    private Map<String, LogLevel> loggerLevels = new HashMap<>();

    private LogConfig() {
        loggerLevels.put(BotStarter.class.getSimpleName(), LogLevel.DEBUG);
        loggerLevels.put(PathFinder.class.getSimpleName(), LogLevel.DEBUG);
    }

    public static synchronized LogConfig getInstance() {
        if (instance == null) {
            instance = new LogConfig();
        }

        return instance;
    }

    public LogLevel getLoggerLevel(String loggerName) {
        if (loggerLevels.containsKey(loggerName)) {
            return loggerLevels.get(loggerName);
        } else {
            return rootLevel;
        }
    }

    public void setLoggerLevel(String name, LogLevel level) {
        loggerLevels.put(name, level);
    }
}
