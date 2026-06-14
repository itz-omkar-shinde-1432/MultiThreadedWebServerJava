import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // Handles one client
    public void handleClient(Socket clientSocket) {

        try {
            BufferedReader fromClient =
                    new BufferedReader(
                            new InputStreamReader(
                                    clientSocket.getInputStream()));

            PrintWriter toClient =
                    new PrintWriter(
                            clientSocket.getOutputStream(),
                            true);

            String message = fromClient.readLine();

            System.out.println(
                    "Client "
                            + clientSocket.getRemoteSocketAddress()
                            + " says: "
                            + message);

            toClient.println("Hello From the Server");

            fromClient.close();
            toClient.close();
            clientSocket.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() throws IOException {

        int port = 8010;

        // Creates server on port 8010
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println(
                "Server is listening on port " + port);

        while (true) {

            // Waits for a client
            Socket clientSocket = serverSocket.accept();

            System.out.println(
                    "Client connected: "
                            + clientSocket.getRemoteSocketAddress());

            // New thread for each client
            Thread thread = new Thread(() ->
                    handleClient(clientSocket));

            thread.start();
        }
    }

    public static void main(String[] args) {

        Server server = new Server();

        try {
            server.run();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}