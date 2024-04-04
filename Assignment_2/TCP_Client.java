package Assignment_2;
import java.io.*;
import java.net.*;

public class TCP_Client {
    private static final String SERVER_IP = "localhost"; // Server IP address
    private static final int SERVER_PORT = 1234; // Port number for the server

    public static void main(String[] args) {
        try {
            // Establish connection with the server
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            // Set up input and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read 3 integer from user 
            System.out.print("Enter the first integer (a): ");
            int a = Integer.parseInt(reader.readLine());
            System.out.print("Enter the second integer (b): ");
            int b = Integer.parseInt(reader.readLine());
            System.out.print("Enter the third integer (c): ");
            int c = Integer.parseInt(reader.readLine());

            // Send the 3 nums to the server
            outToServer.println(a);
            outToServer.println(b);
            outToServer.println(c);

            // Receive the LCM from server
            int lcm = Integer.parseInt(inFromServer.readLine());
            System.out.println("Received from server: Least Common Multiple (LCM) of " + a + " and " + b + " and "+ c + " is " + lcm);

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
