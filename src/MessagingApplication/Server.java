package MessagingApplication;

import java.net.*;
import java.io.*;

public class Server {

    ServerSocket serverSocket;

    // Server
    public Server(int port)
    {

        new Thread(() -> {
            try
            {
                serverSocket = new ServerSocket(port);
                System.out.println("Server is active");
                while (true)
                {
                    try
                    {
                        Socket socket = serverSocket.accept();
                        System.out.println(socket + " is connected");

                        new Thread(new MultiThreadServer(socket)).start();
                    }
                    catch (IOException exception)
                    {
                        exception.toString();
                    }
                }
            }
            catch (IOException exception)
            {
                exception.toString();
            }
        }).start();

    }

}
