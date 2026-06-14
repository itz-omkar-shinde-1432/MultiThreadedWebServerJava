import java.net.InetAddress;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {

    // One client task
    public Runnable getRunnable() {

        return () -> {

            try {

                int port = 8010;

                InetAddress address =
                        InetAddress.getByName("localhost");

                Socket socket =
                        new Socket(address, port);

                PrintWriter toSocket =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true);

                BufferedReader fromSocket =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));

                toSocket.println(
                        "Hello From Client "
                                + socket.getLocalSocketAddress());

                String line =
                        fromSocket.readLine();

                System.out.println(
                        "Response: " + line);

                fromSocket.close();
                toSocket.close();
                socket.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }

    public static void main(String args[]) {

        Client client = new Client();

        // Create 100 clients
        for (int i = 0; i < 100; i++) {

            Thread thread =
                    new Thread(
                            client.getRunnable());

            thread.start();
        }
    }
}