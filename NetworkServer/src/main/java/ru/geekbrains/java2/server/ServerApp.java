package ru.geekbrains.java2.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ServerApp {
    private static final Logger LOGGER = LogManager.getLogger(ServerApp.class);

    private static final int DEFAULT_PORT = 8189;

    public static void main(String[] args) {
        int port = getServerPort(args);
        new NetworkServer(port).start();
    }

    private static int getServerPort(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                LOGGER.error("Некорректный формат порта, будет использоваться порт по умолчанию");
            }
        }
        return port;
    }
}
