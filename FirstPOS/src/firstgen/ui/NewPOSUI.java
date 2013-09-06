/**
 * 
 * @author Supachart
 */

package firstgen.ui;


import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/*import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;*/

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import firstgen.domain.ProductDescription;
import firstgen.domain.Sale;
import firstgen.pos.POSInterface;
import firstgen.pos.SaleViewer;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class NewPOSUI extends javax.swing.JFrame implements Runnable, SaleViewer{
	
	private static final long serialVersionUID = 1L;
	/** Upper limit for numbers loaded into ComboBox for quantity.
	 * If this is too large, the ComboBox may be difficult to use.
	 * If too small, the cashier may need to type in values.
	 */
	private static final int MAX_QUANTITY = 25;

	/** base font size for GUI */
	private int fontsize = 12;

	/** the controller for this UI */
	private POSInterface register;
	
	/** this frame, added for clarity */
	private JFrame frame;
	
	private JScrollPane saletablescrollPanel;
	private AbstractAction addAction;
	private JButton clearButton;
	private JButton addButton;
	private JComboBox quantityBox;
	private JTextField inputText;
	private JLabel quantityLabel;
	private JLabel itemIDLabel;

	private SaleTable saleTable;
	private Sale sale = null;
	
	private JLabel totalLabel;
	private JPanel controlPanel;
	private JTextArea messageArea;
	private JPanel addPanel;
	private JTextField totalTextField;
	private AbstractAction clearAction;
	private JTextField taxTextField;
	private JTextField subtotalTextField;
	private JLabel taxLabel;
	private JLabel subtotalLabel;
	private JPanel totalPanel;
	private JPanel salePanel;
	private AbstractAction removeAbstractAction;
	private JButton removeButton;
	private JLabel removeLabel;
	private JTextField removeIDField;
	private AbstractAction endAction;
	private JButton endButton;

	/**
	 * Create a new POS User Interface
	 * @param register is POS controller to send actions to
	 */
	public NewPOSUI( POSInterface register ) {
		frame = this;
		this.register = register;
		frame.setTitle("FirstGen POS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGUI();
	}
	
	/*protected void setSale(Sale sale)
	{
		this.sale = sale;
	}*/
	
	public void run() {
		frame.setVisible(true);
	}
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	/*public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UI inst = new UI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}*/
	
	/*public UI() {
		super();
		initGUI();
	}*/
	
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.0, 0.1};
			thisLayout.rowHeights = new int[] {123, 7};
			thisLayout.columnWeights = new double[] {0.1};
			thisLayout.columnWidths = new int[] {7};
			getContentPane().setLayout(thisLayout);
			/**Panel that show line item in table*/
			{
				saleTable = new SaleTable( null );				
				saleTable.setFont( new Font( Font.DIALOG, Font.PLAIN, 12 ) );
				saletablescrollPanel = new JScrollPane(saleTable);
				//JScrollPane scrollTable = new JScrollPane(saleTable);
				saletablescrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				getContentPane().add(saletablescrollPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				
			}
			/**Panel that cover all POS user interface*/
			{
				salePanel = new JPanel();
				GridBagLayout salePanelLayout = new GridBagLayout();
				getContentPane().add(salePanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				salePanel.setFocusCycleRoot(true);
				salePanel.setPreferredSize(new java.awt.Dimension(320, 160));
				salePanelLayout.rowWeights = new double[] {0.1};
				salePanelLayout.rowHeights = new int[] {7};
				salePanelLayout.columnWeights = new double[] {0.0, 0.1};
				salePanelLayout.columnWidths = new int[] {181, 7};
				salePanel.setLayout(salePanelLayout);
				/**Panel on the top of interface*/
				{
					/**Panel that show SubTotal, tax and Total of current sale*/
					totalPanel = new JPanel();
					GridBagLayout totalPanelLayout = new GridBagLayout();
					salePanel.add(totalPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					totalPanelLayout.rowWeights = new double[] {0.1, 0.1, 0.1};
					totalPanelLayout.rowHeights = new int[] {7, 7, 7};
					totalPanelLayout.columnWeights = new double[] {0.0, 0.1};
					totalPanelLayout.columnWidths = new int[] {80, 7};
					totalPanel.setLayout(totalPanelLayout);
					{
						/**Panel that contain "subtotal" label*/
						subtotalLabel = new JLabel();
						totalPanel.add(subtotalLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						subtotalLabel.setText("Subtotal");
						subtotalLabel.setFont(new java.awt.Font("Times New Roman",0,20));
					}
					{
						/**Panel that contain "tax" label*/
						taxLabel = new JLabel();
						totalPanel.add(taxLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						taxLabel.setText("Tax");
						taxLabel.setFont(new java.awt.Font("Times New Roman",0,20));
					}
					{
						/**Panel that contain "total" label*/
						totalLabel = new JLabel();
						totalPanel.add(totalLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						totalLabel.setText("Total");
						totalLabel.setFont(new java.awt.Font("Times New Roman",0,20));
					}
					{
						/**Panel that contain value of subtotal in current sale*/
						subtotalTextField = new JTextField();
						totalPanel.add(subtotalTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						subtotalTextField.setText("0.00");
						subtotalTextField.setFont(new java.awt.Font("Tahoma",0,16));
					}
					{
						/**Panel that contain value of tax in current sale*/
						taxTextField = new JTextField();
						totalPanel.add(taxTextField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						taxTextField.setText("0.00");
						taxTextField.setFont(new java.awt.Font("Tahoma",0,16));
						//sale.setTaxAdapter(new TaxMasterAdapter(sale));
						///String txt = String.valueOf(sale.getTax());
						//taxTextField.setText(sale.getTax());
						//taxTextField.setText(txt);
					}
					{
						/**Panel that contain value of total in current sale*/
						totalTextField = new JTextField();
						totalPanel.add(totalTextField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						totalTextField.setText("0.00");
						totalTextField.setFont(new java.awt.Font("Tahoma",0,16));
						//double total = register.getTotal();
						//String txt = String.valueOf(total);
						//totalTextField.setText(txt);
					}
				}
				/**Panel that contain function about add , clear and information
				 * in current sale.
				 */
				{
					addPanel = new JPanel();
					GridBagLayout addPanelLayout = new GridBagLayout();
					salePanel.add(addPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					addPanelLayout.rowWeights = new double[] {0.0, 0.1};
					addPanelLayout.rowHeights = new int[] {101, 7};
					addPanelLayout.columnWeights = new double[] {0.1};
					addPanelLayout.columnWidths = new int[] {7};
					addPanel.setLayout(addPanelLayout);
					{
						/**Show information of current item.*/
						messageArea = new JTextArea();
						addPanel.add(messageArea, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					}
					{
						/**Panel that contain input field, quantity, add and clear 
						 * function.
						 */
						controlPanel = new JPanel();
						GridBagLayout controlPanelLayout = new GridBagLayout();
						addPanel.add(controlPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						controlPanel.setLayout(controlPanelLayout);
						{
							itemIDLabel = new JLabel();
							controlPanel.add(itemIDLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
							itemIDLabel.setText("ItemID");
							itemIDLabel.setFont(new java.awt.Font("Times New Roman",0,14));
						}
						{
							quantityLabel = new JLabel();
							controlPanel.add(quantityLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
							quantityLabel.setText("Quantity");
							quantityLabel.setFont(new java.awt.Font("Times New Roman",0,14));
						}
						{
							inputText = new JTextField();
							inputText.addActionListener(new ActionListener(){
								
								public void actionPerformed(ActionEvent e) {
									//getAddAction();
									
									//JOptionPane.showMessageDialog(null, "fff");
								}
							});
							controlPanel.add(inputText, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
							inputText.setFont(new java.awt.Font("Times New Roman",0,18));
						}
						{
							String[] quanValue = new String[MAX_QUANTITY];
							for(int qnty = 1 ; qnty < quanValue.length ; qnty++) quanValue[qnty] = String.valueOf(qnty);//(new Integer(qnty));
							ComboBoxModel quantityBoxModel = 
								new DefaultComboBoxModel( quanValue);
										//String[] quanValue = new String()
										//new String[]/*{ "Item One", "Item Two" }*/);
							quantityBox = new JComboBox();
							controlPanel.add(quantityBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							quantityBox.setModel(quantityBoxModel);
							quantityBox.setFont(new java.awt.Font("Tahoma",0,16));
							//ComboBoxModel quantityBoxMo = new ComboBoxModel();
							//for(int qnty=1; qnty<=MAX_QUANTITY; qnty++) quantityBox.addItem(new Integer(qnty));
						}
						{
							addButton = new JButton();
							controlPanel.add(addButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							addButton.setText("Add");
							addButton.setAction(getAddAction());
							addButton.setFont(new java.awt.Font("Times New Roman",0,16));
						}
						{
							clearButton = new JButton();
							controlPanel.add(clearButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							controlPanel.add(getEndButton(), new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							controlPanel.add(getRemoveIDField(), new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
							controlPanel.add(getRemoveLabel(), new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
							controlPanel.add(getRemoveButton(), new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							clearButton.setText("Clear");
							clearButton.setAction(getClearAction());
							clearButton.setFont(new java.awt.Font("Times New Roman",0,16));
						}
						controlPanelLayout.rowWeights = new double[] {0.1, 0.1};
						controlPanelLayout.rowHeights = new int[] {7, 7};
						controlPanelLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
						controlPanelLayout.columnWidths = new int[] {57, 106, 51, 64, 73, 70, 20};
					}
				}
			}
			pack();
			setSize(640, 480);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}		

	/**
	 * display an error message
	 */
	private void displayMessage(String text) {
		messageArea.setText( text );
	}

	/**
	 * Set the sale
	 */
	public void setSale(Sale sale) {
		if ( saleTable != null ) saleTable.setSale(sale);
		this.sale = sale;
	}
	
	/**Thing to do when press add button*/
	private AbstractAction getAddAction() {
		if(addAction == null) 
		{
			addAction = new AbstractAction("Add", null) 
			{
				public void actionPerformed(ActionEvent evt) 
				{
					final String MONEYFORMAT = "%.2f";
					int quantity = 1;
					String id = inputText.getText().trim();
					
					if ( id.length() == 0 ) return;
					
					Object value = quantityBox.getSelectedItem();
					
					if ( value == null || value.toString().trim().length() == 0 ) return;
					
					try {
						quantity = Integer.parseInt(value.toString());
					} catch ( Exception ex ) {
						displayMessage("Quantity must be integer.");
					}
					
					ProductDescription pd = register.addItem(id, quantity);
					// if OK then remove id and reset quantity
					// also get value of subtotal , tax and total
					if ( pd != null ) {
						String message = String.format("%s %s  %d @ %.2f", 
								pd.getItemID(), pd.getDescription(), quantity, pd.getPrice() );
						displayMessage( message );
						inputText.setText("");
						quantityBox.setSelectedIndex(1);
						subtotalTextField.setText( String.format(MONEYFORMAT, sale.getSubtotal()) );
						//sale.setTaxAdapter(new TaxMasterAdapter(sale));
						taxTextField.setText(String.format(MONEYFORMAT, sale.getTax()));
						totalTextField.setText(String.format(MONEYFORMAT, sale.getTotal()));
					}
				}
			};
		}
		return addAction;
	}
	
	/**
	 * Reset input and clear information in product 
	 * information box
	 */
	private AbstractAction getClearAction() {
		if(clearAction == null) {
			clearAction = new AbstractAction("Clear", null) {
				public void actionPerformed(ActionEvent evt) {
					inputText.setText("");
					messageArea.setText("");
					quantityBox.setSelectedIndex(1);
					//subtotalTextField.setText("0.00");
					//taxTextField.setText("0.00");
					//totalTextField.setText("0.00");
				}
			};
		}
		return clearAction;
	}

	public void setMessage(int type, String message) {
		// TODO Auto-generated method stub
		
	}
	
	private JButton getEndButton() {
		if(endButton == null) {
			endButton = new JButton();
			endButton.setText("End");
			endButton.setFont(new java.awt.Font("Tahoma",0,16));
			endButton.setAction(getEndAction());
		}
		return endButton;
	}
	
	private AbstractAction getEndAction() {
		if(endAction == null) {
			endAction = new AbstractAction("End", null) {
				public void actionPerformed(ActionEvent evt) {
					register.endSale();
					taxTextField.setText("");
					totalTextField.setText("");
					subtotalTextField.setText("");
					inputText.setText("");
					messageArea.setText("");
					quantityBox.setSelectedIndex(1);
				}
			};
		}
		return endAction;
	}
	
	private JTextField getRemoveIDField() {
		if(removeIDField == null) {
			removeIDField = new JTextField();
			removeIDField.setFont(new java.awt.Font("Times New Roman",0,18));
		}
		return removeIDField;
	}
	
	private JLabel getRemoveLabel() {
		if(removeLabel == null) {
			removeLabel = new JLabel();
			removeLabel.setText("RemoveID");
			removeLabel.setFont(new java.awt.Font("Times New Roman",0,14));
		}
		return removeLabel;
	}
	
	private JButton getRemoveButton() {
		if(removeButton == null) {
			removeButton = new JButton();
			removeButton.setText("Remove");
			removeButton.setAction(getRemoveAbstractAction());
			removeButton.setFont(new java.awt.Font("Times New Roman",0,16));
		}
		return removeButton;
	}
	
	private AbstractAction getRemoveAbstractAction() {
		if(removeAbstractAction == null) {
			removeAbstractAction = new AbstractAction("Remove", null) {
				public void actionPerformed(ActionEvent evt) {
					String id = removeIDField.getText().trim();
					int quantity = 1;
					final String MONEYFORMAT = "%.2f";
					
					if ( id.length() == 0 ) return;
					
					Object value = quantityBox.getSelectedItem();
					
					if ( value == null || value.toString().trim().length() == 0 ) return;
					
					try {
						quantity = Integer.parseInt(value.toString());
					} catch ( Exception ex ) {
						displayMessage("Quantity must be integer.");
					}
					
					register.removeItem(id, quantity);
					removeIDField.setText("");
					quantityBox.setSelectedIndex(1);
					subtotalTextField.setText( String.format(MONEYFORMAT, sale.getSubtotal()) );
					taxTextField.setText(String.format(MONEYFORMAT, sale.getTax()));
					totalTextField.setText(String.format(MONEYFORMAT, sale.getTotal()));
				}
			};
		}
		return removeAbstractAction;
	}
}
