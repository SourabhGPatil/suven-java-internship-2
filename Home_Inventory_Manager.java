/**
‣ This is the main class that represents the Home Inventory Manager application.
‣ It manages a list of inventory items and provides a graphical user interface
‣ for adding, editing, deleting, and printing items from the list.
**/

package com.suven.consultancy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.*;
import com.toedter.calendar.JDateChooser;

/**
‣ Represents the main class that creates the graphical user interface
‣ and manages the inventory items.
**/
public class Home_Inventory_Manager extends JFrame {
    JToolBar inventoryToolBar = new JToolBar();
    JButton newButton = new JButton(new ImageIcon("new.gif"));
    JButton deleteButton = new JButton(new ImageIcon("delete.gif"));
    JButton saveButton = new JButton(new ImageIcon("save.gif"));
    JButton previousButton = new JButton(new ImageIcon("previous.gif"));
    JButton nextButton = new JButton(new ImageIcon("next.gif"));
    JButton printButton = new JButton(new ImageIcon("print.gif"));
    JButton exitButton = new JButton();
    
    // Frame
    JLabel itemLabel = new JLabel();
    JTextField itemTextField = new JTextField();
    JLabel locationLabel = new JLabel();
    JComboBox locationComboBox = new JComboBox();
    JCheckBox markedCheckBox = new JCheckBox();
    JLabel serialLabel = new JLabel();
    JTextField serialTextField = new JTextField();
    JLabel priceLabel = new JLabel();
    JTextField priceTextField = new JTextField();
    JLabel dateLabel = new JLabel();
    JDateChooser dateDateChooser = new JDateChooser();
    JLabel storeLabel = new JLabel();
    JTextField storeTextField = new JTextField();
    JLabel noteLabel = new JLabel();
    JTextField noteTextField = new JTextField();
    JLabel photoLabel = new JLabel();
    static JTextArea photoTextArea = new JTextArea();
    JButton photoButton = new JButton();
    JPanel searchPanel = new JPanel();
    JButton[] searchButton = new JButton[26];
    PhotoPanel photoPanel = new PhotoPanel();
    static final int maximumEntries = 300;
    static int numberEntries;
    static InventoryItem[] myInventory = new InventoryItem[maximumEntries];
    int currentEntry;
    static final int entriesPerPage = 2;
    static int lastPage;
    
    

    /**
    ‣ The main method that starts the application by creating a new instance of the Home_Inventory_Manager class.
    **/
    public static void main(String args[]) {
        // create frame
        new Home_Inventory_Manager().show();
    }

