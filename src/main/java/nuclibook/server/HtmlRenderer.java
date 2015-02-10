package nuclibook.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlRenderer {

	// location of template HTML file
	private String templateFile;

	// data to be threaded into HTML
	private HashMap<String, String> fields;
	private HashMap<String, Collection<Renderable>> collections;

	// set up patterns and options
	// todo

	// initialise
	public HtmlRenderer(String templateFile) {
		this.templateFile = templateFile;
		fields = new HashMap<>();
		collections = new HashMap<>();
	}

	// set a data field (set null to "remove")
	public void setField(String key, String value) {
		fields.put(key, value);
	}

	// set a data collection (set null to "remove")
	public void setCollection(String key, Collection collection) {
		collections.put(key, collection);
	}

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

	// parse any conditional field statements, defaulting to the global fields
	private String parseConditionalFields(String html) {
		return parseConditionalFields(html, fields);
	}

	// parse any conditional field statements with a specific set of fields
	private String parseConditionalFields(String html, HashMap<String, String> fields) {
		// "positive" conditionals
		Pattern fieldPattern = Pattern.compile("<!\\-\\-\\[if field: ([a-zA-Z0-9\\-]+)\\]\\-\\->(.*?)<!\\-\\-\\[/if\\]\\-\\->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher fieldMatcher = fieldPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (fieldMatcher.find()) {
			fieldMatcher.appendReplacement(output, getConditionalFieldValue(fieldMatcher.group(1), fieldMatcher.group(2), fields));
		}
		fieldMatcher.appendTail(output);
		html = output.toString();

		// negated conditionals
		Pattern negatedFieldPattern = Pattern.compile("<!\\-\\-\\[if no field: ([a-zA-Z0-9\\-]+)\\]\\-\\->(.*?)<!\\-\\-\\[/if\\]\\-\\->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher negatedFieldMatcher = negatedFieldPattern.matcher(html);
		output = new StringBuffer();
		while (negatedFieldMatcher.find()) {
			negatedFieldMatcher.appendReplacement(output, getNegatedConditionalFieldValue(negatedFieldMatcher.group(1), negatedFieldMatcher.group(2), fields));
		}
		negatedFieldMatcher.appendTail(output);

		// done
		return output.toString();
	}

	// parse any field statements, defaulting to the global fields
	private String parseFields(String html) {
		return parseFields(html, fields);
	}

	// parse any conditional field statements with a specific set of fields
	private String parseFields(String html, HashMap<String, String> fields) {
		Pattern fieldPattern = Pattern.compile("<!\\-\\-\\[field: ([a-zA-Z0-9\\-]+)\\]\\-\\->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher fieldMatcher = fieldPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (fieldMatcher.find()) {
			fieldMatcher.appendReplacement(output, getFieldValue(fieldMatcher.group(1), fields));
		}
		fieldMatcher.appendTail(output);
		return output.toString();
	}

	// parse any collection statements
	private String parseCollections(String html) {
		Pattern collectionPattern = Pattern.compile("<!\\-\\-\\[collection: ([a-zA-Z0-9\\-]+)\\]\\-\\->(.*?)<!\\-\\-\\[/collection\\]\\-\\->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher collectionMatcher = collectionPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (collectionMatcher.find()) {
			collectionMatcher.appendReplacement(output, getCollectionHtml(collectionMatcher.group(1), collectionMatcher.group(2)));
		}
		collectionMatcher.appendTail(output);
		return output.toString();
	}

	// return the value, or "", for a given field key
	private String getFieldValue(String key, HashMap<String, String> fields) {
		return (fields.containsKey(key) && fields.get(key) != null) ? fields.get(key) : "";
	}

	// return the original text, or "", for a conditional field statement
	private String getConditionalFieldValue(String key, String original, HashMap<String, String> fields) {
		return (fields.containsKey(key) && fields.get(key) != null) ? original : "";
	}

	// return the original text, or "", for a negated conditional field statement
	private String getNegatedConditionalFieldValue(String key, String original, HashMap<String, String> fields) {
		return (!fields.containsKey(key) || fields.get(key) == null) ? original : "";
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
		while (iterator.hasNext()) {
			entry = (Renderable) iterator.next();
			entryHtml = parseConditionalFields(each, entry.getHashMap());
			entryHtml = parseFields(entryHtml, entry.getHashMap());
			sb.append(entryHtml);
		}

		// finish output
		sb.append(post);

		return sb.toString();
	}

	// get an HTML segment between basic <!--[tag]--> and <!--[/tag]--> wrappers
	private String getSegment(String html, String tag) {
		Pattern segmentPattern = Pattern.compile("<!\\-\\-\\[" + tag + "\\]\\-\\->(.*?)<!\\-\\-\\[/" + tag + "\\]\\-\\->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher segmentMatcher = segmentPattern.matcher(html);
		if (segmentMatcher.find()) {
			return segmentMatcher.group(1);
		} else {
			return "";
		}
	}

	// run the parsing methods in the right order
	public String render() {
		String parsedHtml = readSimpleFile();
		parsedHtml = parseCollections(parsedHtml);
		parsedHtml = parseConditionalFields(parsedHtml);
		parsedHtml = parseFields(parsedHtml);
		return parsedHtml;
	}

}
