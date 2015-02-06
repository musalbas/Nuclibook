package nuclibook.server;

import nuclibook.page_routes.DummyPageRoute;
import spark.Spark;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LocalServer {

	public static void main(String... args) {
		// set up the dummy route
		Spark.get("/", new DummyPageRoute());

		// open the browser
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI("http://localhost:4567"));
			} catch (IOException | URISyntaxException e) {
				System.out.println("Failed to open web browser.");
				e.printStackTrace();
			}
		}
	}

}