    public Home_Inventory_Manager() {
        // frame constructor
        setTitle("Home Inventory Manager");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        });
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints;

        // Inventory ToolBar
        inventoryToolBar.setFloatable(false);
        inventoryToolBar.setBackground(Color.BLUE);
        inventoryToolBar.setOrientation(SwingConstants.VERTICAL);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridheight = 8;
        gridConstraints.fill = GridBagConstraints.VERTICAL;
        getContentPane().add(inventoryToolBar, gridConstraints);
        inventoryToolBar.addSeparator();

        // New Button
        Dimension bSize = new Dimension(70, 50);
        newButton.setText("New");
        sizeButton(newButton, bSize);
        newButton.setToolTipText("Add New Item");
        newButton.setHorizontalTextPosition(SwingConstants.CENTER);
        newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        newButton.setFocusable(false);
        inventoryToolBar.add(newButton);
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newButtonActionPerformed(e);
                }
            }
        );

        // Delete Button
        deleteButton.setText("Delete");
        sizeButton(deleteButton, bSize);
        deleteButton.setToolTipText("Delete Current Item");
        deleteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        deleteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        deleteButton.setFocusable(false);
        inventoryToolBar.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteButtonActionPerformed(e);
                }
            }
        );

        // Save Button
        saveButton.setText("Save");
        sizeButton(saveButton, bSize);
        saveButton.setToolTipText("Save Current Item");
        saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        saveButton.setFocusable(false);
        inventoryToolBar.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveButtonActionPerformed(e);
                }
            }
        );
        
        inventoryToolBar.addSeparator();

        // Previous Button
        previousButton.setText("Previous");
        sizeButton(previousButton, bSize);
        previousButton.setToolTipText("Display Previous Item");
        previousButton.setHorizontalTextPosition(SwingConstants.CENTER);
        previousButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        previousButton.setFocusable(false);
        inventoryToolBar.add(previousButton);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                previousButtonActionPerformed(e);
                }
            }
        );

        // Next Button
        nextButton.setText("Next");
        sizeButton(nextButton, bSize);
        nextButton.setToolTipText("Display Next Item");
        nextButton.setHorizontalTextPosition(SwingConstants.CENTER);
        nextButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        nextButton.setFocusable(false);
        inventoryToolBar.add(nextButton);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextButtonActionPerformed(e);
                }
            }
        );
        
        inventoryToolBar.addSeparator();
        
        // Print Button
        printButton.setText("Print");
        sizeButton(printButton, bSize);
        printButton.setToolTipText("Print Inventory List");
        printButton.setHorizontalTextPosition(SwingConstants.CENTER);
        printButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        printButton.setFocusable(false);
        inventoryToolBar.add(printButton);
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printButtonActionPerformed(e);
                }
            }
        );

        // Exit Button
        exitButton.setText("Exit");
        sizeButton(exitButton, bSize);
        exitButton.setToolTipText("Exit Program");
        exitButton.setFocusable(false);
        inventoryToolBar.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitButtonActionPerformed(e);
                }
            }
        );


        // Set text and properties for itemLabel
        itemLabel.setText("Inventory Item");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(itemLabel, gridConstraints);

        // Set properties for itemTextField
        itemTextField.setPreferredSize(new Dimension(400, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 5;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(itemTextField, gridConstraints);
        itemTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                itemTextFieldActionPerformed(e);
            }
        });

        // Set text and properties for locationLabel
        locationLabel.setText("Location");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(locationLabel, gridConstraints);

        // Set properties for locationComboBox
        locationComboBox.setPreferredSize(new Dimension(270, 25));
        locationComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        locationComboBox.setEditable(true);
        locationComboBox.setBackground(Color.WHITE);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(locationComboBox, gridConstraints);
        locationComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                locationComboBoxActionPerformed(e);
            }
        });

        // Set text and properties for markedCheckBox
        markedCheckBox.setText("Marked?");
        markedCheckBox.setFocusable(false);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 5;
        gridConstraints.gridy = 1;
        gridConstraints.insets = new Insets(10, 10, 0, 0);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(markedCheckBox, gridConstraints);

        // Set text and properties for serialLabel
        serialLabel.setText("Serial Number");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 2;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(serialLabel, gridConstraints);
        serialTextField.setPreferredSize(new Dimension(270, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 2;
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(serialTextField, gridConstraints);
        serialTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                serialTextFieldActionPerformed(e);
            }
        });

        // Set text and properties for priceLabel
        priceLabel.setText("Purchase Price");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 3;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(priceLabel, gridConstraints);

        // Set properties for priceTextField
        priceTextField.setPreferredSize(new Dimension(160, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(priceTextField, gridConstraints);
        priceTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                priceTextFieldActionPerformed(e);
            }
        });

        // Set text and properties for dateLabel
        dateLabel.setText("Date Purchased");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 4;
        gridConstraints.gridy = 3;
        gridConstraints.insets = new Insets(10, 10, 0, 0);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(dateLabel, gridConstraints);

        // Set properties for dateDateChooser
        dateDateChooser.setPreferredSize(new Dimension(120, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 5;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(dateDateChooser, gridConstraints);
        dateDateChooser.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                dateDateChooserPropertyChange(e);
            }
        });

        // Set text and properties for storeLabel
        storeLabel.setText("Store/Website");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 4;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(storeLabel, gridConstraints);

        // Set properties for storeTextField
        storeTextField.setPreferredSize(new Dimension(400, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 4;
        gridConstraints.gridwidth = 5;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(storeTextField, gridConstraints);
        storeTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeTextFieldActionPerformed(e);
            }
        });

        // Set text and properties for noteLabel
        noteLabel.setText("Note");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 5;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(noteLabel, gridConstraints);

        // Set properties for noteTextField
        noteTextField.setPreferredSize(new Dimension(400, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 5;
        gridConstraints.gridwidth = 5;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(noteTextField, gridConstraints);
        noteTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                noteTextFieldActionPerformed(e);
            }
        });

        
        // Set text and properties for photoLabel
        photoLabel.setText("Photo");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 6;
        gridConstraints.insets = new Insets(10, 10, 0, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(photoLabel, gridConstraints);

        // Set properties for photoTextArea
        photoTextArea.setPreferredSize(new Dimension(350, 35));
        photoTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        photoTextArea.setEditable(false);
        photoTextArea.setLineWrap(true);
        photoTextArea.setWrapStyleWord(true);
        photoTextArea.setBackground(new Color(255, 255, 192));
        photoTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        photoTextArea.setFocusable(false);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 6;
        gridConstraints.gridwidth = 4;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(photoTextArea, gridConstraints);

        // Set text and properties for photoButton
        photoButton.setText("...");
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 6;
        gridConstraints.gridy = 6;
        gridConstraints.insets = new Insets(10, 0, 0, 10);
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(photoButton, gridConstraints);
        photoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                photoButtonActionPerformed(e);
            }
        });

        // Set properties for searchPanel
        searchPanel.setPreferredSize(new Dimension(240, 160));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Item Search"));
        searchPanel.setLayout(new GridBagLayout());
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 7;
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(10, 0, 10, 0);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        getContentPane().add(searchPanel, gridConstraints);

        // create and position 26 buttons in searchPanel
        int x = 0, y = 0;
        
        // create and position 26 buttons
        for (int i = 0; i < 26; i++) 
            {
            // create new button
            searchButton[i] = new JButton();
                
            // set text property
            searchButton[i].setText(String.valueOf((char) (65 + i)));
            searchButton[i].setFont(new Font("Arial", Font.BOLD, 12));
            searchButton[i].setMargin(new Insets(-10, -10, -10, -10));
            sizeButton(searchButton[i], new Dimension(37, 27));
            searchButton[i].setBackground(Color.YELLOW);
            searchButton[i].setFocusable(false);
            gridConstraints = new GridBagConstraints();
            gridConstraints.gridx = x;
            gridConstraints.gridy = y;
            searchPanel.add(searchButton[i], gridConstraints);
                
            // add method
            searchButton[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    searchButtonActionPerformed(e);
                    }
                });
                
            x++;
            // six buttons per row
            if (x % 6 == 0)
            {
                x = 0;
                y++;
            }
        }

        // Set properties for photoPanel
        photoPanel.setPreferredSize(new Dimension(240, 160));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 4;
        gridConstraints.gridy = 7;
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(10, 0, 10, 10);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        getContentPane().add(photoPanel, gridConstraints);

        // Pack the components to resize the frame
        pack();

        // Get the screen size and set the frame bounds to center it on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) (0.5 * (screenSize.width - getWidth())), (int) (0.5 * (screenSize.height -
                getHeight())), getWidth(), getHeight());
        
        int n;
        
        // open file for entries
        try 
        {
            BufferedReader inputFile = new BufferedReader(new FileReader("inventory.txt"));

            // Read the number of entries from the file
            numberEntries = Integer.valueOf(inputFile.readLine()).intValue();
            if (numberEntries != 0) 
            {
                // Read each entry from the file and store it in the myInventory array
                for (int i = 0; i < numberEntries; i++) 
                {
                    myInventory[i] = new InventoryItem();
                    myInventory[i].description = inputFile.readLine();
                    myInventory[i].location = inputFile.readLine();
                    myInventory[i].serialNumber = inputFile.readLine();
                    myInventory[i].marked = Boolean.valueOf(inputFile.readLine()).booleanValue();
                    myInventory[i].purchasePrice = inputFile.readLine();
                    myInventory[i].purchaseDate = inputFile.readLine();
                    myInventory[i].purchaseLocation = inputFile.readLine();
                    myInventory[i].note = inputFile.readLine();
                    myInventory[i].photoFile = inputFile.readLine();
                }
            }
            
            // Read the combo box elements from the file
            n = Integer.valueOf(inputFile.readLine()).intValue();
            if (n != 0) 
            {
                // Add each combo box element to the locationComboBox
                for (int i = 0; i < n; i++) 
                {
                    locationComboBox.addItem(inputFile.readLine());
                }
            }
            
            inputFile.close();

            // Set the currentEntry to the first entry and display it
            currentEntry = 1;
            showEntry(currentEntry);
        } 
        catch (Exception ex) 
        {
            // If an exception occurs, set the number of entries and currentEntry to 0
            numberEntries = 0;
            currentEntry = 0;
        }

        // Disable buttons if there are no entries
        if (numberEntries == 0) {
            newButton.setEnabled(false);
            deleteButton.setEnabled(false);
            nextButton.setEnabled(false);
            previousButton.setEnabled(false);
            printButton.setEnabled(false);
        }
    }

    // Method called when the form is being exited
    private void exitForm(WindowEvent evt) {
        // Prompt the user to confirm exiting the program and check their response
        if (JOptionPane.showConfirmDialog(null, "Any unsaved changes will be lost.\nAre you sure you want to exit?",
                "Exit Program", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
            return;
        
        // write entries back to file
        try {
            PrintWriter outputFile = new PrintWriter(new BufferedWriter(new FileWriter("inventory.txt")));

            // Write the number of entries to the file
            outputFile.println(numberEntries);
            if (numberEntries != 0) 
            {
                // Write each entry from the myInventory array to the file
                for (int i = 0; i < numberEntries; i++) 
                {
                    outputFile.println(myInventory[i].description);
                    outputFile.println(myInventory[i].location);
                    outputFile.println(myInventory[i].serialNumber);
                    outputFile.println(myInventory[i].marked);
                    outputFile.println(myInventory[i].purchasePrice);
                    outputFile.println(myInventory[i].purchaseDate);
                    outputFile.println(myInventory[i].purchaseLocation);
                    outputFile.println(myInventory[i].note);
                    outputFile.println(myInventory[i].photoFile);
                }
            }
            
            // write combo box entries
            outputFile.println(locationComboBox.getItemCount());
            if (locationComboBox.getItemCount() != 0) 
            {
                // Write each combo box entry to the file
                for (int i = 0; i < locationComboBox.getItemCount(); i++)
                    outputFile.println(locationComboBox.getItemAt(i));
            }
            outputFile.close();
        } 
        
        catch (Exception ex) 
        {
            // Error occurred while writing to the file
        }
        // Exit the program
        System.exit(0);
    }

    // Method called when the "New" button is clicked
    private void newButtonActionPerformed(ActionEvent e) 
    {
        // Check if there are any unsaved changes and prompt the user to save them
        checkSave();

        // Reset the form values to create a new entry
        blankValues();
    }

    // Method called when the "Delete" button is clicked
    private void deleteButtonActionPerformed(ActionEvent e)
    {
        // Prompt the user to confirm the deletion of the item and check their response
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?",
                "Delete Inventory Item", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
            return;

        // Delete the current entry
        deleteEntry(currentEntry);
        
        if (numberEntries == 0) 
        {
            // If there are no more entries, reset the form
            currentEntry = 0;
            blankValues();
        } 
        
        else 
        {
            // Update the current entry and show the previous entry
            currentEntry--;
            
            if (currentEntry == 0)
                currentEntry = 1;
            
            showEntry(currentEntry);
        }
    }

    // Method called when the "Save" button is clicked
    private void saveButtonActionPerformed(ActionEvent e) 
    {
        // Check if the item description is provided
        itemTextField.setText(itemTextField.getText().trim());
        if (itemTextField.getText().equals("")) 
        {
            JOptionPane.showConfirmDialog(null, "Must have item description.", "Error",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            itemTextField.requestFocus();
            return;
        }
        
        if (newButton.isEnabled()) 
        {
            // Delete the existing entry if it is being edited
            deleteEntry(currentEntry);
        }
        
       // Capitalize the first letter of the item description
        String s = itemTextField.getText();
        itemTextField.setText(s.substring(0, 1).toUpperCase() + s.substring(1));

        // Increase the number of entries
        numberEntries++;
        
       // Determine the new current entry location based on the description
        currentEntry = 1;
        if (numberEntries != 1) 
        {
            do 
            {
                if (itemTextField.getText().compareTo(myInventory[currentEntry - 1].description) < 0)
                    break;
                currentEntry++;
            } while (currentEntry < numberEntries);
        }
        
        // Move all entries below the new value down one position unless at the end
        if (currentEntry != numberEntries) 
        {
            for (int i = numberEntries; i >= currentEntry + 1; i--) 
            {
                myInventory[i - 1] = myInventory[i - 2];
                myInventory[i - 2] = new InventoryItem();
            }
        }
        
        // Save the data from the form to the current inventory item
        myInventory[currentEntry - 1] = new InventoryItem();
        myInventory[currentEntry - 1].description = itemTextField.getText();
        myInventory[currentEntry - 1].location = locationComboBox.getSelectedItem().toString();
        myInventory[currentEntry - 1].marked = markedCheckBox.isSelected();
        myInventory[currentEntry - 1].serialNumber = serialTextField.getText();
        myInventory[currentEntry - 1].purchasePrice = priceTextField.getText();
        myInventory[currentEntry - 1].purchaseDate = dateToString(dateDateChooser.getDate());
        myInventory[currentEntry - 1].purchaseLocation = storeTextField.getText();
        myInventory[currentEntry - 1].photoFile = photoTextArea.getText();
        myInventory[currentEntry - 1].note = noteTextField.getText();

        // Show the current entry on the form
        showEntry(currentEntry);
        
        // Enable or disable buttons based on the number of entries
        if (numberEntries < maximumEntries)
            newButton.setEnabled(true);
        else
            newButton.setEnabled(false);
        
        deleteButton.setEnabled(true);
        printButton.setEnabled(true);
    }

    // Method called when the "Previous" button is clicked
    private void previousButtonActionPerformed(ActionEvent e) {
        // Check if the form needs to be saved
        checkSave();
        
        // Move to the previous entry and show it on the form
        currentEntry--;
        showEntry(currentEntry);
    }

    // Method called when the "Next" button is clicked
    private void nextButtonActionPerformed(ActionEvent e) {
        // Check if the form needs to be saved
        checkSave();

        // Move to the next entry and show it on the form
        currentEntry++;
        showEntry(currentEntry);
    }

    // Method called when the "Print" button is clicked
    private void printButtonActionPerformed(ActionEvent e) 
    {
        // Calculate the number of pages based on the entries per page
        lastPage = (int) (1 + (numberEntries - 1) / entriesPerPage);

        // Create a printer job and set the printable document
        PrinterJob inventoryPrinterJob = PrinterJob.getPrinterJob();
        inventoryPrinterJob.setPrintable(new InventoryDocument());

        // Display the print dialog and print the document if selected
        if (inventoryPrinterJob.printDialog()) 
        {
            try 
            {
                inventoryPrinterJob.print();
            } catch (PrinterException ex) 
                {
                JOptionPane.showConfirmDialog(null, ex.getMessage(), "Print Error",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
        }
    }

    // Method called when the "Exit" button is clicked
    private void exitButtonActionPerformed(ActionEvent e) 
    {
        // Call the exitForm method to handle the form exit
        exitForm(null);
    }

    // Method called when the "Photo" button is clicked
    private void photoButtonActionPerformed(ActionEvent e) 
    {
        // Open a file chooser dialog to select a photo file
        JFileChooser openChooser = new JFileChooser();
        openChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        openChooser.setDialogTitle("Open Photo File");
        openChooser.addChoosableFileFilter(new FileNameExtensionFilter("Photo Files",
                "jpg"));

        // If a file is chosen, display the photo
        if (openChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            showPhoto(openChooser.getSelectedFile().toString());
    }

    // Method called when the "Search" button is clicked
    private void searchButtonActionPerformed(ActionEvent e) 
    {
        int i;

        // Check if there are any entries to search
        if (numberEntries == 0)
            return;
        
        // Get the letter clicked on the button
        String letterClicked = e.getActionCommand();
        
        i = 0;
        // Search for an item starting with the clicked letter
        do 
        {
            if (myInventory[i].description.substring(0, 1).equals(letterClicked)) 
            {
                // Set the current entry to the found item and show it on the form
                currentEntry = i + 1;
                showEntry(currentEntry);
                return;
            }
            i++;
        } while (i < numberEntries);
        
        // Display a message if no items starting with the clicked letter are found
        JOptionPane.showConfirmDialog(null, "No " + letterClicked + " inventory items.",
                "None Found", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Method called when the itemTextField has an action performed
    private void itemTextFieldActionPerformed(ActionEvent e) 
    {
        // Move the focus to the locationComboBox
        locationComboBox.requestFocus();
    }

    // Method called when the locationComboBox has an action performed
    private void locationComboBoxActionPerformed(ActionEvent e) 
    {
        // Check if the selected item is already in the list
        if (locationComboBox.getItemCount() != 0) 
        {
            for (int i = 0; i < locationComboBox.getItemCount(); i++) 
            {
                if (locationComboBox.getSelectedItem().toString().equals(locationComboBox.getItemAt(i).toString())) 
                {
                    // If it is, move the focus to the serialTextField and exit the method
                    serialTextField.requestFocus();
                    return;
                }
            }
        }
        
        // If the selected item is not found in the list, add it to the list and move the focus to the serialTextField
        locationComboBox.addItem(locationComboBox.getSelectedItem());
        serialTextField.requestFocus();
    }

    // Method called when the serialTextField has an action performed
    private void serialTextFieldActionPerformed(ActionEvent e) 
    {
        // Move the focus to the priceTextField
        priceTextField.requestFocus();
    }

    // Method called when the priceTextField has an action performed
    private void priceTextFieldActionPerformed(ActionEvent e) 
    {
        // Move the focus to the dateDateChooser
        dateDateChooser.requestFocus();
    }


    // Method called when the dateDateChooser's property changes
    private void dateDateChooserPropertyChange(PropertyChangeEvent e) 
    {
        // Move the focus to the storeTextField
        storeTextField.requestFocus();
    }


    // Method called when the storeTextField has an action performed
    private void storeTextFieldActionPerformed(ActionEvent e)
    {
        // Move the focus to the noteTextField
        noteTextField.requestFocus();
    }


    // Method called when the noteTextField has an action performed
    private void noteTextFieldActionPerformed(ActionEvent e) 
    {
         // Move the focus to the photoButton
        photoButton.requestFocus();
    }

    // Method for setting the size of a button
    private void sizeButton(JButton b, Dimension d) 
    {
        b.setPreferredSize(d);
        b.setMinimumSize(d);
        b.setMaximumSize(d);
    }

    // Method for displaying the entry with index j
    private void showEntry(int j) 
    {
        // Display the fields of the inventory item at index j (1 to numberEntries)
        itemTextField.setText(myInventory[j - 1].description);
        locationComboBox.setSelectedItem(myInventory[j - 1].location);
        markedCheckBox.setSelected(myInventory[j - 1].marked);
        serialTextField.setText(myInventory[j - 1].serialNumber);
        priceTextField.setText(myInventory[j - 1].purchasePrice);
        dateDateChooser.setDate(stringToDate(myInventory[j - 1].purchaseDate));
        storeTextField.setText(myInventory[j - 1].purchaseLocation);
        noteTextField.setText(myInventory[j - 1].note);
        showPhoto(myInventory[j - 1].photoFile);
        nextButton.setEnabled(true);
        previousButton.setEnabled(true);
        
        // Disable previousButton if j is the first entry
        if (j == 1)
        previousButton.setEnabled(false);

        // Disable nextButton if j is the last entry
        if (j == numberEntries)
        nextButton.setEnabled(false);

        // Set the focus to itemTextField
        itemTextField.requestFocus();
    }

    // Method for converting a date string to a Date object
    private Date stringToDate(String s) 
    {
        int m = Integer.valueOf(s.substring(0, 2)).intValue() - 1;
        int d = Integer.valueOf(s.substring(3, 5)).intValue();
        int y = Integer.valueOf(s.substring(6)).intValue() - 1900;
        return (new Date(y, m, d));
    }

    // Method for converting a Date object to a formatted date string
    private String dateToString(Date dd) 
    {
        String yString = String.valueOf(dd.getYear() + 1900);
        int m = dd.getMonth() + 1;
        String mString = new DecimalFormat("00").format(m);
        int d = dd.getDate();
        String dString = new DecimalFormat("00").format(d);
        return (mString + "/" + dString + "/" + yString);
    }

    // Method for displaying the photo file in the photoTextArea
    private void showPhoto(String photoFile) 
    {
        if (!photoFile.equals("")) {
            try {
                photoTextArea.setText(photoFile);
            } catch (Exception ex) {
                photoTextArea.setText("");
            }
        } else {
            photoTextArea.setText("");
        }
        photoPanel.repaint();
    }

    // Method for blanking out the input fields and resetting buttons
    private void blankValues() 
    {
        // Reset the input fields
        newButton.setEnabled(false);
        deleteButton.setEnabled(false);
        saveButton.setEnabled(true);
        previousButton.setEnabled(false);
        nextButton.setEnabled(false);
        printButton.setEnabled(false);
        itemTextField.setText("");
        locationComboBox.setSelectedItem("");
        markedCheckBox.setSelected(false);
        serialTextField.setText("");
        priceTextField.setText("");
        dateDateChooser.setDate(new Date());
        storeTextField.setText("");
        noteTextField.setText("");
        photoTextArea.setText("");
        photoPanel.repaint();
        itemTextField.requestFocus();
    }

    // Method for deleting an entry at index j
    private void deleteEntry(int j) 
    {
        // Delete the entry at index j by shifting all subsequent entries up
        if (j != numberEntries) 
        {
            // move all entries under j up one level
            for (int i = j; i < numberEntries; i++) 
            {
                myInventory[i - 1] = new InventoryItem();
                myInventory[i - 1] = myInventory[i];
            }
        }
        numberEntries--;
    }

    // Method for checking if changes have been made to the current entry and prompting to save them
    private void checkSave() 
    {
        boolean edited = false;

        // Check if any field has been edited
        if (!myInventory[currentEntry - 1].description.equals(itemTextField.getText()))
            edited = true;
        else if (!myInventory[currentEntry -
                1].location.equals(locationComboBox.getSelectedItem().toString()))
            edited = true;
        else if (myInventory[currentEntry - 1].marked != markedCheckBox.isSelected())
            edited = true;
        else if (!myInventory[currentEntry - 1].serialNumber.equals(serialTextField.getText()))
            edited = true;
        else if (!myInventory[currentEntry - 1].purchasePrice.equals(priceTextField.getText()))
            edited = true;
        else if (!myInventory[currentEntry -
                1].purchaseDate.equals(dateToString(dateDateChooser.getDate())))
            edited = true;
        else if (!myInventory[currentEntry -
                1].purchaseLocation.equals(storeTextField.getText()))
            edited = true;
        else if (!myInventory[currentEntry - 1].note.equals(noteTextField.getText()))
            edited = true;
        else if (!myInventory[currentEntry - 1].photoFile.equals(photoTextArea.getText()))
            edited = true;

        // If any field has been edited, prompt to save the changes
        if (edited) 
        {
            if (JOptionPane.showConfirmDialog(null, "You have edited this item. Do you want to save the changes?",
                    "Save Item", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                saveButton.doClick();
        }
    }
}

/* Photo Panel Class */
class PhotoPanel extends JPanel 
{
    // Method for painting the component (photo panel)
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        super.paintComponent(g2D);
        
        // Draw border
        g2D.setPaint(Color.BLACK);
        g2D.draw(new Rectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1));
        
        // Show photo
        Image photoImage = new ImageIcon(Home_Inventory_Manager.photoTextArea.getText()).getImage();
        int w = getWidth();
        int h = getHeight();
        double rWidth = (double) getWidth() / (double) photoImage.getWidth(null);
        double rHeight = (double) getHeight() / (double) photoImage.getHeight(null);
        
        if (rWidth > rHeight) 
        {
            // Leave height at display height, change width by amount height is changed
            w = (int) (photoImage.getWidth(null) * rHeight);
        } 
        else 
        {
            // Leave width at display width, change height by amount width is changed
            h = (int) (photoImage.getHeight(null) * rWidth);
        }
        
        // Center the photo in the panel
        g2D.drawImage(photoImage, (int) (0.5 * (getWidth() - w)), (int) (0.5 * (getHeight() -
                h)), w, h, null);
        g2D.dispose();
    }
}


/**
 ‣ Class representing an inventory document that implements the Printable interface. 
 */
class InventoryDocument implements Printable 
{
    /**
     ‣ Method for printing the inventory document.
     ‣
     ‣ @param g          the Graphics object used for rendering
     ‣ @param pf         the PageFormat object specifying the page format
     ‣ @param pageIndex  the index of the page being printed
     ‣ @return an integer representing the result of printing the page
     */
    public int print(Graphics g, PageFormat pf, int pageIndex) 
    {
        Graphics2D g2D = (Graphics2D) g;

        // Check if the pageIndex is valid
        if ((pageIndex + 1) > Home_Inventory_Manager.lastPage) 
        {
            return NO_SUCH_PAGE;
        }
        
        int i, iEnd;

        
        // Here you decide what goes on each page and draw it
        
        // Header
        g2D.setFont(new Font("Arial", Font.BOLD, 14));
        g2D.drawString("Home Inventory Items - Page " + String.valueOf(pageIndex + 1),
                (int) pf.getImageableX(), (int) (pf.getImageableY() + 25));
        
        // Get starting y coordinate
        int dy = (int) g2D.getFont().getStringBounds("S",
                g2D.getFontRenderContext()).getHeight();
        int y = (int) (pf.getImageableY() + 4 * dy);
        
        iEnd = Home_Inventory_Manager.entriesPerPage * (pageIndex + 1);
        if (iEnd > Home_Inventory_Manager.numberEntries)
            iEnd = Home_Inventory_Manager.numberEntries;
        
        for (i = 0 + Home_Inventory_Manager.entriesPerPage * pageIndex; i < iEnd; i++) {
            // Dividing line
            Line2D.Double dividingLine = new Line2D.Double(pf.getImageableX(), y,
                    pf.getImageableX() + pf.getImageableWidth(), y);
            g2D.draw(dividingLine);
            y += dy;

            //Item Description
            g2D.setFont(new Font("Arial", Font.BOLD, 12));
            g2D.drawString(Home_Inventory_Manager.myInventory[i].description, (int) pf.getImageableX(), y);
            y += dy;

            // Location
            g2D.setFont(new Font("Arial", Font.PLAIN, 12));
            g2D.drawString("Location: " + Home_Inventory_Manager.myInventory[i].location,
                    (int) (pf.getImageableX() + 25), y);
            y += dy;

            // Marked information
            if (Home_Inventory_Manager.myInventory[i].marked)
                g2D.drawString("Item is marked with identifying information.", (int) (pf.getImageableX() + 25), y);
            else
                g2D.drawString("Item is NOT marked with identifying information.", (int) (pf.getImageableX() + 25), y);
            y += dy;

            // Serial number
            g2D.drawString("Serial Number: " +
                    Home_Inventory_Manager.myInventory[i].serialNumber, (int) (pf.getImageableX() + 25), y);
            y += dy;

            // Price and purchase date
            g2D.drawString(
                    "Price: $" + Home_Inventory_Manager.myInventory[i].purchasePrice + ", Purchased on: "
                            + Home_Inventory_Manager.myInventory[i].purchaseDate,
                    (int) (pf.getImageableX() +
                            25),
                    y);
            y += dy;

            // Purchase location
            g2D.drawString("Purchased at: " +
                    Home_Inventory_Manager.myInventory[i].purchaseLocation, (int) (pf.getImageableX() + 25), y);
            y += dy;

            // Note
            g2D.drawString("Note: " + Home_Inventory_Manager.myInventory[i].note, (int) (pf.getImageableX() + 25), y);
            y += dy;

            
            try
            {
                //Maintain original width/height ratio
                Image inventoryImage = new ImageIcon(Home_Inventory_Manager.myInventory[i].photoFile).getImage();
                double ratio = (double) (inventoryImage.getWidth(null)) / (double) inventoryImage.getHeight(null);
                g2D.drawImage(inventoryImage, (int) (pf.getImageableX() + 25), y, (int) (100 *
                        ratio), 100, null);
            } 
            
            catch (Exception ex)
            {
                // have place to go in case image file doesn't open
            }
            y += 2 * dy + 100;
        }
        return PAGE_EXISTS;
    }
}
