package nuclibook.models;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.server.Renderable;

import java.sql.SQLException;
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

	public void refreshPermissions() {
		try {
			staffRolePermissions.refreshCollection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	public String getPermissionSummary() {
		// get permissions in a simple format
		List<Permission> permissions = getPermissions();

		if (permissions.size() == 0) {
			return "None";
		}

		if (permissions.size() == 1) {
			return permissions.get(0).getDescription();
		}

		if (permissions.size() == 2) {
			return permissions.get(0).getDescription() + "<br />" + permissions.get(1).getDescription();
		}

		String output = permissions.get(0).getDescription() + "<br />" + permissions.get(1).getDescription() + "<br />";
		output += "<div id=\"more-permissions-" + getId() + "\" style=\"display: none;\">";
		for (int i = 2; i < permissions.size(); ++i) {
			output += permissions.get(i).getDescription() + "<br />";
		}
		output = output.substring(0, output.length() - 6);
		output += "</div>";
		output += "<span>+ " + (permissions.size() - 2) + " more (<a href=\"javascript:;\" class=\"more-permissions\" data-target=\"more-permissions-" + getId() + "\">show</a>)</span>";
		return output;
	}

	public String getPermissionsIdString() {
		List<Permission> permissions = getPermissions();
		if (permissions.isEmpty()) return "0";
		StringBuilder sb = new StringBuilder();
		for (Permission p : permissions) {
			sb.append(p.getId()).append(",");
		}
		return sb.substring(0, sb.length() - 1);
	}

	public void clearPermissions() {
		if (staffRolePermissions == null) return;
		CloseableIterator<StaffRolePermission> iterator = staffRolePermissions.closeableIterator();
		try {
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
		return new HashMap<String, String>() {{
			put("id", getId().toString());
			put("label", getLabel());
			put("permission-ids", "IDLIST:" + getPermissionsIdString());
			put("permission-summary", getPermissionSummary());
		}};
	}
}
