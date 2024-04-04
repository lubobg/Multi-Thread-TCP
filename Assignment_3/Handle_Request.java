package Assignment_3;
import java.io.*;
import java.net.*;

public class Handle_Request implements Runnable {
    private Socket clientSocket;

    public Handle_Request(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader serverReader = new BufferedReader(new InputStreamReader(System.in));

            String clientMessage;
            String serverMessage;

            while (true) {
                // Receive message from client
                clientMessage = inFromClient.readLine();
                System.out.println("Client: " + clientMessage);
                
                // Check if client wants to end conversation
                if (clientMessage.equalsIgnoreCase("bye")) {
                    break;
                }

                // Server inputs message to send to client
                System.out.print("Server: ");
                serverMessage = serverReader.readLine();
                outToClient.println(serverMessage);

                // Check if server wants to end conversation
                if (serverMessage.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            // Close the client socket
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
