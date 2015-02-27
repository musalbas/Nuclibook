package nuclibook.constants;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;


public class C {

	// server
	public static String MYSQL_URI;
	public static String MYSQL_USERNAME;
	public static String MYSQL_PASSWORD;

    public static void initConstants() throws ConfigurationException {;
        PropertiesConfiguration config = new PropertiesConfiguration("database.properties");
        MYSQL_URI = config.getString("database.URI");
        MYSQL_USERNAME =  config.getString("database.user.name");
        MYSQL_PASSWORD = decryptPassword(config.getString("database.user.password"));
    }

    private static String decryptPassword(String encryptedString) {

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("jasypt");
        String decryptedString = encryptor.decrypt(encryptedString);

        return decryptedString;
    }
}
