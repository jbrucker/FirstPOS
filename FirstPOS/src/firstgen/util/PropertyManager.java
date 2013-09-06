package firstgen.util;

import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/** Manage properties for this application.
 *  Uses static behavior and a private Properties object to manage properties.
 *  To specify a properties file, the first call should be setPropertiesFile(FILENAME)
 *  
 *  // get a property.
 *  // use named constants instead of Strings as keys, for safety.
 */
public class PropertyManager {

	// java.util.Properties object for getting properties from
	// a file or memory.
	private static Properties properties = null;
	private static final Logger logger = Logger.getLogger(PropertyManager.class);
	
	// private constructor prevents others from creating an instance
	private PropertyManager() {
		
	}

	private static void loadProperties( ) {
		if ( properties != null ) return;
		// what's the name of properties file?
		// if user specified on command line or environment variable
		// then use that value, otherwise use default value
		String filename = System.getProperty( Property.PROPERTYFILE.name,
				Property.PROPERTYFILE.value /* default value */ );
		logger.info("loading properties from "+filename);
		
		InputStream instream = null;
		properties = new Properties();
		ClassLoader loader = PropertyManager.class.getClassLoader();
		try {
			instream = loader.getResourceAsStream( filename );
			properties.load( instream );
		} catch (java.io.IOException e) {
			logger.error( "couldn't load properties from " + filename, e );
		} finally {
			if ( instream != null ) try { instream.close(); } catch(Exception e) {/* ignore */}
		}
		/* testing */
		for( Object obj : properties.keySet() ) {
			String key = (String)obj;
			logger.debug( String.format("%s=%s\n", key, properties.getProperty(key) ) );
		}
	}
	
	/** get a property value from the current properties.
	 *  If there is no value, then return a default value.
	 *  The default values are contained in the Property enum.
	 *  @param property is the property to get, using a value from Property enum.
	 *  @return current value of the property
	 */
	public static String getProperty( Property property ) {
		if ( properties == null )  loadProperties();
		return properties.getProperty( property.name, property.value /*default*/ );
	}
	
	/** set the value of a property
	 * @param property is the Property member to set
	 * @param newvalue is the new value for this property
	 */
	public static void putProperty( Property property, String newvalue ) {
		if ( properties == null )  loadProperties();
		properties.setProperty( property.name, newvalue );
	}

	/** save the values of Properties to a file as plain text 'key=value'
	 * @param filename is the name of the file to write properties to.
	 */
	public static void saveProperties( String filename ) {
		if ( properties == null )  loadProperties();
		try {
			java.io.FileOutputStream fout = new java.io.FileOutputStream( filename );
			properties.store(fout, 
					"properties saved on "+ java.util.Calendar.getInstance().toString() );
			fout.close();
		} catch ( java.io.IOException e ) { 
			logger.error( "error saving properties to " + filename, e );
		}
	}
	
	/** return a copy of the properties object.
	 * It returns a copy, so any subsequent changes made by the caller will not
	 * be reflected in the copy, and changes to the copy will not effect values
	 * retrieved using getProperty().
	 * @return copy of the current properties
	 */
	public static Properties getProperties() {
		if ( properties == null ) loadProperties();
		return properties;
	}

}
