package Assignment_2;
import java.io.*;
import java.net.*;

public class Handle_Request implements Runnable {
    private Socket clientSocket;

    public Handle_Request(Socket socket) {
        this.clientSocket = socket;
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // Function to find the LCM (Least Common Multiple) of two numbers
    public static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    // Function to find the LCM of three numbers
    public static int lcmOfThree(int a, int b, int c) {
        int lcmAB = lcm(a, b); // LCM of first two numbers
        return lcm(lcmAB, c);  // LCM of the result and the third number
    }
    
    public void run() {
        try {

            while (true){
                // Setup communication streams
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

                // Read two integers from client
                int a = Integer.parseInt(inFromClient.readLine());
                int b = Integer.parseInt(inFromClient.readLine());
                int c = Integer.parseInt(inFromClient.readLine());
                System.out.println("Received from client: " + a + " and " + b + " and " + c);

                // Find LCM of 3 interger
                int lcm = lcmOfThree(a, b, c);
                
                // Send the uppercase string back to the client
                outToClient.println(lcm);

                // Close the client socket
                clientSocket.close();
                }
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
