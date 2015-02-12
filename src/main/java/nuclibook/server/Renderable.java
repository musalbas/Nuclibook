package nuclibook.server;

import java.util.HashMap;

public interface Renderable {

	/**
	 * This interface must be implemented by any object to be rendered by HtmlRenderer.
	 */

	/**
	 * @return A hashmap of all possible fields that could be called in the front-end
	 *         HTML template. If the front-end will call <!--[field: status]--> there must
	 *         be an entry in this map, ["status", "..."]. An missing or null values will
	 *         be rendered as "".
	 */
	public HashMap<String, String> getHashMap();

}
