import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // Handles one client
    public void handleClient(Socket acceptedConnection) {

        try {

            PrintWriter toClient =
                    new PrintWriter(
                            acceptedConnection.getOutputStream(),
                            true);

            BufferedReader fromClient =
                    new BufferedReader(
                            new InputStreamReader(
                                    acceptedConnection.getInputStream()));

            String clientMessage =
                    fromClient.readLine();

            System.out.println(
                    "Client "
                            + acceptedConnection.getRemoteSocketAddress()
                            + " says: "
                            + clientMessage);

            toClient.println("Hello From the Server");

            toClient.close();
            fromClient.close();
            acceptedConnection.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() throws IOException {

        int port = 8010;

        // Create server
        ServerSocket socket =
                new ServerSocket(port);

        while (true) {

            // Wait for client
            Socket acceptedConnection =
                    socket.accept();

            System.out.println(
                    "Client connected: "
                            + acceptedConnection.getRemoteSocketAddress());

            // Create thread
            Thread thread =
                    new Thread(() ->
                            handleClient(acceptedConnection));

            // Start thread
            thread.start();
        }
    }

    public static void main(String args[]) {

        Server server = new Server();

        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}