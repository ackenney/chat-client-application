package MessagingApplication;


//import Statements
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class MessagingApplicationFXML implements Initializable {

    private Client clientObj;
    @FXML
    private TextField textBox;
    @FXML
    private TextArea textArea;

    static class Client
    {
        private ClientChildThread tempClient;
        Socket serverSocket;
        TextArea textData;

        public Client(int portNumber, TextArea textArea1)
        {

            this.textData = textArea1;
            new Thread(() -> {
                try
                {
                    serverSocket = new Socket("localhost", portNumber);
                }
                catch (IOException exception)
                {
                    exception.toString();
                }
                tempClient = new ClientChildThread(this.textData, serverSocket);
                new Thread(tempClient).start();
            }).start();

        }

        public void sendMessage(String message)
        {
            tempClient.messageToClient(message);
        }
    }

    public void messageAdd()
    {
        if (!textBox.getText().trim().isEmpty())
        {
            clientObj.sendMessage(textBox.getText());
            textBox.setText("");
        }

    }

    @FXML
    private void pressedSend()
    {
        messageAdd();
    }

    @FXML
    private void pressedEnter(KeyEvent event)
    {
        if (event.getCode() == KeyCode.ENTER)
        {
            messageAdd();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        int port = 1997;
        clientObj = new Client(port, textArea);
        textArea.setEditable(false);

    }
}//END of project4FXML
