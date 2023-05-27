/*CompSCIGUI.java
 * Instructions: Select your religion from the drop-down menu and type in the first 3 digits of your postal code. 
 * Then Click on the "Search database" button to get the names of places of worship near you.
 * Data file link: https://data.peelregion.ca/datasets/places-of-worship?geometry=-81.016%2C43.396%2C-78.615%2C44.090
 * Sarah - Input Output and GUI
 * Areesha - Input Output reading from file and splitting
 * Ahmed - processing the data and comparing
 */
package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;        

public class CompSciCPTGUI_1105 implements ActionListener 
{
	//declare GUI components
	JFrame frame;
	JPanel contentPane;
	JLabel  prompt2;
	static JLabel output;
	JTextField userPostalCode;
	JButton searchButton;
	JComboBox religionNames;
	String religionName;
	static String guiOutput = " ";

	/** Compares user religion and postal code to data file contents
	 *	pre:none
	 *  post:adds places near you in output string
	 * */
	public static void readCompareFromFile(String userReligion, String userPostalCode)
	{
		//objects for the input file
		File dataFile = new File("places_of_worship_cpt.txt");

		//gives access to file class functions
		FileReader in;
		BufferedReader readFile;

		String lineOfText;
		try
		{
			//these two lines set us up to open the text file and view it's contents
			in = new FileReader(dataFile);
			readFile = new BufferedReader(in);

			//process the input file and write it back
			String [] postalCode = null;
			
			//this Loop through the file line-by-line.
			while ((lineOfText = readFile.readLine())!= null)//read until file ends
			{
				String fileInfo[] = lineOfText.split(",");	//splits the data file contents based on comma
				String placesWorshipInfo[][] = new String[417][4];//allocates space for array and its the same size as data file size

				// checking religion input by the user is equal to religion for the this line
				if (fileInfo[1].equalsIgnoreCase(userReligion))
				{
					for (int r =0; r<1; r++) // repeat once for one line
					{
						for (int i = 0; i < placesWorshipInfo[0].length; i++)//repeats 4 times for each column
						{
							placesWorshipInfo[r][i] = fileInfo[i]; //add file info to array
							postalCode = fileInfo[3].split(" "); //splits postal code to get first 3 digits
						}

						//check if input postal code equals the one from text file's religion
						if (userPostalCode.equalsIgnoreCase(postalCode[0]))
						{
							System.out.println(fileInfo[0] +" "+ fileInfo[3]);
							// set the GUI output to the place of worship and postal code
							guiOutput = guiOutput.concat(fileInfo[0] +" "+ fileInfo[3] + "   ");
							
						}
					}
				}
			}
			// We close the file here that we opened earlier.
			readFile.close();
			in.close();
		}
		// Handles errors in opening/closing/processing text file.
		catch (FileNotFoundException e)
		{
			System.out.println("File does not exist or could not be found.");
			System.err.println("FileNotFoundException: " +e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("Problem reading file.");
			System.err.println("IOException: " +e.getMessage());
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Problem with Array Index.");
			System.err.println("ArrayIndexOutOfBoundsException: " +e.getMessage());
		}
	}

	public CompSciCPTGUI_1105()
	{
		/* Create and set up the frame */
		frame = new JFrame("Find Places Near You");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Create a content pane with a GridLayout and empty borders */
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(0, 1, 20, 20));
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 50, 50));

		/* Create a combo box and a descriptive label */
		String[] religions = {"Select one", "Islam", "Christianity", "Buddhism", "Sikhism", "Hinduism", "Judaism"};
		religionNames = new JComboBox(religions);
		religionNames.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
		religionNames.setSelectedIndex(0);
		religionNames.addActionListener(this);
		contentPane.add(religionNames);

		/* Add content pane to frame */
		frame.setContentPane(contentPane);

		/* Create and add a second prompt and then a text field */
		prompt2 = new JLabel("Enter the first 3 digits of your postal code: ");
		contentPane.add(prompt2);

		userPostalCode = new JTextField(10);
		contentPane.add(userPostalCode);

		/* Create and add button to search */
		searchButton = new JButton("Search Database");
		searchButton.setActionCommand("Search");
		searchButton.addActionListener(this);
		contentPane.add(searchButton);

		/* Create and add a label that will display the average */
		output = new JLabel(" ");
		output.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
		contentPane.add(output);

		/* Add content pane to frame */
		frame.setContentPane(contentPane);

		/* Size and then display the frame. */
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Handle button click action event
	 * pre: none
	 * post: The average of the grades entered has been calculated and displayed.
	 */
	public void actionPerformed(ActionEvent event) 
	{
		String eventName = event.getActionCommand();

		if (eventName.equals("Search")) {

			String userPostalCode1 = userPostalCode.getText();
			readCompareFromFile(religionName, userPostalCode1);
			output.setText(guiOutput);
		}
		else {
			JComboBox comboBox = (JComboBox)event.getSource();
			religionName = (String)comboBox.getSelectedItem();
		}
	}

	/**
	 * Create and show the GUI.
	 */
	private static void runGUI() 
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		CompSciCPTGUI_1105 findPlaces = new CompSciCPTGUI_1105(); //must match class name
	}

	public static void main(String[] args) 
	{
		/* Methods that create and show a GUI should be 
     	   run from an event-dispatching thread */
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
	}
}
