package firstgen.pos;
import java.util.ArrayList;
import java.util.List;

import firstgen.domain.*;
import firstgen.service.ServiceLocator;
import firstgen.service.TaxCalculator;
import firstgen.store.ProductCatalog;
import firstgen.store.Store;

/** The register is the external interface to the POS,
 *  as seen by the cashier.
 *  @author James Brucker
 */
public class Register implements POSInterface {
	
	private Sale sale = null; // a Sale object records items in this sales transaction
	private Store store;
	private ProductCatalog productCatalog;
	private List<SaleViewer> saleviewers;
	private TaxCalculator taxCalculator;

	public Register() {
		saleviewers = new ArrayList<SaleViewer>();
		taxCalculator = ServiceLocator.getTaxCalculator();
	}
	
	/* (non-Javadoc)
	 * @see nextgen.pos.POSInterface#addItem(java.lang.String, int)
	 */
	public ProductDescription addItem(String id, int quantity) {
		// the store should be set before any sale begins
		assert store != null : "Store has not been set in Register";
		assert productCatalog != null : "ProductCatalog has not been set in Register";

		if ( sale == null ) startSale();
		ProductDescription pd = productCatalog.getItem( id );
		sale.addItem(pd, quantity);
		return pd;
	}

	/* (non-Javadoc)
	 * @see nextgen.pos.POSInterface#getSubtotal()
	 */
	public double getSubtotal() {
		if ( sale == null ) return 0.0;
		return sale.getSubtotal();
	}

	/* (non-Javadoc)
	 * @see nextgen.pos.POSInterface#getTotal()
	 */
	public double getTotal() {
		if ( sale == null ) return 0.0;
		return sale.getTotal();
	}

	/* (non-Javadoc)
	 * @see nextgen.pos.POSInterface#removeItem(java.lang.String, int)
	 */
	public boolean removeItem(String id, int quantity) {
		if ( sale == null ) return false;
		return sale.removeItem( id, quantity );
	}

	/* (non-Javadoc)
	 * @see nextgen.pos.POSInterface#startNewSale()
	 */
	public void startSale() {
		Sale sale = new Sale();
		sale.setTaxCalculator(taxCalculator);
		setSale( sale );
	}

	/* (non-Javadoc)
	 * @see nextgen.pos.POSInterface#startNewSale(java.lang.String)
	 */
	public void startSale(String customerID) {
		sale = new Sale( /*customerID*/ );
		sale.setTaxCalculator(taxCalculator);
		setSale( sale );
	}

	public void setStore( Store store ) {
		this.store = store;
		productCatalog = store.getProductCatalog();
	}

	/* (non-Javadoc)
	 * @see nextgen.pos.POSInterface#startNewSale()
	 */
	public Sale getSale() {
		return sale;
	}

	/**
	 * Get the current ProductCatalog.
	 * @return the productCatalog
	 */
	public ProductCatalog getProductCatalog() {
		return productCatalog;
	}

	/**
	 * Set the ProductCatalog used to look-up items.
	 * This should normally be done during initialization and
	 * not changed during operation.
	 * @param productCatalog the catalog to use
	 */
	public void setProductCatalog(ProductCatalog productCatalog) {
		this.productCatalog = productCatalog;
	}

	public void addSaleViewer(SaleViewer view) {
		if ( !saleviewers.contains(view) ) {
			saleviewers.add(view);
			view.setSale(sale);
		}
	}
	
	/**
	 * internal method to set the sale also updates the sale
	 * in all saleviews. 
	 * @param sale the current sale
	 */
	private void setSale(Sale sale) {
		this.sale = sale;
		sale.setTaxCalculator(taxCalculator);
		for(SaleViewer view: saleviewers ) view.setSale(sale);
	}

	public void endSale() {
		// TODO prompt for payment
		// TODO record sale in ledger
		startSale();
	}
}