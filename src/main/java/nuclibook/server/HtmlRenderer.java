package nuclibook.server;

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

public class HtmlRenderer {

	/**
	 * This class allows front-end HTML templates to be rendered with the inclusion of data
	 * from the Java backend. This is achieved via text elements following a specific format,
	 * as detailed below.
	 *
	 * An HtmlRenderer object is created with the name of the HTML template file, relative to
	 * the resources/static/ folder, e.g.:
	 *
	 * HtmlRenderer renderer = new HtmlRenderer("login.html");
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
	 * #[if: status=okay]It's all good!#[/if]
	 * #[!if: status=okay]Uh-oh#[/!if]
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
	 * A collection tag MUST have 4 internal sections:
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
	 * #[collectionmap: collection-name]
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
	 * Rendering
	 * ---------
	 *
	 * Once all of the data has been set, the rendered HTML can be obtained via:
	 *
	 * renderer.render();
	 */

	// location of template HTML file
	private String templateFile;

	// data to be threaded into HTML
	private HashMap<String, String> fields;
	private HashMap<String, Collection<Renderable>> collections;

	/**
	 * PATTERNS
	 */
	private static int regexOptions = Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE;
	private static Pattern filePattern = Pattern.compile("##([a-z0-9\\-\\._]+)", regexOptions);
	private static Pattern definitionPattern = Pattern.compile("#\\[def: ([a-z0-9\\-]+) = (.*?)\\]", regexOptions);
	private static Pattern conditionalSetFieldPattern = Pattern.compile("#\\[(if|!if): ([a-z0-9\\-]+)\\](.*?)#\\[/(if|!if)\\]", regexOptions);
	private static Pattern conditionalValueFieldPattern = Pattern.compile("#\\[(if|!if): ([a-z0-9\\-]+)=(.*?)\\](.*?)#\\[/(if|!if)\\]", regexOptions);
	private static Pattern fieldPattern = Pattern.compile("#([a-z0-9\\-]+)", regexOptions);
	private static Pattern collectionPattern = Pattern.compile("#\\[collection: ([a-z0-9\\-]+)\\](.*?)#\\[/collection\\]", regexOptions);
	private static Pattern collectionMapPattern = Pattern.compile("#\\[collectionmap: ([a-z0-9\\-]+)\\]", regexOptions);

	/**
	 * CONSTRUCTOR
	 */

	public HtmlRenderer(String templateFile) {
		this.templateFile = templateFile;
		fields = new HashMap<>();
		collections = new HashMap<>();
	}

	/**
	 * DATA SETTERS
	 */

	// set a data field (set null to "remove")
	public void setField(String key, String value) {
		fields.put(key, value);
	}

	// set a data field (set null to "remove")
	public void setField(String key, Integer value) {
		fields.put(key, value == null ? null : value.toString());
	}

	// set a data collection (set null to "remove")
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

	// set all fields in one go (replaces any existing fields)
	public void setBulkFields(HashMap<String, String> fields) {
		this.fields = fields;
	}

	// set all collections in one go (replaces any existing collections)
	public void setBulkCollections(HashMap<String, Collection<Renderable>> collections) {
		this.collections = collections;
	}

	/**
	 * DEFINITION PARSING
	 */

	// parse any defined fields
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

	// include referenced files
	private String parseFiles(String html) {
		Matcher fileMatcher = filePattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (fileMatcher.find()) {
			fileMatcher.appendReplacement(output, getFile(fileMatcher.group(1)));
		}
		fileMatcher.appendTail(output);
		return output.toString();
	}

	// get a referenced file
	private String getFile(String path) {
		HtmlRenderer renderer = new HtmlRenderer(path);
		renderer.setBulkFields(fields);
		renderer.setBulkCollections(collections);
		return renderer.render();
	}

	/**
	 * COLLECTION PARSING
	 */

	// parse any collection statements
	private String parseCollections(String html) {
		Matcher collectionMatcher = collectionPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (collectionMatcher.find()) {
			collectionMatcher.appendReplacement(output, getCollectionHtml(collectionMatcher.group(1), collectionMatcher.group(2)));
		}
		collectionMatcher.appendTail(output);
		return output.toString();
	}

	// return the HTML for an iterated collection
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
			entryHtml = parseConditionalSetFields(each, entry.getHashMap());
			entryHtml = parseFields(entryHtml, entry.getHashMap());

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

	// parse any collection statements
	private String parseCollectionMaps(String html) {
		Matcher collectionMapMatcher = collectionMapPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (collectionMapMatcher.find()) {
			collectionMapMatcher.appendReplacement(output, getCollectionMapHtml(collectionMapMatcher.group(1)));
		}
		collectionMapMatcher.appendTail(output);
		return output.toString();
	}

