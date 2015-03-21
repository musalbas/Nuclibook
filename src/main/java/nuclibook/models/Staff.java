package nuclibook.models;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.constants.P;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.server.Renderable;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Model that represents a staff member in the database.
 */
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

	@DatabaseField(columnName = "password_hash_0")
	private String passwordHash;

	@DatabaseField(columnName = "password_salt_0")
	private String passwordSalt;

	@DatabaseField(columnName = "password_hash_1")
	private String passwordHash1;

	@DatabaseField(columnName = "password_salt_1")
	private String passwordSalt1;

	@DatabaseField(columnName = "password_hash_2")
	private String passwordHash2;

	@DatabaseField(columnName = "password_salt_2")
	private String passwordSalt2;

	@DatabaseField(columnName = "password_hash_3")
	private String passwordHash3;

	@DatabaseField(columnName = "password_salt_3")
	private String passwordSalt3;

	@DatabaseField(columnName = "password_change_date")
	private long passwordChangeDate;

	@ForeignCollectionField(eager = true)
	private ForeignCollection<StaffAvailability> availabilities;

	@ForeignCollectionField(eager = true)
	private ForeignCollection<StaffAbsence> absences;

	@DatabaseField(defaultValue = "true")
	private Boolean enabled;

	private ArrayList<P> permissions = null;

    /**
     * Initialises a staff member without the fields.
     */
	public Staff() {
	}

    /**
     * Gets the ID of the staff member.
     * @return the ID of the staff member.
     */
	public int getId() {
		return id;
	}

    /**
     * Sets the ID of the staff member.
     * @param id the ID of the staff member.
     */
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * Gets the username of the staff member.
     * @return the username of the staff member.
     */
	public String getUsername() {
		return username;
	}

    /**
     * Sets the username of the staff member.
     * @param username the username of the staff member.
     */
	public void setUsername(String username) {
		this.username = username;
	}

    /**
     * Gets the name of the staff member.
     * @return the name of the staff member.
     */
	public String getName() {
		return name;
	}

    /**
     * Sets the name of the staff member.
     * @param name the name of the staff member.
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets the role of the staff member.
     * @return the role of the staff member.
     */
	public StaffRole getRole() {
		return role;
	}

    /**
     * Sets the role of the staff member.
     * @param role the role of the staff member.
     */
	public void setRole(StaffRole role) {
		this.role = role;
	}

    /**
     * Gets whether it's enabled.
     * @return whether it's enabled.
     */
	public boolean isEnabled() {
		return enabled;
	}

    /**
     * Sets whether it's enabled.
     * @param enabled whether it's enabled.
     */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/* PASSWORDS */

    /**
     * Gets the date in which the password was last changed.
     * @return the date in which the password was last changed.
     */
	public DateTime getPasswordChangeDate() {
		return new DateTime(this.passwordChangeDate);
	}

    /**
     * Updates the date in which the password was last changed.
     */
	private void updatePasswordChangeDate() {
		this.passwordChangeDate = new DateTime().getMillis();
	}

    /**
     * Calls polymorphic method that checks to see if the password can be hashed.
     *
     * @param password
     * @return whether the password can be hashed
     * @throws CannotHashPasswordException
     */
	public boolean checkPassword(String password) throws CannotHashPasswordException {
		return checkPassword(this.passwordSalt, this.passwordHash, password);
	}

    /**
     * Check whether the password can be hashed.
     *
     * @param passwordSalt the salt used when hashing the password
     * @param passwordHash the hashed password
     * @param password the password itself
     * @return whether the password can be hashed
     * @throws CannotHashPasswordException
     */
	public boolean checkPassword(String passwordSalt, String passwordHash, String password) throws CannotHashPasswordException {
		// Add salt to password
		password = passwordSalt + password;

		// Check password
		return generateHash(password).equals(passwordHash);
	}

    /**
     * Sets the user's password.
     *
     * @param password the new password.
     * @throws CannotHashPasswordException
     */
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

		// Update password change date
		updatePasswordChangeDate();
	}

    /**
     * Checks to see if the user's new password is similar to the previous three.
     * @param password
     * @return whether the user's new password is similar to the previous three.
     * @throws CannotHashPasswordException
     */
	public boolean isInLastPasswords(String password) throws CannotHashPasswordException {
		// Check that the password is not equivalent to the past 3 passwords
		return checkPassword(this.passwordSalt1, this.passwordHash1, password)
				|| checkPassword(this.passwordSalt2, this.passwordHash2, password)
				|| checkPassword(this.passwordSalt3, this.passwordHash3, password)
				|| checkPassword(password);
	}

    /**
     * Generate a hash of the text given.
     * @param text the text to be hashed.
     * @return the hashed text.
     * @throws CannotHashPasswordException
     */
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

    /**
     * Gets the number of days remaining until the password has to change.
     * @return the number of days remaining until the password has to change.
     */
	public int getDaysRemainingToPasswordChange() {
		return 90 - Days.daysBetween(getPasswordChangeDate().toLocalDate(), new LocalDate()).getDays();
	}

	/* PERMISSIONS */

    /**
     * Loads the permissions the user has.
     * @param force whether it should force reload the permissions
     */
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

    /**
     * Refreshes the user's permissions.
     */
	public void refreshPermissions() {
		loadPermissions(true);
	}

    /**
     * Checks if the user has permission to access a functionality of the system.
     * @param p the permission.
     * @return whether the user has permission to access a functionality of the system.
     */
	public boolean hasPermission(P p) {
		loadPermissions(false);
		return permissions.contains(p);
	}

    /* AVAILABILITIES */

    /**
     * Gets the list of times and dates in which the staff will be available.
     * @return the availability of the staff.
     */
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

    /**
     * Gets a CSV string of the IDs of registered StaffAvailability
     * objects registered to this staff member, which is used in the front end.
     * @return a CSV string of related StaffAvailability objects
     */
	public String getStaffAvailabilityIdString() {
		List<StaffAvailability> staffAvailability = getStaffAvailability();
		if (staffAvailability.isEmpty()) return "0";
		StringBuilder sb = new StringBuilder();
		for (StaffAvailability sa : staffAvailability) {
			sb.append(sa.getId()).append(",");
		}
		return sb.substring(0, sb.length() - 1);
	}

    /**
     * Removes all related StaffAvailability objects.
     */
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

    /**
     * Adds a staff availability in the form of a StaffAvailability object.
     * @param sa a staff availability in the form of a StaffAvailability object.
     */
	public void addStaffAvailability(StaffAvailability sa) {
		sa.setStaff(this);
		AbstractEntityUtils.createEntity(StaffAvailability.class, sa);
	}

    /* ABSENCES */

    /**
     * Gets the list of times and dates in which the staff will be absent.
     * @return the list of times and dates in which the staff will be absent.
     */
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

    /**
     * Gets a CSV string of the IDs of registered StaffAbsence
     * objects registered to this staff member, which is used in the front end.
     * @return a CSV string of related StaffAbsence objects
     */
	public String getStaffAbsencesIdString() {
		List<StaffAbsence> staffAbsence = getStaffAbsences();
		if (staffAbsence.isEmpty()) return "0";
		StringBuilder sb = new StringBuilder();
		for (StaffAbsence sa : staffAbsence) {
			sb.append(sa.getId()).append(",");
		}
		return sb.substring(0, sb.length() - 1);
	}

    /**
     * Removes all related StaffAbsence objects.
     */
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

    /**
     * Adds a StaffAbsence object for this staff member.
     */
	public void addStaffAbsences(StaffAbsence sa) {
		sa.setStaff(this);
		AbstractEntityUtils.createEntity(StaffAbsence.class, sa);
	}

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>() {{
			put("id", ((Integer) getId()).toString());
			put("staff-id", ((Integer) getId()).toString());
			put("name", getName());
			put("staff-name", getName());
			put("username", getUsername());
			put("role-id", getRole() == null ? "0" : getRole().getId().toString());
			put("role-label", getRole() == null ? "-" : getRole().getLabel());
		}};
	}
}
