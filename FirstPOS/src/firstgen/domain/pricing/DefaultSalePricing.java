package firstgen.domain.pricing;

import firstgen.domain.LineItem;
import firstgen.domain.Sale;
/**
 * Default sale pricing applies no special discount.
 * @author Dilbert
 */
public class DefaultSalePricing implements SalePricing {

	@Override
	public double getTotal(Sale sale) {
		double total = 0.0;
		for( LineItem item : sale ) total += item.getTotal();
		return total;
	}

	
}