	private String getCollectionMapHtml(String key) {
		// get collection
		if (!collections.containsKey(key) || collections.get(key) == null) {
			return "";
		}
		Collection<Renderable> collection = collections.get(key);

		// start output basics
		StringBuilder output = new StringBuilder();
		output.append("<script type=\"text/javascript\">");
		output.append("var ").append(key).append(" = {");

		// put in objects
		HashMap<String, String> fields;
		for (Renderable r : collection) {
			fields = r.getHashMap();
			output.append("'").append(fields.get("id")).append("': {");

			// put in fields
			for (Map.Entry<String, String> e : fields.entrySet()) {
				output.append("'").append(e.getKey()).append("': '").append(e.getValue()).append("',");
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

	// parse any conditional set field statements, defaulting to the global fields
	private String parseConditionalSetFields(String html) {
		return parseConditionalSetFields(html, fields);
	}

	// parse any conditional set field statements with a specific set of fields
	private String parseConditionalSetFields(String html, HashMap<String, String> fields) {
		// "positive" conditionals
		Matcher fieldMatcher = conditionalSetFieldPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (fieldMatcher.find()) {
			fieldMatcher.appendReplacement(output, getConditionalSetFieldValue(fieldMatcher.group(1), fieldMatcher.group(2), fieldMatcher.group(3), fields));
		}
		fieldMatcher.appendTail(output);

		// done
		return output.toString();
	}

	// return the original text, or "", for a conditional set field statement
	private String getConditionalSetFieldValue(String ifField, String key, String original, HashMap<String, String> fields) {
		return ifField.startsWith("!") ?
				((!fields.containsKey(key) || fields.get(key) == null) ? original : "") :
				((fields.containsKey(key) && fields.get(key) != null) ? original : "");
	}

	/**
	 * CONDITIONAL VALUE FIELD CHECKING
	 */

	// parse any conditional value field statements, defaulting to the global fields
	private String parseConditionalValueFields(String html) {
		return parseConditionalValueFields(html, fields);
	}

	// parse any conditional value field statements with a specific set of fields
	private String parseConditionalValueFields(String html, HashMap<String, String> fields) {
		// "positive" conditionals
		Matcher matcher = conditionalValueFieldPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(output, getConditionalValueFieldValue(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), fields));
		}
		matcher.appendTail(output);

		// done
		return output.toString();
	}

	// return the original text, or "", for a conditional value field statement
	private String getConditionalValueFieldValue(String ifField, String key, String value, String original, HashMap<String, String> fields) {
		return ifField.startsWith("!") ?
				((!fields.containsKey(key) || fields.get(key) == null || !fields.get(key).equals(value)) ? original : "") :
				((fields.containsKey(key) && fields.get(key) != null && fields.get(key).equals(value)) ? original : "");
	}

	/**
	 * SIMPLE FIELD PARSING
	 */

	// parse any field statements, defaulting to the global fields
	private String parseFields(String html) {
		return parseFields(html, fields);
	}

	// parse any conditional field statements with a specific set of fields
	private String parseFields(String html, HashMap<String, String> fields) {
		Matcher fieldMatcher = fieldPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (fieldMatcher.find()) {
			fieldMatcher.appendReplacement(output, getFieldValue(fieldMatcher.group(1), fields));
		}
		fieldMatcher.appendTail(output);
		return output.toString();
	}

	// return the value, or "", for a given field key
	private String getFieldValue(String key, HashMap<String, String> fields) {
		return (fields.containsKey(key) && fields.get(key) != null) ? StringEscapeUtils.escapeHtml(fields.get(key)) : "";
	}

	/**
	 * HELPER METHODS
	 */

	// read the plain template in as a string
	private String readSimpleFile() {
		// load file
		try {
			URL url = getClass().getClassLoader().getResource("static/" + templateFile);
			if (url == null) throw new NullPointerException();
			File file = new File(url.getFile());

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
			return "<html>" +
					"<head>" +
					"</head>" +
					"<body>" +
					"Failed to load template: <em>" + templateFile + "</em>" +
					"</body>" +
					"</html>";
		}
	}

	// get an HTML segment between basic #[tag] and #[/tag] wrappers
	private String getSegment(String html, String tag) {
		Pattern segmentPattern = Pattern.compile("#\\[" + tag + "\\](.*?)#\\[/" + tag + "\\]", regexOptions);
		Matcher segmentMatcher = segmentPattern.matcher(html);
		if (segmentMatcher.find()) {
			return segmentMatcher.group(1);
		} else {
			return "";
		}
	}

	// replace a given keyword with a given value
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
	 * RENDERER
	 */

	// run the parsing methods in the right order
	public String render() {
		String parsedHtml = readSimpleFile();
		parsedHtml = parseDefinitions(parsedHtml);
		parsedHtml = parseFiles(parsedHtml);
		parsedHtml = parseCollections(parsedHtml);
		parsedHtml = parseCollectionMaps(parsedHtml);
		parsedHtml = parseConditionalSetFields(parsedHtml);
		parsedHtml = parseConditionalValueFields(parsedHtml);
		parsedHtml = parseFields(parsedHtml);
		return parsedHtml;
	}

}
