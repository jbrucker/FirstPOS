package firstgen.service;

import firstgen.domain.Sale;
/**
 * Tax caculator that applies a constant percentage tax rate.
 * @author Jim
 */
public class VATCalculator implements TaxCalculator {
	private double taxRate = 0.07;
	
	/**
	 * Set the sales tax rate applied to sale items
	 * @param rate is tax rate as a decimal number. 0.05 means 5% tax.
	 */
	public void setTaxRate(double rate) {
		this.taxRate = rate;
	}
	
	public double getTaxRate() {
		return taxRate;
	}

	public double getTax(Sale sale) {
		if ( sale == null ) return 0.0;
		return taxRate * sale.getSubtotal();
	}
}
