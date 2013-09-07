/*
 * 
 */

package firstgen.service;

import firstgen.domain.ProductDescription;
import firstgen.store.ProductCatalog;
import java.util.List;

/**
 * @author Dilbert
 */
public class DummyProductService implements ProductService {
	// use product catalog to find items
	private ProductCatalog productCatalog;

	public DummyProductService() { /* nothing to do */ }

	/**
	 * inject a product catalog
	 * @param productCatalog
	 */
	public void setProductCatalog(ProductCatalog productCatalog) {
		this.productCatalog = productCatalog;
	}
	

	public ProductDescription getProduct(String id) {
		if ( productCatalog == null ) return null;
		return productCatalog.getProduct(id);
	}

	public List<ProductDescription> findProduct(String match) {
		if ( productCatalog == null ) return new java.util.ArrayList<ProductDescription>();
		return productCatalog.findProduct( match );
	}

}
