package Assignment_3;
import java.io.*;
import java.net.*;

public class TCP_Client {
    private static final String SERVER_IP = "localhost"; // Server IP address
    private static final int SERVER_PORT = 12345; // Port number for the server

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);

            // Create a separate thread for receiving messages from the server
            Thread receiveThread = new Thread(() -> {
                try {
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String serverMessage;
                    while ((serverMessage = inFromServer.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                        // Check if server wants to end conversation
                        if (serverMessage.equalsIgnoreCase("bye")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            String clientMessage;
            while (true) {

                // Client inputs message to send to server
                System.out.print("Client: ");
                clientMessage = reader.readLine();
                outToServer.println(clientMessage);

                // Check if client wants to end conversation
                if (clientMessage.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
