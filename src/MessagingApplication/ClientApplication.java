package MessagingApplication;

//Import Statements
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;

import java.util.Objects;


public class ClientApplication extends Application {

    //Start function override for application
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent rootVar = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MessagingApplication.fxml")));
        stage.setTitle("Messaging Application");
        stage.setScene(new Scene(rootVar));
        stage.setResizable(false);
        stage.show();
    }

    // main function
    public static void main(String[] args) {
        launch(args);
    }
}

