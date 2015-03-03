package nuclibook.models;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.constants.P;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.server.Renderable;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@DatabaseTable(tableName = "staff")
public class Staff implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(width = 64)
	private String username;

	@DatabaseField(width = 64)
	private String name;

	@DatabaseField(columnName = "role", foreign = true, foreignAutoRefresh = true)
	private StaffRole role;

	@DatabaseField
	private String passwordHash;

	@DatabaseField
	private String passwordSalt;

	@DatabaseField
	private String passwordHash1;

	@DatabaseField
	private String passwordSalt1;

	@DatabaseField
	private String passwordHash2;

	@DatabaseField
	private String passwordSalt2;

	@DatabaseField
	private String passwordHash3;

	@DatabaseField
	private String passwordSalt3;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<StaffAvailability> availabilities;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<StaffAbsence> absences;

	@DatabaseField(defaultValue = "true")
	private Boolean enabled;

	private ArrayList<P> permissions = null;

	public Staff() {
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StaffRole getRole() {
		return role;
	}

	public void setRole(StaffRole role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/* PASSWORDS */

	public boolean checkPassword(String password) throws CannotHashPasswordException {
		return checkPassword(this.passwordSalt, this.passwordHash, password);
	}

	public boolean checkPassword(String passwordSalt, String passwordHash, String password) throws CannotHashPasswordException {
		// Add salt to password
		password = passwordSalt + password;

		// Check password
		return generateHash(password).equals(passwordHash);
	}

	public void setPassword(String password) throws CannotHashPasswordException {
		// Generate new random salt
		SecureRandom random = new SecureRandom();
		String salt = new BigInteger(130, random).toString(32);

		// Add salt to password
		password = salt + password;

		// Generate password hash
		String hash = generateHash(password);

		// Update last 3 passwords
		this.passwordSalt3 = this.passwordSalt2;
		this.passwordHash3 = this.passwordHash2;

		this.passwordSalt2 = this.passwordSalt1;
		this.passwordHash2 = this.passwordHash1;

		this.passwordSalt1 = this.passwordSalt;
		this.passwordHash1 = this.passwordHash;

		// Update fields
		this.passwordSalt = salt;
		this.passwordHash = hash;
	}

	/**
	 * Checks that a new password does not infringe upon the password policy.
	 * @param password new password
	 * @return true if the password is valid according to the password policy, otherwise false.
	 */
	private boolean validateNewPassword(String password) throws CannotHashPasswordException {
		// Check that the password is not equivalent to the past 3 passwords
		if (checkPassword(this.passwordSalt1, this.passwordHash1, password)
				|| checkPassword(this.passwordSalt2, this.passwordHash2, password)
				|| checkPassword(this.passwordSalt3, this.passwordHash3, password)) {
			return false;
		}

		// Check that the password is at least 6 characters long
		if (password.length() < 6) {
			return false;
		}

		return true;
	}

	private String generateHash(String text) throws CannotHashPasswordException {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			throw new CannotHashPasswordException("No such MessageDigest algorithm");
		}

		byte[] hash;
		try {
			hash = digest.digest(text.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new CannotHashPasswordException("Unsupported text encoding");
		}

		return String.format("%0128x", new BigInteger(1, hash));
	}

	/* PERMISSIONS */

	private void loadPermissions(boolean force) {
		// already done the work?
		if (!force && permissions != null) return;

		// load all permissions (raw)
		role.refreshPermissions();
		List<Permission> permissionList = role.getPermissions();

		// create new array
		permissions = new ArrayList<>(permissionList.size());

		// convert to enum list
		for (Permission p : permissionList) {
			try {
				permissions.add(P.valueOf(p.getLabel()));
			} catch (IllegalArgumentException | NullPointerException e) {
				// at least we tried!
			}
		}
	}

	public void refreshPermissions() {
		loadPermissions(true);
	}

	public boolean hasPermission(P p) {
		loadPermissions(false);
		return permissions.contains(p);
	}

    /* AVAILABILITIES */

    public List<StaffAvailability> getStaffAvailability() {
        ArrayList<StaffAvailability> output = new ArrayList<>();
        CloseableIterator<StaffAvailability> iterator = availabilities.closeableIterator();
        try {
            StaffAvailability sa;
            while (iterator.hasNext()) {
                sa = iterator.next();
                if (sa != null) output.add(sa);
            }
        } finally {
            iterator.closeQuietly();
        }
        return output;
    }

    public String getStaffAvailabilityIdString() {
        List<StaffAvailability> staffAvailability = getStaffAvailability();
        if (staffAvailability.isEmpty()) return "0";
        StringBuilder sb = new StringBuilder();
        for (StaffAvailability sa : staffAvailability) {
            sb.append(sa.getId()).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public void clearStaffAvailability() {
        CloseableIterator<StaffAvailability> iterator = availabilities.closeableIterator();
        try {
            while (iterator.hasNext()) {
                AbstractEntityUtils.deleteEntity(StaffAvailability.class, iterator.next());
            }
        } finally {
            iterator.closeQuietly();
        }
    }

    public void addStaffAvailability(StaffAvailability sa) {
        sa.setStaff(this);
        AbstractEntityUtils.createEntity(StaffAvailability.class, sa);
    }

    /* ABSENCES */

    public List<StaffAbsence> getStaffAbsences() {
        ArrayList<StaffAbsence> output = new ArrayList<>();
        CloseableIterator<StaffAbsence> iterator = absences.closeableIterator();
        try {
            StaffAbsence sa;
            while (iterator.hasNext()) {
                sa = iterator.next();
                if (sa != null) output.add(sa);
            }
        } finally {
            iterator.closeQuietly();
        }
        return output;
    }

    public String getStaffAbsencesIdString() {
        List<StaffAbsence> staffAbsence = getStaffAbsences();
        if (staffAbsence.isEmpty()) return "0";
        StringBuilder sb = new StringBuilder();
        for (StaffAbsence sa : staffAbsence) {
            sb.append(sa.getId()).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public void clearStaffAbsences() {
        CloseableIterator<StaffAbsence> iterator = absences.closeableIterator();
        try {
            while (iterator.hasNext()) {
                AbstractEntityUtils.deleteEntity(StaffAbsence.class, iterator.next());
            }
        } finally {
            iterator.closeQuietly();
        }
    }

    public void addStaffAbsences(StaffAbsence sa) {
        sa.setStaff(this);
        AbstractEntityUtils.createEntity(StaffAbsence.class, sa);
    }

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>() {
			{
				put("id", ((Integer) getId()).toString());
				put("name", getName());
				put("username", getUsername());
				put("role-id", getRole() == null ? "0" : getRole().getId().toString());
				put("role-label", getRole() == null ? "-" : getRole().getLabel());
			}
		};
	}
}
