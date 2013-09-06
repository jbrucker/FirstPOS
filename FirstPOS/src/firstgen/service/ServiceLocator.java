package firstgen.service;

import org.apache.log4j.Logger;

import firstgen.util.Property;
import firstgen.util.PropertyManager;

public class ServiceLocator {
	private static Logger logger = null;
	
	public static TaxCalculator getTaxCalculator() {
		String className = PropertyManager.getProperty( Property.TAXCALCULATOR );
		try {
			Class clazz = Class.forName(className);	
			return (TaxCalculator) clazz.newInstance();
		} catch ( Exception e ) {
			getLogger().error("Failed to create TaxCalculator from class "+className, e);
			throw new RuntimeException( e );
		}
		
	}
	
	public static SalePricing getSalePricing() {
		String className = PropertyManager.getProperty( Property.SALEPRICING);
		try {
			Class clazz = Class.forName(className);	
			return (SalePricing) clazz.newInstance();
		} catch ( Exception e ) {
			getLogger().error("Failed to create SalePricing from class "+className, e);
			throw new RuntimeException( e );
		}
	}
	
	private static Logger getLogger( ) {
		if ( logger == null ) logger = Logger.getLogger(ServiceLocator.class);
		return logger;
	}
}
