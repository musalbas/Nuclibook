package nuclibook;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		// initialise
		final WebView webView = new WebView();
		final WebEngine webEngine = webView.getEngine();

		// set up loading icon
		final ImageView loadingImage = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("static/images/loading.gif")));
		loadingImage.setTranslateY(-10);
		loadingImage.setTranslateX(10);

		// set up webView
		webView.getEngine().load("http://localhost:4567");
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends Worker.State> value, Worker.State oldState, Worker.State newState) {
				// show progress bar
				if (newState == Worker.State.RUNNING) {
					loadingImage.setVisible(true);
				}

				// hide progress bar
				if (newState == Worker.State.SUCCEEDED) {
					loadingImage.setVisible(false);
				}

				// set page title
				if (newState == Worker.State.SUCCEEDED) {
					stage.setTitle(getTitle(webEngine));
				}
			}
		});

		// set up panes for scene
		final StackPane mainPane = new StackPane();
		mainPane.getChildren().add(webView);
		mainPane.getChildren().add(loadingImage);
		StackPane.setAlignment(loadingImage, Pos.BOTTOM_LEFT);

		// set up stage
		Scene scene = new Scene(mainPane, 1132, 700);
		stage.setScene(scene);
		stage.setMinHeight(340);
		stage.setMinWidth(600);
		stage.setMaximized(true);
		stage.show();
	}

	// adapted from http://goo.gl/GA9fkd
	private String getTitle(WebEngine webEngine) {
		Document doc = webEngine.getDocument();
		NodeList heads = doc.getElementsByTagName("head");
		String titleText = webEngine.getLocation();
		if (heads.getLength() > 0) {
			Element head = (Element) heads.item(0);
			NodeList titles = head.getElementsByTagName("title");
			if (titles.getLength() > 0) {
				org.w3c.dom.Node title = titles.item(0);
				titleText = title.getTextContent();
			}
		}
		return titleText;
	}
}
