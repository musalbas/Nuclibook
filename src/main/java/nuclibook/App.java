package nuclibook;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.*;

import java.util.Timer;

public class App extends Application {

	// toolbar and hbox to be used by animation objects
	final ToolBar toolBar = new ToolBar();
	final HBox hBox = new HBox(toolBar);

	// animation to hide the loading bar panel
	final Animation hidePanel = new Transition() {
		{
			setCycleDuration(Duration.millis(250));
			setCycleCount(0);
		}

		@Override
		protected void interpolate(double f) {
			final double size = 30 * (1.0 - f);
			hBox.setPrefHeight(size);
		}
	};

	// animation to show the loading bar panel
	final Animation showPanel = new Transition() {
		{
			setCycleDuration(Duration.millis(250));
			setDelay(Duration.millis(1000));
		}

		@Override
		protected void interpolate(double f) {
			final double size = 30 * f;
			hBox.setPrefHeight(size);
		}
	};

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		// initialise
		final WebView webView = new WebView();
		final WebEngine webEngine = webView.getEngine();

		// set up progress bar
		final ProgressBar progressBar = new ProgressBar(0);
		progressBar.prefWidthProperty().bind(toolBar.widthProperty().subtract(30));
		toolBar.getItems().add(progressBar);
		progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());

		// set up webView
		webView.getEngine().load("http://localhost:4567");
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
			Timer timer = new Timer();
			Worker.State currentState;
			boolean panelShown = false;

			@Override
			public void changed(ObservableValue<? extends Worker.State> value, Worker.State oldState, Worker.State newState) {

				currentState = newState;

				// only show progress bar if some time has passed and page has not loaded and if the progress value is less than 50%
				timer.schedule(
						new java.util.TimerTask() {
							@Override
							public void run() {
								if (currentState == Worker.State.RUNNING && progressBar.getProgress() < 0.5) {
									panelShown = true;
									showPanel.play();
								}
							}
						}, 500);

				// hide progress bar if it has been shown after 2 seconds
				if (currentState == Worker.State.SUCCEEDED && panelShown) {
					timer.schedule(
							new java.util.TimerTask() {
								@Override
								public void run() {
									panelShown = false;
									hidePanel.play();
								}
							}, 2000);
				}

				// set page title
				if (newState == Worker.State.SUCCEEDED) {
					stage.setTitle(getTitle(webEngine));
				}
			}
		});

		// set up panes for scene
		final StackPane mainPane = new StackPane();
		final BorderPane pane = new BorderPane();
		HBox.setHgrow(toolBar, Priority.ALWAYS);
		hBox.setPrefHeight(0);
		hBox.setMinHeight(0);
		pane.setBottom(hBox);
		pane.setPickOnBounds(false);
		mainPane.getChildren().add(webView);
		mainPane.getChildren().add(pane);

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
