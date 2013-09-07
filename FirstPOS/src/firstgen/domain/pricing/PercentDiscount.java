/**
 * 
 */
package firstgen.domain.pricing;

import firstgen.domain.Sale;

/**
 * @author Supachart
 *
 */
public class PercentDiscount implements SalePricing {

	double percentage = 0.05;
	/* (non-Javadoc)
	 * @see firstgen.service.SalePricing#getTotal(firstgen.domain.Sale)
	 */

	public double getTotal(Sale sale) {
		// TODO Auto-generated method stub
		if(sale.equals(null)) return 0;
		return percentage * sale.getSubtotal();
	}

}
