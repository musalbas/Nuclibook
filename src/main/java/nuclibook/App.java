package nuclibook;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        WebView root = new WebView();
        root.getEngine().load("http://localhost:4567");

        stage.setScene(new Scene(root, 1132, 700));
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
