/*
 * Demonstrate Product Service using console UI
 */

package firstgen.service;
import firstgen.domain.ProductDescription;
import firstgen.store.ProductCatalog;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.*;
/**
 * @author Dilbert
 */
public class ProductServiceDemo {


	public static void main(String [] args) {
		ProductService productService = new DummyProductService();
		ProductCatalog catalog = new ProductCatalog();
		productService.setProductCatalog( catalog );
		Scanner console = new Scanner(System.in);

		while(true) {
			out.print("product string to search for: ");
			String search = console.next().trim();
			//discard rest of line
			console.nextLine();
			List<ProductDescription> match = productService.findProduct(search);
			out.printf("Found %d products matching \"%s\":\n",
						match.size(), search);
			for (ProductDescription pd : match) {
				out.printf("%-4.4s  %-24.24s %6.2f\n",
						pd.getItemID(), pd.getDescription(), pd.getPrice());
			}
		}
	}
}
