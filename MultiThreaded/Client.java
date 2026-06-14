public class Client {

    // Creates work for one client thread
    public Runnable getRunnable() {

        return new Runnable() {

            @Override
            public void run() {

                // Server port
                int port = 8010;

                try {

                    // Gets localhost address
                    InetAddress address =
                            InetAddress.getByName("localhost");

                    // Connects to server
                    Socket socket =
                            new Socket(address, port);

                    try (

                        // Sends data to server
                        PrintWriter toSocket =
                                new PrintWriter(
                                        socket.getOutputStream(),
                                        true);

                        // Reads data from server
                        BufferedReader fromSocket =
                                new BufferedReader(
                                        new InputStreamReader(
                                                socket.getInputStream()));

                    ) {

                        // Sends message
                        toSocket.println(
                                "Hello from Client "
                                        + socket.getLocalSocketAddress());

                        // Receives response
                        String line = fromSocket.readLine();

                        // Prints response
                        System.out.println(
                                "Response from Server "
                                        + line);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {

        // Creates client object
        Client client = new Client();

        // Creates 100 clients
        for (int i = 0; i < 100; i++) {

            try {

                // Creates thread
                Thread thread =
                        new Thread(client.getRunnable());

                // Starts thread
                thread.start();

            } catch (Exception ex) {
                return;
            }
        }
    }
}