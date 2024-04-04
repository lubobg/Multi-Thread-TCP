package Assignment_5;
import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private int randomNumber;

    public ClientHandler(Socket socket, int randomNumber) {
        this.clientSocket = socket;
        this.randomNumber = randomNumber;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            Server.addClientOutputStream(out);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                try {
                    int guess = Integer.parseInt(inputLine);
                    if (guess == randomNumber) {
                        out.println("Congratulation");
                        Server.sendToAllClients("You lose");
                        Server.removeClientOutputStream(out);
                        break;
                    } else {
                        out.println("Please predict again");
                    }
                } catch (NumberFormatException e) {
                    out.println("Invalid input. Please enter an integer.");
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
