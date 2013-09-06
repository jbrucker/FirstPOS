package firstgen.service;
import firstgen.domain.Sale;
/**
 * Adapter interface for external tax calculator
 */
public interface TaxCalculator {
	/**
	 * Compute the tax on a Sale
	 * @param sale is the sale object to compute tax of
	 * @return the tax for this sale
	 */
	public double getTax(Sale sale);
}
