package nuclibook.constants;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class C {

	// server
	public static String MYSQL_URI;
	public static String MYSQL_USERNAME;
	public static String MYSQL_PASSWORD;

	// security
	public static int AUTOMATIC_TIMEOUT;

	public static void initConstants() throws ConfigurationException {
		PropertiesConfiguration config = new PropertiesConfiguration("database.properties");
		MYSQL_URI = config.getString("database.URI");
		MYSQL_USERNAME = config.getString("database.user.name");
		MYSQL_PASSWORD = config.getString("database.user.password");
		AUTOMATIC_TIMEOUT = config.getInt("security.automatictimeout");
	}

}
