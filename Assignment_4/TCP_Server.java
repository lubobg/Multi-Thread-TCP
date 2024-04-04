package Assignment_4;
import java.io.*;
import java.net.*;
import java.util.*;

public class TCP_Server {
    private static final int PORT = 12345;
    private static Set<Socket> clientSockets = new HashSet<>();
    private static final Object lock = new Object();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                synchronized (lock) {
                    clientSockets.add(clientSocket);
                }
                System.out.println("New client connected: " + clientSocket);

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received from client " + clientSocket + ": " + inputLine);
                    sendToAllClients(inputLine);
                }

                synchronized (lock) {
                    clientSockets.remove(clientSocket);
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendToAllClients(String message) {
            synchronized (lock) {
                for (Socket socket : clientSockets) {
                    if (socket != clientSocket) {
                        try {
                            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                            writer.println(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
