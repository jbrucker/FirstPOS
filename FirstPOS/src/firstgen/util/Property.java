package firstgen.util;

/** List of all the properties used in this project.
 *  The enumeration names are used to avoid name inconsistencies.
 *  To get a property value, use something like this:
 *     properties.getProperty( Property.NAME )
 * @author James Brucker 
 */
public enum Property {
	// names and attributes of properties.
	// Syntax:
	// PROPERTYNAME( "property_key" , "default value" )
	// keyname is the property key as a String
	// defaultvalue is the default value of the property,
	// which is used if the property isn't found in the properties file.

	/** the name of properties file. Should be on the classpath
	 * the key (firstgen.config) is the name that can be used on
	 * the java -D option to override default properties filename,
	 * e.g.:  java -Dfirstgen.config=my.properties
	 */
	PROPERTYFILE("firstgen.config", "firstgen.properties"), // name of properties file
	
	// only for JDBC.  Not used for Hibernate.
	/** url for JDBC connection to database */
	DATABASEURL("jdbc.url", "jdbc:mysql://localhost/firstgen" ),
	// database properties: these are the same names as the MySQL
	// JDBC driver properties, so that a Properties object can be
	// passed directly to the MySQL Connector upon instantiation!
	DATABASEUSER("user", "guest" ),
	DATABASEPASSWORD("password", "FatChance" ),
	/** name of class implementing JDBC Driver interface */
	DATABASEDRIVER("jdbc.drivers", "com.mysql.jdbc.Driver"),
	/** characterEncoding is a MySQL property: sets default character encoding */
	DATABASECHARSET("characterEncoding", "UTF-8"),
	
	/** fully qualified name of TaxAdapter class */
	TAXCALCULATOR("taxCalculator", "firstgen.service.ZeroTaxCalculator"),
	/** qualified name of SaleStrategy class*/
	SALEPRICING("percentDiscount", "firstgen.setvice.DiscountOverThreshold");
	
	
	private Property(String propname, String defaultvalue) {
		this.name = propname;
		this.value = defaultvalue;
	}
	
	public final String name; // name of the property in the properties file
	public final String value; // value of the property
}
