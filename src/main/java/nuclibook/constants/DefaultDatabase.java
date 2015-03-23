package nuclibook.constants;

public class DefaultDatabase {

	public static String SQLQuery = "SET SQL_MODE = \"NO_AUTO_VALUE_ON_ZERO\";\n" +
			"SET time_zone = \"+00:00\";\n" +
			"\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(1, ' Viewed all patients', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(2, ' Created a new patient', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(3, ' Viewed patient', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(4, ' Updated patient', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(6, ' Attempted to view patients', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(7, ' Attempted to create a new patient', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(8, ' Attempted to view patient', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(9, ' Attempted to update patient', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(11, ' Viewed all tracers ', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(12, ' Created tracer', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(13, ' Updated tracer', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(14, ' Deleted tracer', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(15, ' Attempted to view all tracers ', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(16, ' Attempted to create tracer', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(17, ' Attempted to update tracer', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(18, ' Attempted to delete tracer', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(19, ' Created camera type', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(20, ' Viewed all camera types', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(21, ' Updated camera type', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(22, ' Deleted camera type', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(23, ' Attempted to view all camera types', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(24, ' Attempted to create camera type', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(25, ' Attempted to update camera type', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(26, ' Attempted to delete camera type', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(27, ' Created camera', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(28, ' Viewed all cameras', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(29, ' Updated camera', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(30, ' Deleted camera', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(31, ' Attempted to view all cameras', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(32, ' Attempted to create camera', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(33, ' Attempted to update camera', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(34, ' Attempted to delete camera', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(35, ' Created therapy', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(36, ' Viewed all therapies', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(37, ' Updated therapy', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(38, ' Deleted therapy', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(39, ' Attempted to view all therapies', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(40, ' Attempted to create therapy', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(41, ' Attempted to update therapy', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(42, ' Attempted to delete therapy', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(43, ' Viewed all staff roles', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(44, ' Created staff role', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(45, ' Updated staff role', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(46, ' Deleted staff role', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(47, ' Attempted to view all staff roles', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(48, ' Attempted to create staff role', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(49, ' Attempted to update staff role', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(50, ' Attempted to delete staff role', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(51, ' Created staff member', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(52, ' Viewed all staff accounts', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(53, ' Updated staff member', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(54, ' Deleted staff member', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(55, ' Attempted to create staff account', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(56, ' Attempted to create staff member with invalid password', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(57, ' Attempted to view all staff account', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(58, ' Attempted to update staff account', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(59, ' Attempted to delete staff account', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(60, ' Created staff absence', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(61, ' Viewed staff list en route to absences', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(62, ' Viewed staff absence', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(63, ' Updated staff absence', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(64, ' Deleted staff absence', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(65, ' Attempted to view staff list en route to absences', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(66, ' Attempted to create staff absence', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(67, ' Attempted to view staff absence', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(68, ' Attempted to update staff absence', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(69, ' Attempted to delete staff absence', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(70, ' Viewed staff list en route to availabilities', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(71, ' Created staff availability', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(72, ' Viewed staff availability', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(73, ' Updated staff availability', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(74, ' Deleted staff availability', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(75, ' Attempted to view staff list en route to availabilities', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(76, ' Attempted to create staff availability', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(77, ' Attempted to view staff availability', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(78, ' Attempted to update staff availability', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(79, ' Attempted to delete staff availability', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(80, ' Viewed booking calendar', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(81, ' Created booking', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(82, ' Viewed booking', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(83, ' Updated booking', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(85, ' Attempted to view booking calendar', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(86, ' Attempted to create booking', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(87, ' Attempted to view booking', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(88, ' Attempted to update booking', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(90, ' Logged in', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(91, ' Attempted to log in with wrong password', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(92, ' Attempted to log in with staff ID that doesn''t exist', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(93, ' Attempted to log in with a disabled staff ID', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(94, ' Logged out', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(95, ' Changed account password', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(96, ' Attempted to change account password', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(97, ' Viewed action log', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(98, ' Attempted to view action log', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(99, ' Viewed tracer orders', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(100, ' Attempted to view tracer orders', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(101, ' Imported patients', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(102, ' Attempted to import patients', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(103, ' Exported patients', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(104, ' Attempted to export patients', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(105, 'Created generic event', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(106, 'Viewed generic event', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(107, 'Updated generic event', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(108, 'Deleted generic event', 0);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(109, 'Attempted to view generic events', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(110, 'Attempted to create generic event', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(111, 'Attempted to update generic event', 1);\n" +
			"INSERT INTO `action_log_events` (`id`, `label`, `is_error`) VALUES(112, 'Attempted to delete generic event', 1);\n" +
			"\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(1, 'EDIT_PATIENTS', 'Create, edit and remove patients');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(3, 'EDIT_TRACERS', 'Create, edit and remove radio tracers');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(4, 'EDIT_CAMERAS', 'Create, edit and remove cameras and camera types');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(5, 'EDIT_THERAPIES', 'Create, edit and remove therapies');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(6, 'EDIT_STAFF', 'Create, edit and remove staff members and assign roles');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(7, 'EDIT_APPOINTMENTS', 'Create, edit and remove appointments');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(8, 'VIEW_PATIENT_LIST', 'View the patient list');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(9, 'VIEW_PATIENT_DETAILS', 'View the details and history of a single patient');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(10, 'VIEW_TRACERS', 'View the list of tracers');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(11, 'VIEW_CAMERAS', 'View the list of cameras and camera types');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(12, 'VIEW_THERAPIES', 'View the list of therapies');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(13, 'VIEW_STAFF', 'View the list of staff members');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(14, 'VIEW_APPOINTMENTS', 'View the calendar of existing appointments');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(15, 'VIEW_ACTION_LOG', 'View the staff action log');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(16, 'EDIT_STAFF_ROLES', 'Edit the permissions assigned to each staff role');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(17, 'VIEW_STAFF_ROLES', 'View the list of staff roles and assigned permissions');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(18, 'EDIT_STAFF_ABSENCES', 'Create, edit and remove staff absences');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(19, 'EDIT_STAFF_AVAILABILITIES', 'Create, edit and remove staff availabilities');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(20, 'VIEW_STAFF_AVAILABILITIES', 'View the list of staff availabilities');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(21, 'VIEW_STAFF_ABSENCES', 'View the list of staff absences');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(22, 'VIEW_APPOINTMENT_DETAILS', 'View a booking''s details');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(23, 'EDIT_STAFF_PASSWORD', 'Change other staff passwords');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(24, 'IMPORT_PATIENTS', 'Import patients to the database.');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(25, 'EXPORT_PATIENTS', 'Export the database of patients.');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(26, 'EDIT_GENERIC_EVENTS', 'Create, edit and remove generic events');\n" +
			"INSERT INTO `permissions` (`id`, `label`, `description`) VALUES(27, 'VIEW_GENERIC_EVENTS', 'View the list of generic events');\n" +
			"\n" +
			"INSERT INTO `staff` (`id`, `username`, `name`, `role`, `password_hash_0`, `password_salt_0`, `password_hash_1`, `password_salt_1`, `password_hash_2`, `password_salt_2`, `password_hash_3`, `password_salt_3`, `password_change_date`, `enabled`) VALUES(1, 'admin', 'Admin', 2, 'd70a1830fcf5bb3bf71050c1412b410c31f366efa14a528a00126df8561b0650bb9fca4ce7702caf735a4284af00f2c90a306f98e13497d59effbb266613189c', 'aibojuflq4glq9jv8a6s5v6nfn', NULL, NULL, NULL, NULL, NULL, NULL, 0, 1);\n" +
			"\n" +
			"INSERT INTO `staff_roles` (`id`, `label`, `enabled`) VALUES(2, 'System Admin', 1);\n" +
			"INSERT INTO `staff_roles` (`id`, `label`, `enabled`) VALUES(3, 'Patient Permissions Only', 0);\n" +
			"INSERT INTO `staff_roles` (`id`, `label`, `enabled`) VALUES(4, 'Staff Manager', 1);\n" +
			"INSERT INTO `staff_roles` (`id`, `label`, `enabled`) VALUES(5, 'Doctor', 1);\n" +
			"\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(451, 3, 8);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(452, 3, 1);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(453, 4, 19);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(454, 4, 18);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(455, 4, 13);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(456, 4, 20);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(457, 4, 21);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(623, 5, 19);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(624, 5, 18);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(625, 5, 26);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(626, 5, 27);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(627, 5, 9);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(628, 5, 1);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(629, 5, 11);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(630, 5, 4);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(631, 5, 3);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(657, 2, 19);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(658, 2, 17);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(659, 2, 18);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(660, 2, 15);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(661, 2, 16);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(662, 2, 13);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(663, 2, 14);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(664, 2, 22);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(665, 2, 23);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(666, 2, 20);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(667, 2, 21);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(668, 2, 26);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(669, 2, 27);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(670, 2, 24);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(671, 2, 25);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(672, 2, 9);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(673, 2, 8);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(674, 2, 7);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(675, 2, 6);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(676, 2, 1);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(677, 2, 11);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(678, 2, 12);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(679, 2, 10);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(680, 2, 5);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(681, 2, 4);\n" +
			"INSERT INTO `staff_role_permissions` (`id`, `staff_role_id`, `permission_id`) VALUES(682, 2, 3);\n";

}
