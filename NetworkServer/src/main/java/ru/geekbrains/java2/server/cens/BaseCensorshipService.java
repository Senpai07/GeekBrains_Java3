package ru.geekbrains.java2.server.cens;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class BaseCensorshipService implements CensorService {

    public static final Logger LOGGER = LogManager.getLogger(BaseCensorshipService.class);
    private static final List<String> CENS_DATA =
            List.of("BOMB", "KILL", "SUCK");
    public static final String REPLACE_SYMBOL = "*";

    @Override
    public String checkMessage(String textLine) {
        String[] split = textLine.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String str : split) {
            String tmpString = str.replaceAll("[^A-Za-z0-9]", "");
            if (CENS_DATA.contains(tmpString.toUpperCase())) {
                str = REPLACE_SYMBOL.repeat(str.length());
            }
            result.append(str).append(" ");
        }
        return result.toString().trim();
    }

    @Override
    public void start() {
        LOGGER.info("Сервис цензуры запущен");
    }

    @Override
    public void stop() {
        LOGGER.info("Сервис цензуры оставлен");
    }

}
