import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void run() throws IOException {

        int port = 8010;
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("Server started on port " + port);

        while (true) {

            try {
                System.out.println("Waiting for client connection...");

                Socket clientSocket = serverSocket.accept();

                System.out.println(
                        "Client connected: "
                                + clientSocket.getRemoteSocketAddress());

                BufferedReader fromClient =
                        new BufferedReader(
                                new InputStreamReader(
                                        clientSocket.getInputStream()));

                PrintWriter toClient =
                        new PrintWriter(
                                clientSocket.getOutputStream(),
                                true);

                // Read message from client
                String clientMessage = fromClient.readLine();

                System.out.println(
                        "Message from client: "
                                + clientMessage);

                // Send response
                toClient.println("Hello From the Server");

                // Close resources
                fromClient.close();
                toClient.close();
                clientSocket.close();

                System.out.println("Client disconnected\n");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
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