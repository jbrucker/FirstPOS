package firstgen.ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import firstgen.domain.ProductDescription;
import firstgen.domain.Sale;
import firstgen.pos.POSInterface;
import firstgen.pos.SaleViewer;

/**
 * A Swing view of the POS.
 * It contains a reference to the controller (Register).
 * We use the SaleViewer interface so that the controller can
 * set the sale object in this View, making it possible for
 * this view to attach itself as observer of the Sale (model).
 * 
 * @author Dilbert
 * @version 2009.08.18
 */

public class POSUI extends JFrame implements Runnable, SaleViewer, Observer {
	private static final long serialVersionUID = 1L;
	/** Upper limit for numbers loaded into ComboBox for quantity.
	 * If this is too large, the ComboBox may be difficult to use.
	 * If too small, the cashier may need to type in values.
	 */
	private static final int MAX_QUANTITY = 20;
	final int TOTAL_FIELD_SIZE = 12;

	/** base font size for GUI */
	private int fontsize = 12;

	/** the controller for this UI */
	private POSInterface register;
	
	/** the current sale, for getting totals */
	private Sale sale = null;
	
	/** this frame, added for clarity */
	private JFrame frame;
	
	/** sub-containers for components of the UI */
	private JPanel bannerPanel;
	private JPanel controlPanel;
	private JPanel totalsPanel;
	
	/** components */
	private JComboBox quantityBox;
	private JTextArea messageArea;
	private JTextField inputArea;
	
	/** components for displaying subtotal, tax, total */
	private JTextField subtotalField;
	private JTextField taxField;
	private JTextField totalField;
	
	private JButton addButton;
	private JButton clearButton;
	private JButton endSaleButton;
	private SaleTable saleTable;
	
