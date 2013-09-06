package firstgen.service;

import firstgen.domain.ProductDescription;
import firstgen.store.ProductCatalog;
import java.util.List;

/**
 * ProductService locates and returns Product Descriptions
 * @author Jim
 */
public interface ProductService {
	/**
	 * Get the product with matching product ID
	 * @param id the product id to get
	 * @return a product description or null if no matching product
	 */
	public ProductDescription getProduct(String id);
	/**
	 * Find all products in catalog with matching description or id.
	 * An exact, case-insensitive search is done.
	 * @param match is part of the product description
	 * @return list of product descriptions.  List is empty if no matching product
	 */
	public List<ProductDescription> findProduct(String match);

	/**
	 * Set the product catalog
	 */
	public void setProductCatalog(ProductCatalog catalog);
}
