/**
 * 
 */
package firstgen.domain.pricing;

import firstgen.domain.Sale;

/**
 * @author Supachart
 *
 */
public interface SalePricing {
	
	public double getTotal(Sale sale);
}
