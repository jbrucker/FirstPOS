package firstgen.pos;

import firstgen.domain.ProductDescription;
import firstgen.domain.Sale;

/** 
 * The public interface between the POS and components
 * that use the POS to enter sale data, primarily
 * the User Interface component.
 * @author James Brucker
 */
public interface POSInterface {
	
	/** Start a new sale with no customer ID. */
	public void startSale( );
	
	/** Start a new sale with a given customer ID.
	 * The string corresponds to customer ID from
	 * a member card, telephone number, etc.
	 * @param customerID is a String describing the customer.
	 * It may be an empty string.
	 */
	public void startSale(String customerID);
	
	/** Add an item to the current sale.
	 * 
	 * @param id is the item ID, e.g. UPS code.
	 * @param quantity is the number of this item type to add.
	 * @return description for this item or null if item cannot be added,
	 *    e.g. item id not found in product catalog.
	 *  
	 *  Note: For items sold by weight or other unit, this is the
	 *  number of "units" of the item.  Should be double.
	 */
	public ProductDescription addItem(String id, int quantity);

	/** Remove an item from the current sale.
	 * 
	 * @param id is the item ID, e.g. UPS code.
	 * @param quantity is the number of items to remove.
	 * @return true if the item was successfully removed.
	 */
	public boolean removeItem(String id, int quantity);
	
	/** Get total so far, including tax and any discounts.
	 *  @return the total price, in what unit of money applies.
	 */	
	public double getTotal();
	
	/** Get total so far, without tax.
	 *  @return the total price, in what unit of money applies.
	 */
	public double getSubtotal();
	
	/**
	 * Get a reference to the current sale.
	 * The UI should not modify the sale directly.
	 * @return current sale or null if no sale in progress
	 */
	public Sale getSale();
	
	/**
	 * Add a viewer for the current Sale.
	 * @param view
	 */
	public void addSaleViewer(SaleViewer view);
	
	/** End a sale.
	 */
	public void endSale( );
}
