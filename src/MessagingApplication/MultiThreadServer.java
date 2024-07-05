package MessagingApplication;

import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadServer implements Runnable {

    static ArrayList<ObjectOutputStream> users = new ArrayList<>();
    static ArrayList<String> messagesList = new ArrayList<>();
    ObjectInputStream inputData;
    ObjectOutputStream outputData;
    Socket socket;




    @Override
    public void run()
    {
        System.out.println("Running the server");
        while (true)
        {
            try
            {
                int option = 0;
                option = (int) inputData.readObject();
                String message;

                // case 1 message list, case 2 getting the message and case 3 getting the sent message
                switch (option)
                {
                    case 1:
                        outputData.writeObject(Integer.toString(messagesList.size()));
                        outputData.flush();
                        break;
                    case 2:

                        message = inputData.readObject().toString();
                        messagesList.add(message);
                        System.out.println(message);
                        break;
                    case 3:
                        int messLoc = 0;
                        messLoc = (int) inputData.readObject();
                        messageToClient(messagesList.get(messLoc - 1));
                        break;
                }

            }
            catch (IOException | ClassNotFoundException exception)
            {
                exception.toString();
            }

        }

    }

    //This functions gets the messages to the clients
    public void messageToClient(String text)
    {
        try
        {
            for (ObjectOutputStream str : users)
            {
                str.writeObject(text);
                str.flush();
                outputData.flush();
            }

        }
        catch (IOException exception)
        {
            exception.toString();
        }
    }

    public MultiThreadServer(Socket socket)
    {
        try
        {
            this.socket = socket;
            inputData = new ObjectInputStream(socket.getInputStream());
            outputData = new ObjectOutputStream(socket.getOutputStream());
            users.add(outputData);

        }
        catch (IOException exception)
        {
            exception.toString();
        }

    }

}//END of MultiThreadServer
