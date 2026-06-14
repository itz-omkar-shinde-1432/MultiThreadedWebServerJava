import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 8010;

    private void handleClient(Socket clientSocket) {
        System.out.println("[THREAD] Handling client: " +
                clientSocket.getRemoteSocketAddress());

        try (
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()))
        ) {

            String message = fromClient.readLine();

            System.out.println("[MESSAGE] From " +
                    clientSocket.getRemoteSocketAddress() +
                    " -> " + message);

            toClient.println("Hello from server 👋");

            System.out.println("[DONE] Response sent to " +
                    clientSocket.getRemoteSocketAddress());

        } catch (IOException e) {
            System.out.println("[ERROR] Client handling failed: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("[DISCONNECTED] Client closed: " +
                        clientSocket.getRemoteSocketAddress());
            } catch (IOException e) {
                System.out.println("[ERROR] Closing socket failed");
            }
        }
    }

    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("==================================");
            System.out.println("🚀 SERVER STARTED");
            System.out.println("📡 Port: " + PORT);
            System.out.println("⏳ Waiting for clients...");
            System.out.println("==================================");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("\n[CONNECTED] Client: " +
                        clientSocket.getRemoteSocketAddress());

                Thread thread = new Thread(() -> handleClient(clientSocket));
                thread.start();
            }

        } catch (IOException e) {
            System.out.println("[SERVER ERROR] " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Server().run();
    }
}