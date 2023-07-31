package sh.fer.discordbot.infrastructure.configuration.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private static final Logger logger = LoggerFactory.getLogger(Log.class);

    public static void logInfo(String userName, String userId, String commandName, String serverName, String serverId) {
        logger.info("User '{}' (ID: {}) executed '{}' command in server '{}' (ID: {})",
                userName, userId, commandName, serverName, serverId);
    }

    public static void logError(String userName, String userId, String commandName) {
        logger.error("An error has occurred trying to execute command {}, User: {} ID: {}", commandName, userName, userId);
    }


}