	/**
	 * Create a new POS User Interface
	 * @param register is POS controller to send actions to
	 */
	public POSUI( POSInterface register ) {
		frame = this;
		this.register = register;
		frame.setTitle("FirstGen POS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
	}
	
	public void run() {
		frame.setVisible(true);
	}

//TODO Apply the state pattern to actions from the UI 
	/**
	 * action to perform when user presses Add button
	 */
	private Action addAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent event) {
			int quantity = 1;
			String id = inputArea.getText().trim();
			if ( id.length() == 0 ) return;
			Object value = quantityBox.getSelectedItem();
			if ( value == null || value.toString().trim().length() == 0 ) return;
			try {
				quantity = Integer.parseInt(value.toString());
			} catch ( Exception ex ) {
				displayMessage(0, "Quantity must be integer.");
			}
			ProductDescription pd = register.addItem(id, quantity);
			// if OK then remove id and reset quantity
			if ( pd != null ) {
				String message = String.format("%s %s  %d @ %.2f", 
						pd.getItemID(), pd.getDescription(), quantity, pd.getPrice() );
				displayMessage( 0, message );
				inputArea.setText("");
				quantityBox.setSelectedIndex(0);
			}
		}
	};
	
	/**
	 * action to perform when user presses Clear button
	 */
	private Action clearAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent event) {
			inputArea.setText("");
			messageArea.setText("");
			quantityBox.setSelectedIndex(0);
		}
	};
	
	private Action endSaleAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent event) {
			register.endSale();
		}
	};

	/** initialize GUI components and layout */
	private void initComponents() {
		// create fonts
		Font baseFont = new Font(Font.DIALOG, Font.PLAIN, fontsize );
		Font messageFont = new Font(Font.DIALOG, Font.BOLD, (int)(1.5*fontsize) );
		Font inputFont = new Font(Font.DIALOG_INPUT, Font.BOLD, (int)(1.2*fontsize) );
		Font totalFont = new Font(Font.DIALOG, Font.BOLD, (int)(1.35*fontsize) );
		
		// action components
		addButton = new JButton("Add");
		addButton.setFont(baseFont);
		clearButton = new JButton("Clear");
		clearButton.setFont(baseFont);
		addButton.addActionListener(addAction);
		clearButton.addActionListener(clearAction);
		endSaleButton = new JButton("End Sale");
		endSaleButton.addActionListener(endSaleAction);
		inputArea = new JTextField(12);
		inputArea.setFont(inputFont);
		inputArea.addActionListener(addAction);
		// quantity of items
		quantityBox = new JComboBox();
		quantityBox.setFont(inputFont);
		for(int qnty=1; qnty<=MAX_QUANTITY; qnty++) quantityBox.addItem(new Integer(qnty));
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		
		// panel for a banner message
		bannerPanel = new JPanel();

		messageArea = new JTextArea(2,40); //(rows,columns)
		messageArea.setFont(messageFont);
		messageArea.setBorder( new EtchedBorder() );
		
		// sales summary information
		subtotalField = new JTextField( TOTAL_FIELD_SIZE );
		taxField = new JTextField( TOTAL_FIELD_SIZE );
		totalField = new JTextField( TOTAL_FIELD_SIZE );
		subtotalField.setFont( totalFont );
		taxField.setFont( totalFont );
		totalField.setFont( totalFont );
		totalField.setBackground( Color.white );
		subtotalField.setHorizontalAlignment(JTextField.RIGHT);
		taxField.setHorizontalAlignment(JTextField.RIGHT);
		totalField.setHorizontalAlignment(JTextField.RIGHT);
		subtotalField.setEditable(false);
		taxField.setEditable(false);
		totalField.setEditable(false);
		totalsPanel = new JPanel();
		totalsPanel.setLayout( gridbag );
		// layout the message area so it spans all three totals fields
		gc.insets = new Insets(2,2,2,2); // padding
		gc.fill = GridBagConstraints.BOTH;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridheight = 3; // span the subtotal, tax, total fields
		gc.weightx = 0.8;
		addToPanel(totalsPanel, messageArea, gc);
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.EAST;
		gc.weightx = 0.1;
		gc.gridx = 1;
		gc.gridy = 0;
		addToPanel(totalsPanel, new JLabel("Subtotal:"), gc);
		gc.gridy++;
		addToPanel(totalsPanel, new JLabel("Tax:"), gc);
		gc.gridy++;
		addToPanel(totalsPanel, new JLabel("Total:"), gc);
		gc.gridx++;
		gc.gridy = 0;
		gc.weightx = 0.4;
		gc.fill = GridBagConstraints.WEST;
		addToPanel(totalsPanel, subtotalField, gc);
		gc.gridy++;
		addToPanel(totalsPanel, taxField, gc);
		gc.gridy++;
		addToPanel(totalsPanel, totalField, gc);
		
		// panel for input area, buttons, etc.
		controlPanel = new JPanel();
		// we could use a FlowLayout, but for future enhancement use gridbag
		gridbag = new GridBagLayout();
		gc = new GridBagConstraints();
		controlPanel.setLayout( gridbag );
		
		gc.insets = new Insets(4,4,4,4); // padding
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		// top row message area
		gc.gridy = 0;
		gc.gridx = 0;
		// add components to control panel
		gc.gridx++;
		JLabel label1 = new JLabel("Quantity: ");
		label1.setFont(baseFont);
		addToPanel(controlPanel, label1, gc );
		gc.gridx++;
		addToPanel(controlPanel, quantityBox, gc );
		gc.gridx++;
		JLabel label2 = new JLabel("Item ID: ");
		label2.setFont(baseFont);
		addToPanel(controlPanel, label2, gc );
		gc.gridx++;
		addToPanel(controlPanel, inputArea, gc );
		gc.gridx++;
		addToPanel(controlPanel, addButton, gc );
		gc.gridx++;
		addToPanel(controlPanel, clearButton, gc );
		gc.gridx++;
		addToPanel(controlPanel, endSaleButton, gc );
		
		saleTable = new SaleTable( null );
		saleTable.setFont( baseFont );
		JScrollPane scrollTable = new JScrollPane(saleTable);
		scrollTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// add panels to frame
		frame.add( totalsPanel, BorderLayout.NORTH );
		frame.add( controlPanel, BorderLayout.CENTER );
		frame.add( scrollTable, BorderLayout.SOUTH );
		// resize everything
		frame.pack();
	}
	
	
	
	// add component to container using gridbag constraints
	private void addToPanel( Container container, Component component, GridBagConstraints c) {
		LayoutManager layout = container.getLayout();
		if ( layout instanceof GridBagLayout ) ((GridBagLayout)layout).setConstraints(component, c);
		container.add( component );
	}
	
	/**
	 * display an error message
	 */
	private void displayMessage(int type, String text) {
		if ( type == SaleViewer.ERROR_MESSAGE ) messageArea.setForeground(Color.RED);
		else messageArea.setForeground( Color.black );
		messageArea.setText( text );
	}

	public void setSale(Sale sale) {
		if ( saleTable != null ) saleTable.setSale(sale);
		// remove as observer from old sale
		if ( this.sale != null ) this.sale.deleteObserver(this);
		this.sale = sale;
		if ( sale != null ) sale.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		final String MONEYFORMAT = "%.2f";
		subtotalField.setText( String.format(MONEYFORMAT, sale.getSubtotal()) );
		taxField.setText( String.format(MONEYFORMAT, sale.getTax()) );
		totalField.setText( String.format(MONEYFORMAT, sale.getTotal()) );
	}

	public void setMessage(int type, String message) {
		displayMessage( type, message );
	}
	
}
