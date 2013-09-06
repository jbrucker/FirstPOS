package firstgen.domain;

/** A customer known to the store.
 *  This includes customers with store card.
 * @author James Brucker
 */
public class Customer {
	private String customerID;
	
	public Customer( String id ) {
		customerID = id;
	}
	
	public String getCustomerID() { return customerID; }
	
	public String toString() { return customerID; }

}
