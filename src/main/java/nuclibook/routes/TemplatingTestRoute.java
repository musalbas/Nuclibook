package nuclibook.routes;

import nuclibook.server.HtmlRenderer;
import nuclibook.server.Renderable;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;

public class TemplatingTestRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// start renderer
		HtmlRenderer renderer = new HtmlRenderer("test.html");

		// input field values
		renderer.setField("test-field", "It works!");
		renderer.setField("status", "win");

		// make collection
		ArrayList<DummyItem> testCollection = new ArrayList<>();
		testCollection.add(new DummyItem("item a"));
		testCollection.add(new DummyItem("item b"));
		testCollection.add(new DummyItem("item c"));
		testCollection.add(new DummyItem("item d"));
		testCollection.add(new DummyItem("item e"));

		// add collection
		renderer.setCollection("test-collection", testCollection);

		return renderer.render();
	}

	public class DummyItem implements Renderable {

		String name;

		public DummyItem(String name) {
			this.name = name;
		}

		@Override
		public HashMap<String, String> getHashMap() {
			HashMap<String, String> output = new HashMap<>();
			output.put("name", name);
			return output;
		}
	}
}
