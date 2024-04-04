package Assignment_1;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TCP_Server {
    private static final int PORT = 1234; // Port number for the server
    private static final int NTHREADS = 100;
    private static final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);
    public static void main(String[] args) throws IOException {
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("TCP Server is running...");

            while (true) {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Start a new thread to handle the client
                Handle_Request each_client = new Handle_Request(clientSocket);
                exec.execute(each_client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}