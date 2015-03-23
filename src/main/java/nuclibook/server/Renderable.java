package nuclibook.server;

import java.util.HashMap;

/**
 * This interface must be implemented by any object to be rendered by {@link nuclibook.server.HtmlRenderer}
 */
public interface Renderable {

	/**
	 * @return A HashMap of all possible fields that could be called in the front-end HTML template.
	 */
	public HashMap<String, String> getHashMap();

}