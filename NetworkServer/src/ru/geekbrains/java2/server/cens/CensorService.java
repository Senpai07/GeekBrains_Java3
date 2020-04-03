package ru.geekbrains.java2.server.cens;

public interface CensorService {

    String checkMessage(String word);

    void start();

    void stop();
}
