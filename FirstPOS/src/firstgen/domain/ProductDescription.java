package firstgen.domain;

/** Describes an item in the store's inventory,
 *  including ID (such as upc number), description, and unit price.
 *  LineItems are compared by ID.
 *  
 * @author James Brucker
 */
public class ProductDescription implements Comparable<ProductDescription> {
	protected String itemID;
	protected String description;
	protected double price;
	
        	
	/** Default constructor for product description,
         * needed for instantiation by persistence framework.
	 */
	public ProductDescription( ) {
		this.itemID = null;
		this.description = "";
	}
        
	/** Create a new item descrption.
	 * @param itemID the ID of item, e.g. ups code
	 * @param description is a description of this item
	 * @param price is the unit sale price of the item
	 * @prerequisite price is non-negative and itemID is not null
	 */
	public ProductDescription(String itemID, String description, double price) {
		super(); // Eclipse added this
		assert itemID != null : this.getClass().getName() + ": creating new item with null itemID";
		assert price >= 0 : this.getClass().getName() + ": creating new item with negative price " + price;
		this.itemID = (itemID==null)?"":itemID.trim();
		this.description = (description==null)?"":description.trim();
		this.price = price;
	}
	
	/** Create a new line item as a copy of another line item.
	 * @param other is the line item to make copy of
	 */
	public ProductDescription( ProductDescription other ) {
		this.itemID = other.itemID;
		this.description = other.description;
		this.price = other.price;
	}
	
	/** compare two line items using itemID.
	 * @param other is the other item for comparison with this one.
	 * @return < 0 if this item is first, 0 if equal, > 0 if this item comes later.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo( ProductDescription other ) {
		if ( other == null ) return -1;
		return itemID.compareToIgnoreCase( other.itemID );
	}
	
	/** compare two line items using itemID.
	 * @param other is the other item for comparison with this one.
	 * @return true of both items have the same ID
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public boolean equals( ProductDescription other ) {
		if ( other == null ) return false;
		return itemID.equalsIgnoreCase( other.itemID );
	}
	
	/** Get the item's description
	 * @return the description
	 */
	public String getDescription() { return description; }

	/** Get the item's ID string
	 * @return the itemID
	 */
	public String getItemID() { return itemID; }

	/** Get the item's unit price
	 * @return the price
	 */
	public double getPrice() { return price; }
	
	public String toString() { return String.format("%s @ %.2f", 
			description, price); }

	/**
	 * set the unit price of this item
	 * @param price is the item's price
	 */
    protected void setPrice(double price) {
        this.price = price;
    }

	/**
	 * set the id of this item
	 * @param itemID is the item's ID
	 */
    protected void setItemID(String itemID) {
        this.itemID = itemID;
    }

	/**
	 * set the description of this item
	 * @param description the item's description
	 */
    protected void setDescription(String description) {
        this.description = description;
    }
}