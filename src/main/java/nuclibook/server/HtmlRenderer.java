package nuclibook.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlRenderer {

	private String templateFile;

	private HashMap<String, String> fields;

	public HtmlRenderer(String templateFile) {
		this.templateFile = templateFile;
		fields = new HashMap<>();
	}

	public void setField(String key, String value) {
		fields.put(key, value);
	}

	public void removeField(String key) {
		fields.remove(key);
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

	private String parseConditionalFields(String html) {
		Pattern fieldPattern = Pattern.compile("<!\\-\\-\\[if field: ([a-zA-Z0-9\\-]+)\\]\\-\\->(.*)<!\\-\\-\\[end if\\]\\-\\->");
		Matcher fieldMatcher = fieldPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (fieldMatcher.find()) {
			fieldMatcher.appendReplacement(output, getConditionalFieldValue(fieldMatcher.group(1), fieldMatcher.group(2)));
		}
		fieldMatcher.appendTail(output);
		return output.toString();
	}

	private String parseFields(String html) {
		Pattern fieldPattern = Pattern.compile("<!\\-\\-\\[field: ([a-zA-Z0-9\\-]+)\\]\\-\\->");
		Matcher fieldMatcher = fieldPattern.matcher(html);
		StringBuffer output = new StringBuffer();
		while (fieldMatcher.find()) {
			fieldMatcher.appendReplacement(output, getFieldValue(fieldMatcher.group(1)));
		}
		fieldMatcher.appendTail(output);
		return output.toString();
	}

	private String getFieldValue(String key) {
		return fields.containsKey(key) ? fields.get(key) : "";
	}

	private String getConditionalFieldValue(String key, String original) {
		return fields.containsKey(key) ? original : "";
	}

	public String render() {
		String html = readSimpleFile();
		html = parseConditionalFields(html);
		html = parseFields(html);
		return html;
	}

}
