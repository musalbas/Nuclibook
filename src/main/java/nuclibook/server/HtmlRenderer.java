package nuclibook.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlRenderer {

	private String templateFile;

	private HashMap<String, String> fields;
	private HashMap<String, Collection> collections;

	private String parsedHtml = "";

	public HtmlRenderer(String templateFile) {
		this.templateFile = templateFile;
		fields = new HashMap<>();
		collections = new HashMap<>();
	}

	public void setField(String key, String value) {
		fields.put(key, value);
	}

	public void setCollection(String key, Collection collection) {
		collections.put(key, collection);
	}

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
			return null;
		}
	}

	private void parseConditionalFields() {
		Pattern fieldPattern = Pattern.compile("<!\\-\\-\\[if field: ([a-zA-Z0-9\\-]+)\\]\\-\\->(.*?)<!\\-\\-\\[/if\\]\\-\\->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher fieldMatcher = fieldPattern.matcher(parsedHtml);
		StringBuffer output = new StringBuffer();
		while (fieldMatcher.find()) {
			fieldMatcher.appendReplacement(output, getConditionalFieldValue(fieldMatcher.group(1), fieldMatcher.group(2)));
		}
		fieldMatcher.appendTail(output);
		parsedHtml = output.toString();
	}

	private void parseFields() {
		Pattern fieldPattern = Pattern.compile("<!\\-\\-\\[field: ([a-zA-Z0-9\\-]+)\\]\\-\\->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher fieldMatcher = fieldPattern.matcher(parsedHtml);
		StringBuffer output = new StringBuffer();
		while (fieldMatcher.find()) {
			fieldMatcher.appendReplacement(output, getFieldValue(fieldMatcher.group(1)));
		}
		fieldMatcher.appendTail(output);
		parsedHtml = output.toString();
	}

	private String getFieldValue(String key) {
		return (fields.containsKey(key) && fields.get(key) != null) ? fields.get(key) : "";
	}

	private String getConditionalFieldValue(String key, String original) {
		return (fields.containsKey(key) && fields.get(key) != null) ? original : "";
	}

	private void parseCollections() {
		Pattern collectionPattern = Pattern.compile("<!\\-\\-\\[collection: ([a-zA-Z0-9\\-]+)\\]\\-\\->(.*?)<!\\-\\-\\[/collection\\]\\-\\->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher collectionMatcher = collectionPattern.matcher(parsedHtml);
		StringBuffer output = new StringBuffer();
		while (collectionMatcher.find()) {
			collectionMatcher.appendReplacement(output, getCollectionHtml(collectionMatcher.group(1), collectionMatcher.group(2)));
		}
		collectionMatcher.appendTail(output);
		parsedHtml = output.toString();
	}

	private String getCollectionHtml(String key, String original) {
		// get collection iteration "chunks"
		String pre = getSegment(original, "pre");
		String post = getSegment(original, "post");
		String empty = getSegment(original, "empty");
		String each = getSegment(original, "each");

		return pre + " / " + post + " / " + empty + " / " + each + " / ";
	}

	private String getSegment(String html, String tag) {
		Pattern segmentPattern = Pattern.compile("<!\\-\\-\\[" + tag + "\\]\\-\\->(.*?)<!\\-\\-\\[/" + tag + "\\]\\-\\->", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher segmentMatcher = segmentPattern.matcher(html);
		if (segmentMatcher.find()) {
			return segmentMatcher.group(1);
		} else {
			return null;
		}
	}

	public String render() {
		parsedHtml = readSimpleFile();
		parseCollections();
		parseConditionalFields();
		parseFields();
		return parsedHtml;
	}

}
