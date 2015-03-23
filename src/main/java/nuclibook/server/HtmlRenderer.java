package nuclibook.server;

import nuclibook.constants.P;
import nuclibook.models.Staff;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class allows front-end HTML templates to be rendered with the inclusion of data
 * from the Java backend. This is achieved via text elements following a specific format,
 * as detailed below.
 *
 * An HtmlRenderer object is created with the name of the HTML template file, relative to
 * the resources/static/ folder, e.g.:
 *
 * HtmlRenderer renderer = new HtmlRenderer();
 * renderer.setTemplateFile("login.html");
 *
 *
 * Fields
 * ------
 *
 * Fields are the most basic method of integrating data into an HTML template. They
 * contain simple string values only, and can be reference with a hash (#), followed by
 * their name:
 *
 * You are logged in as #user-first-name
 *
 * The field-name may consist only of a combination of lower-case letters, digits, and
 * dashes (-). The corresponding string value is given on the backend as follows:
 *
 * renderer.setField(String name, String value);
 *
 * A missing or null-valued field will result in an empty string when rendered.
 *
 *
 * Defining Fields
 * ---------------
 *
 * Fields can be defined in HTML using the following format:
 *
 * #[def: name = value]
 *
 * This is useful in conjunction with referenced files (see below), such as in the following
 * example:
 *
 * #[def: page-title = Login Page]
 * ##_header.html
 *
 *
 * Conditional Fields (Check if Set)
 * ---------------------------------
 *
 * These introduce some degree of conditional control based on whether a field has been
 * set, and can be used as such:
 *
 * #[if: status]The status is #status.#[/if]
 * #[!if: status]No status is set.#[/!if]
 *
 *
 * Conditional Fields (Check Value)
 * --------------------------------
 *
 * These provide more advanced conditional control by checking the value of a field, like so:
 *
 * #[if: status = okay]It's all good!#[/if]
 * #[!if: status = okay]Uh-oh#[/!if]
 *
 *
 * Conditional Fields (Check Permission)
 * -------------------------------------
 *
 * These provide an easy way to check if a user has a certain permission, like so:
 *
 * #[ifperm: VIEW_TOP_SECRET_DATA]
 *     Hello Mr Bond.
 * #[/ifperm]
 *
 * #[!ifperm: VIEW_TOP_SECRET_DATA]
 *     YOU SHALL NOT PASS!
 * #[/!ifperm]
 *
 * The inner content will be shown if the currently active user has the stated permission.
 *
 * The permission name must match *exactly* with the enum values in nuclibook.constants.P.
 *
 *
 * Collections
 * -----------
 *
 * Collection tags allow you to iterate over a collection of objects that implement the
 * Renderable interface. This is useful for lists, tables, etc. A collection has a
 * unique name (of the same format as field names) and is defined as follows:
 *
 * #[collection: names]
 *     #[pre]
 *         <ul>
 *     #[/pre]
 *     #[each]
 *         <li>#first-name #last-name</li>
 *     #[/each]
 *     #[post]
 *         </ul>
 *     #[/post]
 *     #[empty]
 *         <p>Empty collection.</p>
 *     #[/empty]
 * #[/collection]
 *
 * A collection tag MUST have 4 internal sections (even if they are empty):
 *     pre:   this is printed before the iterative section
 *     post:  this is printed after the iterative section
 *     each:  this is printed for each iteration and may contain fields from the global
 *            set and/or the iterated Renderable object
 *     empty: this is printed if the collection is empty, or not set
 *
 * The collection can be specified on the back-end as:
 *
 * renderer.setCollection(String name, Collection<Renderable> data)
 *
 * Two "helper" tags exist within the 'each' section:
 *     #_index        prints the index of the current iteration, starting from zero
 *     #_guid         prints a GUID for that iteration (useful for an identifier when
 *                    the index is unsuitable)
 *
 *
 * Collection Maps
 * ---------------
 *
 * This will produce an ID-indexed array of JavaScript objects with all the fields of the
 * entity, useful for any on-page CRUD actions. Usage:
 *
 * #[collectionmap: varname: collection-name]
 *
 *
 * Files
 * -----
 *
 * Another static HTML file can be included using a double-hash, as follows:
 *
 * ##header.html
 *
 * Included files will also be rendered.
 *
 *
 * Rendering
 * ---------
 *
 * Once all of the data has been set, the rendered HTML can be obtained via:
 *
 * renderer.render();
 */
	public class HtmlRenderer {

	// location of template HTML file
	private String templateFile;

	// data to be threaded into HTML
	private HashMap<String, String> fields;
	private HashMap<String, Collection<Renderable>> collections;

	// the user requesting the page
	private Staff currentUser;

	/**
	 * PATTERNS
	 */

	private static int regexOptions = Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE;
	private static Pattern filePattern = Pattern.compile("##([a-z0-9\\-\\._]+)", regexOptions);
	private static Pattern definitionPattern = Pattern.compile("#\\[def: ([a-z0-9\\-]+) = (.*?)\\]", regexOptions);
	private static Pattern conditionalSetFieldPattern = Pattern.compile("#\\[(if|!if): ([a-z0-9\\-]+)\\](.*?)#\\[/(if|!if)\\]", regexOptions);
	private static Pattern conditionalValueFieldPattern = Pattern.compile("#\\[(if|!if): ([a-z0-9\\-]+) = (.*?)\\](.*?)#\\[/(if|!if)\\]", regexOptions);
	private static Pattern conditionalPermissionFieldPattern = Pattern.compile("#\\[(ifperm|!ifperm): ([a-z0-9\\-_]+)\\](.*?)#\\[/(ifperm|!ifperm)\\]", regexOptions);
	private static Pattern fieldPattern = Pattern.compile("#(HTMLOKAY:)?([a-z0-9\\-]+)", regexOptions);
	private static Pattern collectionPattern = Pattern.compile("#\\[collection: ([a-z0-9\\-]+)\\](.*?)#\\[/collection\\]", regexOptions);
	private static Pattern collectionMapPattern = Pattern.compile("#\\[collectionmap: ([a-z0-9\\\\-]+): ([a-z0-9\\-]+)\\]", regexOptions);

	/**
	 * CONSTRUCTOR
	 */

	/**
	 * Initialise a renderer and set the fields and collections as blank.
	 */
	public HtmlRenderer() {
		fields = new HashMap<>();
		collections = new HashMap<>();
	}

	/**
	 * DATA SETTERS
	 */

	/**
	 * Sets the user currently requesting this page
	 * @param user The user currently requesting this page
	 */
	public void setCurrentUser(Staff user) {
		this.currentUser = user;
	}

	/**
	 * Sets the HTML template file that is to be used with this engine
	 * @param templateFile The HTML template file that is to be used
	 */
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	/**
	 * Set a public data field
	 * Set as null to remove
	 * @param key The name of the field to set
	 * @param value The value of the field to set
	 */
	public void setField(String key, String value) {
		fields.put(key, value);
	}

	/**
	 * Set a public data field
	 * Set as null to remove
	 * @param key The name of the field to set
	 * @param value The value of the field to set (will be converted to a string)
	 */
	public void setField(String key, Integer value) {
		fields.put(key, value == null ? null : value.toString());
	}

	/**
	 * Set a public data collection
	 * Set as null to remove
	 * @param key The name of the collection to set
	 * @param collection The value of the collection to set - must be a collection of items implementing the {@link nuclibook.server.Renderable} interface
	 */
	public <E> void setCollection(String key, Collection<E> collection) {
		ArrayList<Renderable> renderableCollection = new ArrayList<>(collection.size());
		try {
			renderableCollection.addAll(collection.stream().map(o -> (Renderable) o).collect(Collectors.toList()));
			collections.put(key, renderableCollection);
		} catch (ClassCastException e) {
			collections.put(key, null);
			System.out.println("ERROR: Cannot cast to Renderable");
		}
	}

	/**
	 * Set multiple fields at once (merged with current)
	 * @param fields Map of fields to be set
	 */
	public void setBulkFields(HashMap<String, String> fields) {
		HashMap<String, String> tmp = new HashMap<>(fields);
		tmp.keySet().removeAll(this.fields.keySet());
		this.fields.putAll(tmp);
	}

	/**
	 * Set multiple collections at once (merged with current)
	 * @param collections Map of fields to be set
	 */
	public void setBulkCollections(HashMap<String, Collection<Renderable>> collections) {
		HashMap<String, Collection<Renderable>> tmp = new HashMap<>(collections);
		tmp.keySet().removeAll(this.collections.keySet());
		this.collections.putAll(tmp);
	}

	/**
	 * Remove all fields
	 */
	public void clearFields() {
		fields.clear();
	}

	/**
	 * Remove all collections
	 */
	public void clearCollections() {
		collections.clear();
	}

	/**
	 * DEFINITION PARSING
	 */

	/**
	 * Parse any on-page field definitions
	 * @param html Input HTML
	 * @return Output HTML
	 */
	private String parseDefinitions(String html) {
		Matcher definitionMatcher = definitionPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (definitionMatcher.find()) {
			definitionMatcher.appendReplacement(output, "");
			fields.put(definitionMatcher.group(1), definitionMatcher.group(2));
		}
		definitionMatcher.appendTail(output);
		return output.toString();
	}

	/**
	 * FILE PARSING
	 */

	/**
	 * Parse any on-page file inclusions
	 * @param html Input HTML
	 * @return Output HTML
	 */
	private String parseFiles(String html) {
		Matcher fileMatcher = filePattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (fileMatcher.find()) {
			fileMatcher.appendReplacement(output, getFile(fileMatcher.group(1)));
		}
		fileMatcher.appendTail(output);
		return output.toString();
	}

	/**
	 * Get the HTML for an included file
	 * @param path The file name to load
	 * @return HTML of the loaded file
	 */
	private String getFile(String path) {
		HtmlRenderer renderer = new HtmlRenderer();
		renderer.setTemplateFile(path);
		renderer.setBulkFields(fields);
		renderer.setBulkCollections(collections);
		renderer.setCurrentUser(currentUser);
		return renderer.render();
	}

	/**
	 * COLLECTION PARSING
	 */

	/**
	 * Parse any collection fields
	 * @param html Input HTML
	 * @return Output HTML
	 */
	private String parseCollections(String html) {
		Matcher collectionMatcher = collectionPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (collectionMatcher.find()) {
			collectionMatcher.appendReplacement(output, getCollectionHtml(collectionMatcher.group(1), collectionMatcher.group(2)));
		}
		collectionMatcher.appendTail(output);
		return output.toString();
	}

	/**
	 * Generate the HTML for a collection
	 * @param key They key of the collection objects
	 * @param original The full HTML of the collection block
	 * @return Output HMTL
	 */
	private String getCollectionHtml(String key, String original) {
		// get collection iteration "chunks"
		String pre = getSegment(original, "pre");
		String post = getSegment(original, "post");
		String empty = getSegment(original, "empty");
		String each = getSegment(original, "each");

		// no collection?
		if (!collections.containsKey(key) || collections.get(key) == null || collections.get(key).isEmpty()) {
			return empty;
		}

		// get collection
		Collection collection = collections.get(key);

		// start output
		StringBuilder sb = new StringBuilder();
		sb.append(pre);

		// add to output for each element in the collection
		Iterator iterator = collection.iterator();
		Renderable entry;
		String entryHtml;
		int i = 0;
		while (iterator.hasNext()) {
			// get entry and handle fields
			entry = (Renderable) iterator.next();
			entryHtml = parseConditionalSetFields(each, mergeHashMaps(entry.getHashMap(), fields));
			entryHtml = parseConditionalValueFields(entryHtml, mergeHashMaps(entry.getHashMap(), fields));
			entryHtml = parseFields(entryHtml, mergeHashMaps(entry.getHashMap(), fields));

			// insert index and guid
			entryHtml = basicReplace(entryHtml, "_index", "" + i);
			entryHtml = basicReplace(entryHtml, "_guid", UUID.randomUUID().toString());
			sb.append(entryHtml);
			++i;
		}

		// finish output
		sb.append(post);

		return sb.toString();
	}

	/**
	 * COLLECTION MAP PARSING
	 */

	/**
	 * Parse any collection maps
	 * @param html Input HTML
	 * @return Output HTML
	 */
	private String parseCollectionMaps(String html) {
		Matcher collectionMapMatcher = collectionMapPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (collectionMapMatcher.find()) {
			collectionMapMatcher.appendReplacement(output, getCollectionMapHtml(collectionMapMatcher.group(2), collectionMapMatcher.group(1)));
		}
		collectionMapMatcher.appendTail(output);
		return output.toString();
	}

	/**
	 * Generate the collection map HTML for a given collection key and variable name
	 * @param key The key of the collection to be rendered
	 * @param varName The variable name to use in the code output
	 * @return Output HTML
	 */
	private String getCollectionMapHtml(String key, String varName) {
		// get collection
		if (!collections.containsKey(key) || collections.get(key) == null) {
			return "";
		}
		Collection<Renderable> collection = collections.get(key);
		return getCollectionMapHtml(collection, varName);
	}

	/**
	 * Generate the collection map HTML for a given collection key and variable name
	 * @param collection The collection to be rendered
	 * @param varName The variable name to use in the code output
	 * @return Output HTML
	 */
	public static <E> String getCollectionMapHtml(Collection<E> collection, String varName) {
		ArrayList<Renderable> renderableCollection = new ArrayList<>(collection.size());
		try {
			renderableCollection.addAll(collection.stream().map(o -> (Renderable) o).collect(Collectors.toList()));
		} catch (ClassCastException e) {
			System.out.println("ERROR: Cannot cast to Renderable");
			return "";
		}

		// start output basics
		StringBuilder output = new StringBuilder();
		output.append("<script type=\"text/javascript\">");
		output.append("var ").append(varName).append(" = {");

		// put in objects
		HashMap<String, String> fields;
		for (Renderable r : renderableCollection) {
			fields = r.getHashMap();
			output.append("'").append(fields.get("id")).append("': {");

			// put in fields
			for (Map.Entry<String, String> e : fields.entrySet()) {
				if (e.getValue().startsWith("IDLIST:")) {
					output.append("'").append(e.getKey()).append("': ").append("[");
					String[] ids = e.getValue().substring(7).split(",");
					for (String id : ids) {
						output.append(id).append(",");
					}
					output.append("],");
				} else if (e.getValue().startsWith("CUSTOM:")) {
					output.append("'").append(e.getKey()).append("': ").append(e.getValue().substring(7)).append(",");
				} else {
					output.append("'").append(e.getKey()).append("': '").append(e.getValue().replace("\'", "\\\\\'")).append("',");
				}
			}

			output.append("},");
		}

		// finish output
		output.append("}");
		output.append("</script>");
		return output.toString();
	}

	/**
	 * CONDITIONAL SET FIELD PARSING
	 */

	/**
	 * Parse any conditional set field statements
	 * @param html Input HTML
	 * @return Output HTML
	 */
	private String parseConditionalSetFields(String html) {
		return parseConditionalSetFields(html, fields);
	}

	/**
	 * Parse any conditional set field statements for a specific set of fields
	 * @param html Input HTML
	 * @param fields The map of fields to check against
	 * @return Output HTML
	 */
	private String parseConditionalSetFields(String html, HashMap<String, String> fields) {
		Matcher fieldMatcher = conditionalSetFieldPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (fieldMatcher.find()) {
			fieldMatcher.appendReplacement(output, getConditionalSetFieldValue(fieldMatcher.group(1), fieldMatcher.group(2), fieldMatcher.group(3), fields));
		}
		fieldMatcher.appendTail(output);

		// done
		return output.toString();
	}

	/**
	 * Generates the HTML for a conditional field - either the enclosed HTML from the original block, or ""
	 * @param ifField The tag type; either "if" or "!if"
	 * @param key The field to check
	 * @param original The original HTML block
	 * @param fields The map of fields to check against
	 * @return Output HTML
	 */
	private String getConditionalSetFieldValue(String ifField, String key, String original, HashMap<String, String> fields) {
		return ifField.startsWith("!") ?
				((!fields.containsKey(key) || fields.get(key) == null) ? original : "") :
				((fields.containsKey(key) && fields.get(key) != null) ? original : "");
	}

	/**
	 * CONDITIONAL VALUE FIELD CHECKING
	 */

	/**
	 * Parse any conditional value field statements
	 * @param html Input HTML
	 * @return Output HTML
	 */
	private String parseConditionalValueFields(String html) {
		return parseConditionalValueFields(html, fields);
	}

	/**
	 * Parse any conditional value field statements for a specific set of fields
	 * @param html Input HTML
	 * @param fields The map of fields to check against
	 * @return Output HTML
	 */
	private String parseConditionalValueFields(String html, HashMap<String, String> fields) {
		Matcher matcher = conditionalValueFieldPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(output, getConditionalValueFieldValue(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), fields));
		}
		matcher.appendTail(output);

		// done
		return output.toString();
	}

	/**
	 * Generates the HTML for a conditional field - either the enclosed HTML from the original block, or ""
	 * @param ifField The tag type; either "if" or "!if"
	 * @param key The field to check
	 * @param value The value to check for
	 * @param original The original HTML block
	 * @param fields The map of fields to check against
	 * @return Output HTML
	 */
	private String getConditionalValueFieldValue(String ifField, String key, String value, String original, HashMap<String, String> fields) {
		return ifField.startsWith("!") ?
				((!fields.containsKey(key) || fields.get(key) == null || !fields.get(key).equals(value)) ? original : "") :
				((fields.containsKey(key) && fields.get(key) != null && fields.get(key).equals(value)) ? original : "");
	}

	/**
	 * CONDITIONAL PERMISSION FIELD CHECKING
	 */

	/**
	 * Parse any conditional permission field
	 * @param html Input HTML
	 * @return Output HTML
	 */
	private String parseConditionalPermissionFields(String html) {
		Matcher matcher = conditionalPermissionFieldPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(output, getConditionalPermissionFieldValue(matcher.group(1), matcher.group(2), matcher.group(3)));
		}
		matcher.appendTail(output);

		// done
		return output.toString();
	}

	/**
	 * Generate the HTML for a conditional permission field - either the enclosed HTML from the original block, or ""
	 * @param ifField The tag type; either "if" or "!if"
	 * @param key The permission name to check for
	 * @param original The original HTML block
	 * @return Output HTML
	 */
	private String getConditionalPermissionFieldValue(String ifField, String key, String original) {
		try {
			P p = P.valueOf(key);
			Staff currentStaff = currentUser;
			return ifField.startsWith("!") ?
					((currentStaff == null || !currentStaff.hasPermission(p)) ? original : "") :
					((currentStaff != null && currentStaff.hasPermission(p)) ? original : "");
		} catch (NullPointerException | IllegalArgumentException e) {
			return "";
		}
	}

	/**
	 * SIMPLE FIELD PARSING
	 */

	/**
	 * Parse any field statements
	 * @param html Input HTML
	 * @return Output HTML
	 */
	private String parseFields(String html) {
		return parseFields(html, fields);
	}

	/**
	 * Parse any field statements for a specific set of fields
	 * @param html Input HTML
	 * @param fields The map of fields to check against
	 * @return Output HTML
	 */
	private String parseFields(String html, HashMap<String, String> fields) {
		Matcher fieldMatcher = fieldPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (fieldMatcher.find()) {
			fieldMatcher.appendReplacement(output, getFieldValue(fieldMatcher.group(2), fieldMatcher.group(1), fields));
		}
		fieldMatcher.appendTail(output);
		return output.toString();
	}

	/**
	 * Generate the HTML for a simple field - either the field value or ""
	 * @param key The field name to insert
	 * @param htmlOkay Whether HTML should be allowed; either "HTMLOKAY:" or ""
	 * @param fields The map of fields to check against
	 * @return Output HTML
	 */
	private String getFieldValue(String key, String htmlOkay, HashMap<String, String> fields) {
		return (fields.containsKey(key) && fields.get(key) != null) ? ((htmlOkay != null && htmlOkay.equals("HTMLOKAY:")) ? fields.get(key) : StringEscapeUtils.escapeHtml(fields.get(key))) : "";
	}

	/**
	 * HELPER METHODS
	 */

	/**
	 * Read the template file into a simple string
	 * @return The template file contents
	 */
	private String readSimpleFile() {
		// load file
		URL url = null;
		try {
			url = getClass().getClassLoader().getResource("static/" + templateFile);
			if (url == null) throw new NullPointerException();
			File file = new File(url.getPath());

			// read HTML
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			return sb.toString();
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			return "<html>" +
					"<head>" +
					"</head>" +
					"<body>" +
					"<p>Failed to load template: <em>" + templateFile + "</em></p>" +
					"<p>Searching in: <em>" + (url == null ? "null" : url.toString()) + "</em></p>" +
					"</body>" +
					"</html>";
		}
	}

	/**
	 * Get an HTML segment between basic #[tag] and #[/tag] wrappers
	 * @param html The HTML to extract from
	 * @param tag The tag to extract
	 * @return The contents between the tags, or ""
 	 */
	private String getSegment(String html, String tag) {
		Pattern segmentPattern = Pattern.compile("#\\[" + tag + "\\](.*?)#\\[/" + tag + "\\]", regexOptions);
		Matcher segmentMatcher = segmentPattern.matcher(html);
		if (segmentMatcher.find()) {
			return segmentMatcher.group(1);
		} else {
			return "";
		}
	}

	/**
	 * Replace a simple #tag with a given value
	 * @param html The HTML to replace in
	 * @param tag The tag to replace
	 * @param value The value to be inserted
	 * @return Output HTML
	 */
	private String basicReplace(String html, String tag, String value) {
		Pattern basicReplacePattern = Pattern.compile("#" + tag, regexOptions);
		Matcher basicReplaceMatcher = basicReplacePattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (basicReplaceMatcher.find()) {
			basicReplaceMatcher.appendReplacement(output, value);
		}
		basicReplaceMatcher.appendTail(output);
		return output.toString();
	}

	/**
	 * Merge two HashMaps together
	 * @param a HashMap A
	 * @param b HashMap B
	 * @return A HashMap built from A, with all fields of B added (fields in B will overwrite A)
	 */
	private HashMap<String, String> mergeHashMaps(HashMap<String, String> a,  HashMap<String, String> b) {
		HashMap<String, String> temp = (HashMap<String, String>) a.clone();
		temp.putAll(b);
		return temp;
	}

	/**
	 * RENDERER
	 */

	/**
	 * This runs all of the rendering sub-methods in the right order
	 * @return Fully rendered HTML
	 */
	public String render() {
		String parsedHtml = readSimpleFile();
		parsedHtml = parseDefinitions(parsedHtml);
		parsedHtml = parseFiles(parsedHtml);
		parsedHtml = parseCollections(parsedHtml);
		parsedHtml = parseCollectionMaps(parsedHtml);
		parsedHtml = parseConditionalSetFields(parsedHtml);
		parsedHtml = parseConditionalValueFields(parsedHtml);
		parsedHtml = parseConditionalPermissionFields(parsedHtml);
		parsedHtml = parseFields(parsedHtml);
		return parsedHtml;
	}

}
