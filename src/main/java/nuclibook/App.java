package nuclibook;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App extends Application {

	/**
	 * Runs when the app is launched to start the JavaFX application.
	 *
	 * @param args Any command line arguments; ignored in this application.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Sets up the stage, including loading icon, web view and close confirmation.
	 *
	 * @param stage the primary stage for this application, onto which the application scene can be set.
	 */
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
		webEngine.load("http://localhost:4567");
		webEngine.documentProperty().addListener(new ChangeListener<Document>() {
			@Override
			public void changed(ObservableValue<? extends Document> observable, Document oldValue, Document newValue) {
				try {
					// get HTML of new document
					DOMSource domSource = new DOMSource(newValue);
					StringWriter writer = new StringWriter();
					StreamResult result = new StreamResult(writer);
					TransformerFactory tf = TransformerFactory.newInstance();
					Transformer transformer = tf.newTransformer();
					transformer.transform(domSource, result);
					String htmlString = writer.toString();

					// does it contain an instruction to open in the browser?
					Pattern openPattern = Pattern.compile("(.*)<!\\-\\-OPEN:(.*)\\-\\->(.*)");
					Matcher openMatcher = openPattern.matcher(htmlString);
					if (openMatcher.matches() && openMatcher.groupCount() >= 3 && openMatcher.group(2).contains("http")) {
						getHostServices().showDocument(openMatcher.group(2));
					}
				} catch (TransformerException e) {
					// nothing
				}
			}
		});
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

		// confirm close
		scene.getWindow().setOnCloseRequest(event -> {
			// prevent the close action
			event.consume();

			// build a dialog
			final Stage dialog = new Stage();
			Label label = new Label("Are you sure you want to exit Nuclibook?");
			Button okButton = new Button("OK");
			okButton.setOnAction(event1 -> {
				// close dialog and stage
				dialog.close();
				stage.close();
			});
			Button cancelButton = new Button("Cancel");
			cancelButton.setOnAction(event1 -> {
				// close dialog only
				dialog.close();
			});
			FlowPane buttonPane = new FlowPane(10, 10);
			buttonPane.setAlignment(Pos.CENTER);
			buttonPane.getChildren().addAll(okButton, cancelButton);
			VBox vBox = new VBox(10);
			vBox.setAlignment(Pos.CENTER);
			vBox.getChildren().addAll(label, buttonPane);
			vBox.setPadding(new Insets(10));
			Scene scene1 = new Scene(vBox);
			dialog.setScene(scene1);
			dialog.show();
		});
	}

	/**
	 * Get the title from the <title> HTML tags of the page currently loaded in the web engine.
	 * Defaults to the page URL if no title can be found.
	 * Code adapted from http://goo.gl/GA9fkd
	 *
	 * @param webEngine The web engine to extract the title from.
	 * @return The title from the provided web engine.
	 */
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
