package nuclibook.routes;

import spark.Request;
import spark.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HtmlTestRoute extends DefaultRoute {

	/**
	 * This route provides a testing page for static HTML.
	 */

	public HtmlTestRoute() {
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// get file name
		String fileName = request.params(":file");

		// load file
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("static/" + fileName).getFile());

		// return HTML
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			return sb.toString();
		} catch (Exception e) {
			return "Failed to read file";
		}

	}
}