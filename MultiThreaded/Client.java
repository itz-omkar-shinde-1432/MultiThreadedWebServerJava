import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    // Client task
    public Runnable getRunnable() {

        return new Runnable() {

            @Override
            public void run() {

                // Server port
                int port = 8010;

                try {

                    // Server address
                    InetAddress address =
                            InetAddress.getByName("localhost");

                    // Connect to server
                    Socket socket =
                            new Socket(address, port);

                    try (

                        // Send data
                        PrintWriter toSocket =
                                new PrintWriter(
                                        socket.getOutputStream(),
                                        true);

                        // Receive data
                        BufferedReader fromSocket =
                                new BufferedReader(
                                        new InputStreamReader(
                                                socket.getInputStream()));

                    ) {

                        // Send message
                        toSocket.println(
                                "Hello from Client "
                                        + socket.getLocalSocketAddress());

                        // Read response
                        String line = fromSocket.readLine();

                        // Print response
                        System.out.println(
                                "Response from Server: "
                                        + line);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {

        // Create client
        Client client = new Client();

        // Create 100 threads
        for (int i = 0; i < 100; i++) {

            try {

                // New thread
                Thread thread =
                        new Thread(client.getRunnable());

                // Start thread
                thread.start();

            } catch (Exception ex) {
                return;
            }
        }
    }
}