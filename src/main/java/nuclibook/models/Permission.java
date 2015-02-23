package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

@DatabaseTable(tableName = "permissions")
public class Permission  implements Renderable{

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(width = 32)
	private String label;

	@DatabaseField(width = 64)
	private String description;

	public Permission() {
	}

	public Integer getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>(){{
			put("id", getId().toString());
			put("label", getLabel());
			put("description", getDescription());
		}};
	}
}
