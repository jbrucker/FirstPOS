package firstgen.ui;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.*; // for AbstractTableModel

import org.apache.log4j.Logger;
import firstgen.domain.Sale;
import firstgen.domain.LineItem;

/** 
 * This is a TableModel used by JTable in Register UI.
 * The table model provides source of data displayed by a JTable.
 * This model provides JTable with info about items in a Sale.
 * The application needs to be careful to provide this model
 * with a current Sale object.
 * 
 * We may need to allow for possibility that there is no sale,
 * for example when POS is first initialized, and avoid causing
 * NullPointerException.
 * 
 * The TableModel needs a reference to the Sale that it should
 * get data from.
 * 
 * This class needs to an Observer of the Sale so that it will be
 * notified when the Sale data changes.
 * 
 * @author James Brucker
 */
public class SaleTableModel extends AbstractTableModel implements Observer {
	private static final long serialVersionUID = 1L;
	private Sale sale = null;
	private static Logger logger;

//TODO Internationalize these strings
	static final String [] COLUMNNAMES = 
	{ "Item ID", "Description", "Price", "Quantity" , "Subtotal" };
	
	public SaleTableModel( Sale sale ) {
		setSale(sale);
	}

	/* return the number of columns in the table.
	 * We want to display itemID, name, and quantity.
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return COLUMNNAMES.length;
	}

	/* How many rows are in the data?
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		if ( sale == null ) return 0;
		return sale.size();
	}
	
	/** optional: override method to return column names */
	public String getColumnName(int col) {
		return COLUMNNAMES[col];
	}

	/* Get a particular value from the data by row and column.
	 * We return the corresponding LineItem from the Sale.
	 * 
	 * @param row is the row in the Jtable. row 0 is first item in sale
	 * @param col is the column in the Jtable 
	 * @return if col==0 return the itemID, if col==1 return the item description, etc.,
	 *  for attributes in LineItem.
	 */
	public Object getValueAt(int row, int col) {
		// how are we going to find the entry for this row ?
		if ( sale == null ) return "";
		if ( row >= sale.size() ) return "";
		LineItem lineItem = sale.getLineItem(row);
		if ( lineItem == null ) {
			getLogger().warn("Sale returned null for item #"+row);
			return null;
		}
		// return requested column in table
		switch(col) {
			case 0: return lineItem.getItem().getItemID(); 
			case 1: return lineItem.getItem().getDescription();
			case 2: return lineItem.getItem().getPrice();
			case 3: return lineItem.getQuantity();
			case 4: return lineItem.getTotal();
			default: return "";
		}
	}
	
	/*
	 * this should be override of getColumnClass,
	 * but it throws exception when used.
	 * So I renamed it until I can fix the bug.
	 */
	@SuppressWarnings("unchecked") // ok to return raw Class object
	public Class<?> getColumnClass(int column) {
		switch(column) {
			case 0: return String.class; // ItemID
			case 1: return String.class; // Item Description
			case 2: return Double.class; // unit price
			case 3: return Integer.class; // quantity
			case 4: return Double.class; // subtotal
	        default: return Object.class;
		}
    }
	
	/** lazily get the logger for this class */
	private static Logger getLogger() {
		if ( logger == null ) logger = Logger.getLogger(SaleTableModel.class);
		return logger;
	}

	/**
	 * @param sale the sale to set
	 */
	public void setSale(Sale sale) {
		// first remove ourself as Observer of the old sale
		if ( this.sale != null ) sale.deleteObserver(this);
		// now become observer of the new sale
		this.sale = sale;
		if ( sale != null ) sale.addObserver(this);
		// notify listeners that table data has changed
		super.fireTableDataChanged( );
	}

	public void update(Observable subject, Object arg) {
		getLogger().debug("Notified by "+subject.getClass().getName() );
		// probably means that sale data has changed
		super.fireTableDataChanged();
	}
	
	
}
