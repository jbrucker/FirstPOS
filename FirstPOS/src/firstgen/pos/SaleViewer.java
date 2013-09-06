package firstgen.pos;
import firstgen.domain.*;

public interface SaleViewer {
	public static final int ERROR_MESSAGE = 2;
	public static final int INFO_MESSAGE = 1;
	
	/**
	 * Set the current Sale. May be null.
	 * @param sale the current sale, which maybe null if no sale in progress.
	 */
	public void setSale(Sale sale);
	
	public void setMessage(int type, String message);
}
