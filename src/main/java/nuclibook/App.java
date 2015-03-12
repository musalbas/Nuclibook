package nuclibook;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Timer;

public class App extends Application {

    ToolBar toolBar1 = new ToolBar();
    HBox hBox = new HBox(toolBar1);

    @Override
    public void start(Stage stage) {

        final ProgressBar progressBar = new ProgressBar(0);
        progressBar.prefWidthProperty().bind(toolBar1.widthProperty().subtract(30));
        WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        toolBar1.getItems().add(progressBar);
        StackPane mainPane = new StackPane();
        BorderPane pane = new BorderPane();
        hBox.setHgrow(toolBar1, Priority.ALWAYS);
        hBox.setPrefHeight(0);
        hBox.setMinHeight(0);
        pane.setBottom(hBox);
        pane.setPickOnBounds(false);
        mainPane.getChildren().add(webView);
        mainPane.getChildren().add(pane);
        Scene scene = new Scene(mainPane, 1132, 700);
        stage.setScene(scene);
        stage.setMinHeight(340);
        stage.setMinWidth(600);
        stage.setMaximized(true);
        stage.show();


        progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
        webView.getEngine().load("http://localhost:4567");
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            Timer timer =  new Timer();
            Worker.State currentState;
            boolean panelShown = false;
            @Override
            public void changed(ObservableValue<? extends Worker.State> value,
                                Worker.State oldState, Worker.State newState) {

                currentState = newState;
                //only show progressbar if a second has passed and page has not loaded and if the progress value is less than 50%
                timer.schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (currentState == Worker.State.RUNNING && progressBar.getProgress() < 0.5) {
                                    panelShown = true;
                                    showPanel.play();
                                }
                            }
                        }, 1000);

                //hide progress bar if it has been shown after 2 seconds
                if(currentState == Worker.State.SUCCEEDED && panelShown){
                    timer.schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    panelShown = false;
                                    hidePanel.play();
                                }
                            }, 2000);

                }

                //set pag
                if(newState == Worker.State.SUCCEEDED){
                    stage.setTitle(getTitle(webEngine));
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }


    final Animation hidePanel = new Transition() {
        {
            setCycleDuration(Duration.millis(250));
            setCycleCount(0);

        }

        @Override
        protected void interpolate(double frac) {
            final double size = 30 * (1.0 - frac);
            hBox.setPrefHeight(size);
        }
    };

    Animation showPanel = new Transition() {
        {
            setCycleDuration(Duration.millis(250));
            setDelay(Duration.millis(1000));
        }

        @Override
        protected void interpolate(double frac) {
            final double size = 30 * frac;
            hBox.setPrefHeight(size);
        }
    };

    private String getTitle(WebEngine webEngine) {
        Document doc = webEngine.getDocument();
        NodeList heads = doc.getElementsByTagName("head");
        String titleText = webEngine.getLocation();
        if (heads.getLength() > 0) {
            Element head = (Element)heads.item(0);
            NodeList titles = head.getElementsByTagName("title");
            if (titles.getLength() > 0) {
                org.w3c.dom.Node title = titles.item(0);
                titleText = title.getTextContent();
            }
        }
        return titleText ;
    }
}
