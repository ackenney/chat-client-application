package MessagingApplication;


//import Statements
import javafx.scene.control.TextArea;
import javafx.application.Platform;
import java.net.*;
import java.io.*;


public class ClientChildThread implements Runnable {


    private ObjectInputStream inputData;
    private ObjectOutputStream outputData;
    private boolean messTest = false;
    private int messIndex;
    private final TextArea textArea;


    @Override
    public void run() {
        String tempStr;

        while (true)
        {
            if (messTest)
            {
                try
                {
                    tempStr = (String) inputData.readObject();
                    messTest = false;
                }

                catch (IOException | ClassNotFoundException exception)
                {
                    exception.toString();
                }
            }

            int messList = messageList();
            if (messList > messIndex)
            {
                tempStr = messageFromClient(messList); //Message to other client
                textArea.appendText("User: " + tempStr + "\n");
                messIndex++; // move to next spot on index
            }
            else
            {
                try
                {
                    Thread.sleep(222);
                }
                catch (InterruptedException exception)
                {
                    exception.toString();
                }
            }

        }

    }

    public int messageList() {
        try
        {
            outputData.writeObject(1);
            outputData.flush();
        }
        catch (IOException exception)
        {
            exception.toString();
        }

        int index = 0;

        try
        {
            index = Integer.parseInt((String) inputData.readObject());
        }
        catch (IOException exception)
        {
            Platform.runLater(() -> textArea.appendText("IOException " + exception.toString() + "\n"));
        }
        catch (ClassNotFoundException exception)
        {
            exception.toString();
        }

        return index; // Final return index
    }

    public String messageFromClient(int messLoc) {
        String textStr = ""; //initialize textStr

        try
        {
            outputData.writeObject(3);
            outputData.flush();
            outputData.writeObject(messLoc);
            outputData.flush();
            textStr = inputData.readObject().toString();
        }
        catch (IOException exception)
        {
            exception.toString();
        }

        catch (ClassNotFoundException exception)
        {
            exception.toString();
        }

        return textStr;
    }

    public void messageToClient(String text) {
        try
        {

            messTest = true;
            messIndex++;

            outputData.writeObject(2); // Message to other person
            outputData.flush();

            outputData.writeObject(text);// Message to current client
            outputData.flush();
            textArea.appendText("Me: " + text + "\n");
        }
        catch (IOException exception)
        {
            exception.toString();
        }

    }

    public ClientChildThread(TextArea textArea, Socket socket)
    {
        messIndex = 0;
        this.textArea = textArea;

        try
        {
            outputData = new ObjectOutputStream(socket.getOutputStream());
            inputData = new ObjectInputStream(socket.getInputStream());

        } catch (IOException exception)
        {
            Platform.runLater(() -> textArea.appendText("IOException " + exception.toString() + "\n"));
        }
    }


}//END of ClientChildThread
