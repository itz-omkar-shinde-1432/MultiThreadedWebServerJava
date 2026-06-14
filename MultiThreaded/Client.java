import java.net.InetAddress;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {

    private static final int PORT = 8010;

    private Runnable createClientTask() {

        return () -> {
            try {
                InetAddress address = InetAddress.getByName("localhost");

                try (Socket socket = new Socket(address, PORT);
                     PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader fromSocket = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {

                    System.out.println("[CLIENT] Connected -> " +
                            socket.getLocalSocketAddress());

                    String msg = "Hello from client " +
                            socket.getLocalSocketAddress();

                    toSocket.println(msg);

                    String response = fromSocket.readLine();

                    System.out.println("[SERVER RESPONSE] " + response);

                }

            } catch (IOException e) {
                System.out.println("[CLIENT ERROR] " + e.getMessage());
            }
        };
    }

    public static void main(String[] args) {

        Client client = new Client();

        System.out.println("Starting 100 clients...\n");

        for (int i = 0; i < 100; i++) {
            new Thread(client.createClientTask()).start();
        }
    }
}