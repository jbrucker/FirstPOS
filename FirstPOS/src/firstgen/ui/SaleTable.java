package firstgen.ui;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

import firstgen.domain.*;

/**
 * A JTable that displays contents of a sale.
 * @author James Brucker
 */
public class SaleTable extends JTable {
	private static final long serialVersionUID = 1L;
	private SaleTableModel model;
	
	public SaleTable( Sale sale ) {
		model = new SaleTableModel( sale );
		super.setModel( model );
		setFillsViewportHeight(true);
		setShowGrid( true );
		setAutoResizeMode( JTable.AUTO_RESIZE_NEXT_COLUMN );

	}
	
	public void setSale( Sale sale ) {
		model.setSale(sale);
	}
}
