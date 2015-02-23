package nuclibook.models;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.server.Renderable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@DatabaseTable(tableName = "staff_roles")
public class StaffRole implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(width = 32)
	private String label;

	@ForeignCollectionField(eager = true)
	private ForeignCollection<StaffRolePermission> staffRolePermissions;

	@DatabaseField(defaultValue = "true")
	private Boolean enabled;

	public StaffRole() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Permission> getPermissions() {
		ArrayList<Permission> output = new ArrayList<>();
		CloseableIterator<StaffRolePermission> iterator = staffRolePermissions.closeableIterator();
		try {
			Permission p;
			while (iterator.hasNext()) {
				p = iterator.next().getPermission();
				if (p != null) output.add(p);
			}
		} finally {
			iterator.closeQuietly();
		}
		return output;
	}

	public void clearPermissions() {
		CloseableIterator<StaffRolePermission> iterator = staffRolePermissions.closeableIterator();
		try {
			Permission p;
			while (iterator.hasNext()) {
				AbstractEntityUtils.deleteEntity(StaffRolePermission.class, iterator.next());
			}
		} finally {
			iterator.closeQuietly();
		}
	}

	public void addPermission(Permission p) {
		AbstractEntityUtils.createEntity(StaffRolePermission.class, new StaffRolePermission(this, p));
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>(){{
			put("id", getId().toString());
			put("label", getLabel());
		}};
	}
}
