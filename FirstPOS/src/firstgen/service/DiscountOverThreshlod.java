/**
 * 
 */
package firstgen.service;

import firstgen.domain.Sale;

/**
 * @author Supachart
 *
 */
public class DiscountOverThreshlod implements SalePricing {

	double discount = 0.0;
	double threshold = 0.0;
	/* (non-Javadoc)
	 * @see firstgen.service.SalePricing#getTotal(firstgen.domain.Sale)
	 */

	public double getTotal(Sale sale) {
		// TODO Auto-generated method stub
		double pdt = sale.getSubtotal();
		if(pdt<threshold)
			return pdt;
		else
			return pdt - discount;
		//return 0;
	}

}
