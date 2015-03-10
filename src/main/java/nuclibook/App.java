package nuclibook;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        WebView root = new WebView();

        WebEngine webEngine = root.getEngine();

        webEngine.load("http://localhost:4567");
        stage.setScene(new Scene(root, 1132, 700));
		stage.setMinHeight(340);
		stage.setMinWidth(600);
        stage.setMaximized(true);
        stage.show();

        webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<Worker.State>() {
                public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                    if (newState == Worker.State.SUCCEEDED) {
                        stage.setTitle(getTitle(webEngine));
                    }
                }
            }
        );

    }

    public static void main(String[] args) {
        launch(args);
    }

    // From https://stackoverflow.com/questions/27820793/find-the-title-of-current-webpage-open-in-webengine-javafx
    private String getTitle(WebEngine webEngine) {
        Document doc = webEngine.getDocument();
        NodeList heads = doc.getElementsByTagName("head");
        String titleText = webEngine.getLocation();
        if (heads.getLength() > 0) {
            Element head = (Element)heads.item(0);
            NodeList titles = head.getElementsByTagName("title");
            if (titles.getLength() > 0) {
                Node title = titles.item(0);
                titleText = title.getTextContent();
            }
        }
        return titleText ;
    }

}
