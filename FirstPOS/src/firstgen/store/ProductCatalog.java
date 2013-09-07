package firstgen.store;

import org.apache.log4j.Logger;

import firstgen.domain.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/** 
 * The ProductCatalog contains information about items for sale.
 * It has a method to find any item by ID string.
 * Use dependency injection or a service locator (e.g. store)
 * to manage creation of product catalog.
 * For testability, we don't make this a singleton
 * @author James Brucker
 */
public class ProductCatalog {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProductCatalog.class);

	/** a map from item id to product description */
	private HashMap<String,ProductDescription> products;
	
	/** constructor initializes data for catalog */
	public ProductCatalog( ) {
		logger.info("Creating a mock product catalog from Stock enum");
		products = new HashMap<String,ProductDescription>( );
		for(Stock stock : Stock.values() ) {
			products.put( stock.id,
					new ProductDescription(stock.id, stock.description, stock.price) );
		
			String s = String.format("add to catalog: id %s %s", stock.id, stock.description);
			logger.info( s );
		}
		logger.info("Catalog contains "+products.size()+" items");
	}

	
	/** return a ProductDescription that matches the given id.
	 * @param id is the Item ID string of the item to get.
	 * @return a LineItem having the given id.
	 *        If the item ID is not found in the Store's inventory,
	 *        then a null value is returned.
	 */
	public ProductDescription getProduct( String id ) {
		logger.info("looking for item "+id);
		ProductDescription item;
		try {
			item = products.get( id );
		} catch ( RuntimeException e ) {
			logger.error("inventory threw exception "+e.getMessage());
			return null;
		}
		
		if ( item == null ) {
			logger.info("Item id "+ id + " not found in catalog");
		}
		else logger.info("found "+id );
		return item;
	}

	/**
	 * Add a product description to catalog
	 * @param id is the ID of the product
	 * @param pd is the product description of the item
	 * @return true if operation succeeds, false if this ID already in catalog
	 */
	protected boolean addProduct(String id, ProductDescription pd ) {
		if ( products.containsKey(id) ) return false;
		products.put(id, pd);
		return true;
	}

	/**
	 * Search for products containing a given string in their description
	 * @param match the string to search for, case insensitive
	 * @return List of matching products.  Empty list if no matches.
	 */
	public List<ProductDescription> findProduct(String match) {
		match = match.toLowerCase();
		List<ProductDescription> list = new ArrayList<ProductDescription>();
		for(ProductDescription pd : products.values() ) {
			if ( pd.getDescription().toLowerCase().contains(match) ) list.add(pd);
		}
		return list;
	}
}