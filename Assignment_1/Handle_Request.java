package Assignment_1;
import java.io.*;
import java.net.*;

public class Handle_Request implements Runnable {
    private Socket clientSocket;

    public Handle_Request(Socket socket) {
        this.clientSocket = socket;
    }
    
    public void run() {
        try {
            // Setup communication streams
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read string from client
            String receivedString = inFromClient.readLine();
            System.out.println("Received from client: " + receivedString);

            // Convert the string to uppercase
            String upperCaseString = receivedString.toUpperCase();

            // Send the uppercase string back to the client
            outToClient.println(upperCaseString);

            // Close the client socket
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
