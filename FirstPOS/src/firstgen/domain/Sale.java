package firstgen.domain;
import java.util.*;

import org.apache.log4j.Logger;

import firstgen.service.SalePricing;
import firstgen.service.TaxCalculator;

/** A Sale object contains information about a sales transaction,
 *  including customer, date, list of items purchased, ...
 *  It has methods to compute and return the total price.
 *  
 * @author James Brucker
 */
public class Sale extends Observable implements Iterable<LineItem> {
	
	private Calendar date; // date of the sale
	private List<LineItem> items; // items in the sale
	private Customer customer;
	private TaxCalculator taxCalculator;
	private SalePricing salePricing;
	

	private static Logger logger = Logger.getLogger( Sale.class );
		
	/** create a new sale with a given customer */
	public Sale( Customer customer ) {
		this.customer = customer;
		items = new ArrayList<LineItem>( );
		date = Calendar.getInstance(); // maybe updated when the sale ends?
	}
	
	/** create a new sale with unknown customer */
	public Sale( ) {
		this( (Customer)null );
		date = Calendar.getInstance();
	}
	
	public void setTaxCalculator(TaxCalculator taxCalculator) {
		this.taxCalculator = taxCalculator;
	}
	
	public void setSalePricing(SalePricing salePricing) {
		this.salePricing = salePricing;
	}

	/** end a sale.  This method consolidates the list of
	 *  sales items (combine multiple occurences of the same
	 *  item into one) and sorts them by item ID.
	 *  TODO: Do we want to update the date of the sale?
	 *  It could conceivably affect the pricing that a
	 *  customer receives.
	 */
	public void endSale( ) {
		// don't use an iterator because it throws an exception
		// if you change the List while iterating over it.
		if ( items.size() == 0 ) return; 

//		java.util.Collections.sort( items );

//TODO revise this logic so it works on an unsorted list

		// since items are now sorted by ID, iterate over the list comparing
		// consecutive items for duplicate IDs.  Start from the end of the list
		// so it can shrink as items are deleted (without affecting loop logic).
		LineItem lastitem = items.get( items.size()-1 ); // get the last item
		for( int k=items.size()-2; k>=0; k-- ) {
			LineItem thisitem = items.get(k);
			if ( thisitem.equals( lastitem ) ) {
				// same item -- consolidate
				thisitem.setQuantity( thisitem.getQuantity() + lastitem.getQuantity() );
				items.remove( k+1 );
			}
			lastitem = thisitem;
		}
	}
	
	/** add an item to the current sale.
	 * @param item is the description of the item to add
	 * @param quantity is the quantity of the item to add
	 * @return true if item added successfully to sale.
	 */
	public boolean addItem( ProductDescription item, int quantity ) {
		logger.debug("adding item = " + item);
		if ( item == null ) {
			logger.warn("attempt to add null item to sale");
			return false;
		}
		if ( quantity == 0 ) {
			logger.warn("attempt to add item with quantity 0");
			return false;
		}
		if ( quantity < 0 ) {
			logger.error("attempt to add item with quantity < 0");
			return false;
		}
		
		boolean check = false;
		for( int k = items.size()-1; k >= 0; k--) {
			LineItem sli = items.get(k);
			if ( item.itemID.equals( sli.getItem().getItemID() ) )
			{
				sli.setQuantity( sli.getQuantity() + quantity );
				check = true;
				break;
			}
		}
		if(!check)
		{
			LineItem saleItem = new LineItem( item, quantity );
			// this should always succeed (true)
			//boolean result =
			items.add( saleItem );
		}
		setChangedAndNotifyObservers();
		return true;
	}
	
	/** delete an item from the current sale.
	 * @param itemID is the ID of the item to delete
	 * @param quantity is the quantity of the item to delete
	 * @return true if the item was deleted from the sale
	 * @precondition itemID is not null
	 */
	public boolean removeItem( String itemID, int quantity ) {
		assert itemID != null : "Null itemID in removeItem";
		if ( quantity <= 0 ) {
			logger.error("Attempt to remove quantity = "+quantity);
			// could be simple UI problem, so return normally
			return false;
		}
		// count how many units of this item are in the sale
		int count = 0;
		for( LineItem sli : items ) 
			if ( itemID.equals( sli.getItem().getItemID() ) ) count += sli.getQuantity();
		if ( count < quantity ) return false; // not enough units to delete
		// now work backwards through the list deleting units.
		for( int k = items.size()-1; k >= 0; k--) {
			LineItem sli = items.get(k);
			if ( !  itemID.equals( sli.getItem().getItemID() ) ) continue;
			if ( sli.getQuantity() <= quantity ) {
				// this salesLineItem has fewer units than we need to delete,
				// so delete the entire item!
				quantity -= sli.getQuantity();
				items.remove(k);
				if ( quantity <= 0 ) break;
			}
			else {
				// this salesLineItem has more units than we need to delete,
				// so just reduce the quantity.  Then break out of loop.
				sli.setQuantity( sli.getQuantity() - quantity );
				quantity = 0;
				break;
			}
		}
		setChangedAndNotifyObservers();
 		return true; // return true so cashier knows its OK to remove 
 					 // these items from the sales basket.
	}
	
	/** get the total value of the sale so far, without tax or discount.
	 * @return gross sale total so far, without tax or discount.
	 */
	public double getSubtotal( ) {
		double subtotal = 0;
		for( LineItem item : items ) subtotal += item.getTotal();
		return subtotal;
	}
	
	/** get the tax on the sale so far.
	 * @return tax applicable to the sales items.
	 */
	public double getTax( ) {
		double tax = 0;
		if ( taxCalculator != null ) tax = taxCalculator.getTax(this);
		return tax;
	}
	
	/** get the total value of the sale, net of tax and discount.
	 * @return gross sale total so far, with tax or discount.
	 */
	public double getTotal( ) {
		double subtotal = getSubtotal();
		double tax = getTax();
		double discount = salePricing.getTotal(this);
		return (subtotal + tax) - discount;
	}
	
	/** get precondition for discount product.
	 * @return discount value to sale items.
	 */
	public double getPreDiscountTotal() {
		//TODO implement
		return 0;		
	}

	/** Create and return an iterator of the sale items.
	 * Note that if the contents of the sale change after creating
	 * an iterator that the Iterator will probably be invalid and
	 * may throw exception if used. 
	 * @return an Iterator of items in this sale
	 */
	public Iterator<LineItem> iterator() {
		return items.iterator();
	}
	/** Return the number of items in the sale */
	public int size() {
		return items.size();
	}
	/**
	 * Get the k-th item in the list of sale items
	 * @param k is index of item to get
	 */
	public LineItem getLineItem(int k) {
		if ( k >= 0 && k < items.size() ) return items.get(k);
		else return null;
	}

	/**
	 * Get the date that the sale was made.  This is the date that the
	 * sale object was created.
	 * @return the date of this sale
	 */
	public Calendar getDate() {
		return date;
	}
	
	public void setChangedAndNotifyObservers() {
		super.setChanged();
		super.notifyObservers();
	}
}
