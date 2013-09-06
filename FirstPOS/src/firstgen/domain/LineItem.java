package firstgen.domain;

/** LineItem is an item included in a sales transaction.
 *  It contains a ProductDescription and a quantity.
 * @author James Brucker
 */
public class LineItem implements Comparable<LineItem> {

	/** quantity of the LineItem in this sale */
	protected int quantity;
	/** the line item */
	protected ProductDescription item;
	

	/** Create a new SalesLineItem containing a line item and quantity
	 * @param item is the Item that is being purchased
	 * @param quantity is the number of units of the item to purchase
	 */
	public LineItem(ProductDescription item, int quantity) {
//TODO should we clone the item?
		this.item = item;
		this.quantity = quantity;
	}
	
	/** Return the gross price of this salesLineItem.
	 * This is the quantity times the price of the LineItem.
	 * @return the gross price of this salesLineItem, without tax or discount.
	 */
	public double getTotal() {
		return quantity * item.getPrice();
	}

	/** Get the number of units of the LineItem in this sales item.
	 * @return the number of units of the LineItem in this sales item.
	 */
	public int getQuantity() {
		return quantity;
	}

	/** Set the number of units of the LineItem in this sales item.
	 * @param quantity the new number of LineItem units in this sales item.
	 * @prerequisite the quantity is not negative
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/** compare two SalesLineItems.  They are equal only if the LineItem AND quantity
	 *  are the same.
	 */
	public boolean equals( LineItem other ) {
		if ( other == null ) return false;
		return quantity == other.quantity && item.equals(other.item);
	}
	/** 
	 * Get product description of item in this SalesLineItem
	 * @return the item
	 */
	public ProductDescription getDescription() {
		return item;
	}

	/** Get string description of the item in this SalesLineItem
	 * @return the item
	 */
	public String toString() {
		return item.getDescription();
	}
	
	/* 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(LineItem other) {
		if ( other == null ) return -1; // objects before nulls
		int comp = this.item.compareTo(other.item);
		if ( comp != 0 ) return comp;
		return quantity - other.quantity;
	}

	/**
	 * Get a reference to the item descriptor of this LineIitem
	 * @return Item descriptor of this line item
	 */
	public ProductDescription getItem() {
		return item;
	}


}
