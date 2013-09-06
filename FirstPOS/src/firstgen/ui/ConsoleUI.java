package firstgen.ui;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import firstgen.domain.ProductDescription;
import firstgen.domain.Sale;
import firstgen.pos.POSInterface;
import firstgen.pos.Register;
import firstgen.pos.SaleViewer;

/**
 * Simple console UI to demonstrate core domain classes.
 * @author James Brucker
 *
 */
public class ConsoleUI implements SaleViewer {
	// the controller
	private POSInterface register;
	// for MVC, need a reference to the Sale
	private Sale sale;

	/**
	 * @param register
	 */
	public ConsoleUI(POSInterface register) {
		super();
		this.register = register;
	}
	
	public void run() {
		Scanner console = new Scanner( System.in );
		PrintStream out = System.out;
		out.println("======= POS ========");
		out.println("Starting a new sale.");
		out.println("====================");
		register.startSale();
		out.println("Enter item ID and quantity; omit quantity for 1. Example:  011 3");
		out.println("Enter END to end sale or ? to view some items in catalog.");
		do {
			out.print("input item id and quantity ('end' to end sale): ");
			String id = console.nextLine();
			if ( id == null ) break; // shouldn't happen
			id = id.trim();
			int qnty = 1;
			// check for optional quantity
			String [] words = id.split("\\s+");
			if ( words.length > 1 ) {
				id = words[0];
				try {
					qnty = Integer.parseInt(words[1]);
				} catch ( NumberFormatException e ) { qnty = -1; }
			}
			if ( id.equalsIgnoreCase("end") ) break;
			if ( id.equals("?") ) { showCatalog(); continue; }
			if ( qnty <= 0 ) {
				out.println("Invalid quantity: must be integer 1 or more.");
				continue;
			}
			ProductDescription pd = register.addItem( id, qnty);
			// this stuff doesn't use MVC.
			// using MVC we'd let the controller tell us what to display
			// or let the model notify us of a change in the model
			if ( pd == null ) {
				out.printf("%s not found\n", id);
				continue;
			}
			else showItem(pd);
			
		} while ( true );
		// show sale total
		System.out.printf("Total is %.2f\n", register.getTotal() );
	}
	
	private void showCatalog() {
		List<ProductDescription> products =
				((Register)register).getProductCatalog().findProduct("");
		int maxitems = Math.min(15, products.size());
		for(int k=0; k<maxitems; k++) {
			showItem( products.get(k) );
		}
	}
	
	private void showItem(ProductDescription pd) {
		System.out.printf("%-10.10s %s @ %.2f\n", pd.getItemID(), 
				pd.getDescription(), pd.getPrice() );
	}

	/**
	 * Set the current sale object
	 * @param sale the Sale object to use
	 */
	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public void setMessage(int type, String message) {
		System.out.println( message );
		
	}
}