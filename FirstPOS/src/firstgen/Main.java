package firstgen;
import javax.swing.SwingUtilities;

import firstgen.pos.*;
import firstgen.store.ProductCatalog;
import firstgen.store.Store;
import firstgen.ui.ConsoleUI;
import firstgen.ui.POSUI;

import org.apache.log4j.*;
/**
 * @author James Brucker
 *
 */
public class Main {
	private static Logger logger;

	/** Initialize log4j.
	 *  @param debug if true, then initialize level to debug before starting.
	 *  This enables you to see the log4j initialization messages.
	 */
	public static void initLog4j( boolean debug ) {
		
		// for "debug" level, also show log4j initialization
			
		if ( debug ) System.setProperty("log4j.debug", "true" );
			
		// For a simple default configuration that logs to console, 
		// and does not require a log4j.properties file, use this: 
//		org.apache.log4j.BasicConfigurator.configure();

		// The first call to getRootLogger() initializes log4j
	}

	/* use the first command line argument as optional logging level */
	public static void main(String[] args) {
			
		// initLog4j( false );
		Store store = Store.getInstance();
		ProductCatalog catalog = store.getProductCatalog();
		Register register = new Register( );
		register.setStore(store);
		register.setProductCatalog(catalog);
		
		// run boring console UI
//		ConsoleUI ui = new ConsoleUI(register);
//		register.addSaleViewer(ui);
//		ui.run();
		
		// run Swing GUI
		POSUI gui = new POSUI(register);
		register.addSaleViewer(gui);
		SwingUtilities.invokeLater( gui );
		
	}
	
	private static Logger getLogger() {
		if ( logger == null ) logger = Logger.getLogger(Main.class);
		return logger;
	}
}
