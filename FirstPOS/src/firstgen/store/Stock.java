
package firstgen.store;

/**
 * A static set of products for initializing the product catalog
 * during first iteration and for testing.  This is not for production.
 * @author Jim
 */
public enum Stock {
	BROWNRICE("011", "Jasmine Brown Rice, 5kg", 105),
	SOYSAUCE("012", "3-No Light Soy Sauce", 27.5),
	COKE("013", "Coca-Cola, 1.25L", 23.5),
	PEPSI("014", "Pepsi Cola, 1.25Liter", 23),
	TOFU("015", "Medium Tofu, 250gram", 15),
	APPLE("016", "Red Medium Apple, each", 5),
	PAPAYA("017", "Whole Papaya, 1kg", 12),
	BANANACAKE("018", "Banana Cake, 250gram", 30),
	FRUITCAKE("019", "Fruit Cake, 250gram", 35),
	PEANUTS("020", "Peanuts, 100gram", 10);

	private Stock(String id, String desc, double price) {
		this.id = id;
		this.description = desc;
		this.price = price;
	}
	public final String id;
	public final String description;
	public final double price;
}
