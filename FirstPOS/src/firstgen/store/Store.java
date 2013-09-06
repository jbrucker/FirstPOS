package firstgen.store;

import org.apache.log4j.Logger;

/** 
 * The store doesn't do anything yet.
 * This uses the Singleton pattern.  Call getInstance() to get a store object.
 * @author James Brucker
 */
public class Store {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Store.class);

	private static Store store;
	private ProductCatalog productCatalog;
	
	/** constructor initializes data for items in the store */
	private Store( ) {
		productCatalog = new ProductCatalog();
	}
	
	/** get a reference to the Store object */
	public static Store getInstance() {
		if ( store == null ) store = new Store();
		return store;
	}
	
	public ProductCatalog getProductCatalog() {
		return productCatalog;
	}

}
		
		
		
		

			