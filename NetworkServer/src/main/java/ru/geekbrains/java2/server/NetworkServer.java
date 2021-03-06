package ru.geekbrains.java2.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.server.auth.AuthService;
import ru.geekbrains.java2.server.auth.DBAuthService;
import ru.geekbrains.java2.server.cens.BaseCensorshipService;
import ru.geekbrains.java2.server.cens.CensorService;
import ru.geekbrains.java2.server.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkServer {

    private static final Logger LOGGER = LogManager.getLogger(NetworkServer.class);

    private final int port;
    private final List<ClientHandler> clients = new ArrayList<>();
    //  private final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    //  private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private final AuthService authService;
    private final CensorService censorService;


    public NetworkServer(int port) {
        this.port = port;
        this.authService = new DBAuthService();
        this.censorService = new BaseCensorshipService();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Сервер был успешно запущен на порту " + port);
            authService.start();
            censorService.start();
            //noinspection InfiniteLoopStatement
            while (true) {
                LOGGER.info("Ожидание клиентского подключения...");
                Socket clientSocket = serverSocket.accept();
                LOGGER.info("Клиент подключился");
                createClientHandler(clientSocket);
            }
        } catch (IOException e) {
            LOGGER.error("Ошибка при работе сервера");
            e.printStackTrace();
        } finally {
            authService.stop();
            censorService.stop();
        }
    }

    private void createClientHandler(Socket clientSocket) {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.run();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public CensorService getCensorService() {
        return censorService;
    }

    public synchronized void broadcastMessage(Command message, ClientHandler owner)
            throws IOException {
        for (ClientHandler client : clients) {
            if (client != owner) client.sendMessage(message);
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        clients.add(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {
        clients.remove(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    public synchronized void sendMessage(String receiver, Command commandMessage) throws IOException {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(receiver)) {
                client.sendMessage(commandMessage);
                break;
            }
        }
    }

    public List<String> getAllUsernames() {
        return clients.stream()
                .map(ClientHandler::getNickname)
                .collect(Collectors.toList());
//        List<String> usernames = new LinkedList<>();
//        for (ClientHandler clientHandler : clients) {
//            usernames.add(clientHandler.getNickname());
//        }
//        return usernames;
    }

    public boolean isNicknameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void changeNickname(String newNickname, ClientHandler clientHandler) {
        authService.changNickname(clientHandler.getNickname(), newNickname);
    }
}
