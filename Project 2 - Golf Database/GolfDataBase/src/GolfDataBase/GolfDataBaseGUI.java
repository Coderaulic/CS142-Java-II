package GolfDataBase;

import java.awt.Container;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Class:	<b>GolfDataBase</b>
*  File:	GolfDataBase.java
* <pre>
*  Description:	Description goes here.
*  @author:	<i>Ryu Muthui</i>
*  Environment:	PC, Windows 7, jdk1.7_17, NetBeans 7.3
*  Date:	5/15/2013
*  Hours:       30 hours
*  @version	1.01
*  @see         java.awt.Container;
*  @see         java.awt.Toolkit;
*  @see         java.io.BufferedReader;
*  @see         java.io.FileNotFoundException;
*  @see         java.io.FileReader;
*  @see         java.io.IOException;
*  @see         java.util.ArrayList;
*  @see         java.util.StringTokenizer;
*  @see         java.util.logging.Level;
*  @see         java.util.logging.Logger;
*  @see         javax.swing.DefaultListModel;
*  @see         javax.swing.JDialog;
*  @see         javax.swing.JFileChooser;
*  @see         javax.swing.JOptionPane;
*  @see         javax.swing.filechooser.FileNameExtensionFilter;
* </pre>
*  History Log:	Started 4/30/2013, Completed 5/15/2013.
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class GolfDataBaseGUI extends javax.swing.JFrame 
{
    //instance variables
    private String fileName = "src/GolfDataBase/memberList.txt";
    private String firstName, lastName, eMail, phoneNumber;
    private int memberRank, currentlySelectedIndex = 0;
    
    //used in determining if database is full
    private final int MAX_DATA_INDEX = 8;

    //use to pass information to the memberInformation class.
    MemberInformation myMemberDatabase = new MemberInformation();
    
    //ArrayList to store all information
    public ArrayList <MemberInformation> memberDatabase = new ArrayList();

    //For use in adding new members
    public static MemberInformation  tempNewMember = new MemberInformation();
    public static boolean  addNewMemberComplete;      //used for adding new members.
    
    /**
     * Default Constructor.
     */
    public GolfDataBaseGUI() 
    {
        initComponents();

        //Sets the Splash Screen timer to 4 seconds.
        try {Thread.sleep(4000);}
        catch(InterruptedException e){}
        
        //Sets scan new button as default and request focus to it at start.
        this.getRootPane().setDefaultButton(addJButton); 
        addJButton.requestFocus();
        
        //Sets the form to display at the center.
        setLocationRelativeTo(null);
        
        //Sets icon image on form.
        this.setIconImage(Toolkit.getDefaultToolkit()
                .getImage("src\\Resources\\GolfDB_Splash_Screen.png"));
        
        //read the memberList.txt and populate the shippingInfo Array List.
        readInfo(fileName);
        addToList();
        
        //Sets the background color of the form.
        Container background = this.getContentPane();
        background.setBackground(new java.awt.Color(204,255,255));
        
        playerJList.setSelectedIndex(0);
        updateJButton.setEnabled(false);
        updateJMenuItem.setEnabled(false);
        cancelEditJMenuItem.setEnabled(false);
    }

    //Class wide methods.
    
    /**
     * Method: uniqueRankValidator -
     * A method created to validate the rank as a the unique factor in checking for duplicate
     * members in the database. It searches through the main array List and organizes the rank of 
     * the members each time they are added, removed, and or updated.
     * Author Siqi Zhang
     */
    private void uniqueRankValidator()
    {    
           //swap downward
           if(memberRank > playerJList.getSelectedIndex())
           {         
               //when the ranknumber is smaller than the lowest rank in the database,
               //the system automatically set the ranknumber to the next one.
                 if(memberRank > memberDatabase.size()) 
                 {  
                    memberRank =  memberDatabase.size();
                    rankJFormattedTextField.setText(Integer.toString
                            (memberDatabase.get(memberDatabase.size()-1).getRank()));

                        for(int i = memberDatabase.size() -1; i >= 0; i--)
                        {
                            Swap(memberDatabase.get(0),memberDatabase.get(i));
                            
                            tempNewMember = new MemberInformation
                                 (memberDatabase.get(i).getFirstName(),
                                  memberDatabase.get(i).getLastName(),
                                  memberDatabase.get(i).getEmail(),
                                  memberDatabase.get(i).getPhoneNumber(),i + 1);     
                                  memberDatabase.set(i, tempNewMember);            
                        }
                  }
                  //change the player's rank to the lower rank,all the players
                  //between the two ranks will automatically move up by 1
                  else
                  {
                    for(int i = memberRank - 1; i >=playerJList.getSelectedIndex(); i--)
                    {
                       Swap(memberDatabase.get(playerJList.getSelectedIndex()),memberDatabase.get(i));

                       tempNewMember = new MemberInformation
                            (memberDatabase.get(i).getFirstName(),
                            memberDatabase.get(i).getLastName(),
                            memberDatabase.get(i).getEmail(),
                            memberDatabase.get(i).getPhoneNumber(),i + 1);     
                            memberDatabase.set(i, tempNewMember);            
                    }
                  }
             }
            //swap upward      
            //change the player's rank to a higher rank,all the players between 
            //the two ranks will automatically move down by 1
            else
            {
                for(int i = memberRank -1; i <= playerJList.getSelectedIndex(); i++)
                {
                    Swap(memberDatabase.get(playerJList.getSelectedIndex()),memberDatabase.get(i));
                    tempNewMember = new MemberInformation
                         (memberDatabase.get(i).getFirstName(),
                          memberDatabase.get(i).getLastName(),
                          memberDatabase.get(i).getEmail(),
                          memberDatabase.get(i).getPhoneNumber(),i+1);     
                          memberDatabase.set(i, tempNewMember);            
                }
            }
    }

    /**
     * Method: readInfo -
     * Read the information from the external text file and assign its value to the 
     * memberDatabase Array List.
     * @param inFilename 
     * @param void
     * Author Siqi Zhang
     */
    private void readInfo(String inFilename)
    {
       //constructing 
      FileReader inputFile;
      BufferedReader input;
      try
      {
          inputFile = new FileReader(inFilename);
          input = new BufferedReader(inputFile);
          memberDatabase.clear();
          //read a line of unput from text file
          String lineOfInput = input.readLine();
          
          while(lineOfInput!= null)//for each line of input
          {   
              StringTokenizer st = new StringTokenizer(lineOfInput,",");
              firstName = st.nextToken().toString();
              lastName = st.nextToken().toString();
              eMail = st.nextToken().toString();
              phoneNumber = st.nextToken().toString();
              memberRank = Integer.parseInt(st.nextToken().toString());         
              myMemberDatabase = new MemberInformation
                      (firstName,lastName,eMail,phoneNumber,memberRank);
              memberDatabase.add(myMemberDatabase);
              lineOfInput = input.readLine();//read line
          }      
      }
      //file not found exception
      catch(FileNotFoundException ex)
      {
          JOptionPane.showMessageDialog(null, "File not found", "File Open Error",
                    JOptionPane.WARNING_MESSAGE);
          
      }
      //input output exception
      catch(IOException ex)
      {
          JOptionPane.showMessageDialog(null, "File not found", "File Open Error",
                    JOptionPane.WARNING_MESSAGE);
      }    
    }
    
    /**
     * Method: addToList - 
     * Creates a default list model that holds the member's Last Name and First Name.
     * It then sets the list model to be displayed in the playerJList object.
     * Author Siqi Zhang
     * @return void
     * @throws IndexOutOfBoundsException
     */
    public void addToList()
    {   
        // list model for display
        DefaultListModel myListModel = new DefaultListModel();
        try
        {
            for(int i = 0; i < memberDatabase.size(); i++)
            {
               //add the Last Name and First Name to the List model.
               myListModel.add(i,memberDatabase.get(i).getLastName() + 
                       ", " + memberDatabase.get(i).getFirstName());
            }
            playerJList.setModel(myListModel);
        }
        catch(IndexOutOfBoundsException e){}     
     }  
    
    /**
     * Method: enabledEditing -
     * A method to enable the corresponding menu and button items when editing is in use.
     * Author: Ryu Muthui
     * @return void
     */
    private void enabledEditing()
    {
        firstNameJTextField.setEditable(true);
        lastNameJTextField.setEditable(true);
        emailJTextField.setEditable(true);
        phoneNumberJFormattedTextField.setEditable(true);
        rankJFormattedTextField.setEditable(true);
        firstNameJTextField.requestFocus();
        
        playerJList.setEnabled(false);
        updateJButton.setEnabled(true);
        addJButton.setEnabled(false);
        editJButton.setEnabled(false);
        deleteJButton.setEnabled(false);
        searchJButton.setEnabled(false);
        cancelEditJMenuItem.setEnabled(true);
        
        updateJMenuItem.setEnabled(true);
        addJMenuItem.setEnabled(false);
        editJMenuItem.setEnabled(false);
        deleteJMenuItem.setEnabled(false);
        searchJMenuItem.setEnabled(false);        
    }
    
    
    /**
     * Method: emptyDatabase - 
     * This method is used when the member database is cleared either by the user
     * removing all members or when the user decides to clear the entire arrayList
     * via the clearJMenuItem option. Once the array is clear this method is called
     * to set the corresponding menu and buttons with the cleared arrayList state.
     * Author: Ryu Muthui
     * @return void
     */
    private void emptyDatabase()
    {
        firstNameJTextField.setText("");
        lastNameJTextField.setText("");
        emailJTextField.setText("");
        phoneNumberJFormattedTextField.setText("");
        rankJFormattedTextField.setText("");
        searchJButton.setEnabled(false);
        deleteJButton.setEnabled(false);
        editJButton.setEnabled(false);
        searchJMenuItem.setEnabled(false);
        deleteJMenuItem.setEnabled(false);
        editJMenuItem.setEnabled(false);
    }
    
    /**
     * Method: disableEditing -
     * A method to disable the corresponding menu and button items when editing 
     * is no longer in use.
     * Author: Ryu Muthui
     * @return void
     */
    private void disableEditing()
    {
        firstNameJTextField.setEditable(false);
        lastNameJTextField.setEditable(false);
        emailJTextField.setEditable(false);
        phoneNumberJFormattedTextField.setEditable(false);
        rankJFormattedTextField.setEditable(false);
        cancelEditJMenuItem.setEnabled(false);
    }

    /**
     * Method: disableEntryFields -
     * A method to disable the entry fields when editing is canceled or completed.
     * Author: Ryu Muthui
     * @return void
     */
    private void disableEntryFields()
    {
        firstNameJTextField.setEditable(false);
        lastNameJTextField.setEditable(false);
        emailJTextField.setEditable(false);
        phoneNumberJFormattedTextField.setEditable(false);
        rankJFormattedTextField.setEditable(false);
    }
    
    /**
    * Method: getInformation -
    * A method to get data from all appropriate fields and store them to the
    * corresponding variable so that it can be passed to the array list.
    * Author: Ryu Muthui
    * @return void
    */
    private void getInformation()
    {
        firstName = firstNameJTextField.getText().trim();
        lastName = lastNameJTextField.getText().trim();
        eMail = emailJTextField.getText().trim();
        phoneNumber = phoneNumberJFormattedTextField.getText().trim();
        memberRank = Integer.parseInt(rankJFormattedTextField.getText().trim());
    }
        
    /**
    * Method: setInformation -
    * A method to get data from the array list at the corresponding index and 
    * set the data in the appropriate fields to be displayed to the user.
    * Author: Ryu Muthui
    * @return void
    */
    private void setInformation()
    {   
        //disables the add button when the the arrayList is at max size for single digits.
        if(playerJList.getLastVisibleIndex()== MAX_DATA_INDEX)
        {
            addJButton.setEnabled(false);
            addJMenuItem.setEnabled(false);
        }
        else
        {   
            addJButton.setEnabled(true);
            addJMenuItem.setEnabled(true);
        }
        //sets the information from the array list to the respected fields.
        firstNameJTextField.setText(memberDatabase.get(currentlySelectedIndex).getFirstName());
        lastNameJTextField.setText(memberDatabase.get(currentlySelectedIndex).getLastName());
        emailJTextField.setText(memberDatabase.get(currentlySelectedIndex).getEmail());
        phoneNumberJFormattedTextField.setText(memberDatabase.get(currentlySelectedIndex).getPhoneNumber());
        rankJFormattedTextField.setText(Integer.toString(memberDatabase.get(currentlySelectedIndex).getRank()));
    }
    

    /**
     * Method: setNewMember - 
     * Sets tempNewMember and addNewMemberComplete with values taken from the
     * addNewMemberJButtonperfomed subroutine.
     * @param newMember: Member to be added to database
     * @param isCompleted: Boolean y/n if the add member prompt was successful
     * Pre-condition: A valid member object and a boolean value
     * Post-condition: tempNewMember now holds the member object to be added to
     * the database as well as a boolean variable indicating if the adding process was completed.
     */
    public static void setNewMember(MemberInformation newMember, boolean isCompleted)
    {
        tempNewMember = newMember;
        addNewMemberComplete = isCompleted;
    }

    /**
     * Method: displayMembers -
     * A method to display the current members in the Array List. It sorts based on
     * Last Name and Rank depending on which display method is selected.
     * @see selectionSort
     * @see insertionSort
     * @return void
     */
    private void displayMembers()
    {
        String[] memberList = new String [memberDatabase.size()];  //Name only
        
        if(sortbyRankJRadioButtonMenuItem.isSelected())
        {
            //sort by rank using selection sort in ascending order
            selectionSort(memberDatabase);
            for(int index = 0; index < memberDatabase.size(); index++)
            {
                memberList[index]= memberDatabase.get(index).getLastName() + ", " +
                        memberDatabase.get(index).getFirstName() + ", " +
                        " Rank: " + memberDatabase.get(index).getRank();
            }
        }
        else
        {
            //sort by last name using insertion sort
            insertionSort(memberDatabase);
            for(int index = 0; index < memberDatabase .size(); index++)
            {
                memberList[index]= memberDatabase.get(index).getLastName() + ", " +
                        memberDatabase.get(index).getFirstName();
            }
        }
        playerJList.setListData(memberList);        //populate Jlist with members
        playerJList.setSelectedIndex(0);            //select first member
    }
    
    /**
     * Method: insertionSort - 
     * Sorts ArrayList of memberDatabase in ascending order by last name. 
     * Uses the insertion sort algorithm which inserts the member at correct position
     * and shuffles everyone else below that position.
     * @parem ArrayList: members
     * @return void
     * pre-condition: ArrayList memberDatabase filled-in with member objects.
     * post-condition: memberDatabase ArrayList is sorted by Last Name.
     */
    private void insertionSort(ArrayList <MemberInformation > members)
    {
        int i, j;
        for (i = 0; i < members.size(); i++)
        {
            MemberInformation temp = members.get(i);
            j = i - 1;
            while (j >=0 && members.get(j).getLastName().compareToIgnoreCase(temp.getLastName())>0)
            {
                members.set(j + 1, members.get(j));
                j--;
            }
            members.set(j+1, temp);
        }    
    }
    
    /**
     * Method: selectionSort -
     * Sorts the main ArrayList of members in descending order. Calls
     * findMaximum to find the member with the highest rank in each pass and
     * swap to exchange with lower rank when necessary.
     * @param members
     * @return void
     * pre-condition: ArrayList memberDatabse filled-in with MemberInformation objects.
     * post-condition: ArrayList is sorted by rank.
     */
    public void selectionSort(ArrayList <MemberInformation> members)
    {
        for (int i = 0; i < members.size(); i++)
        {
            int max = findTopRank(members, i);
            swap(members, i, max);
        }
    }
    
    /**
     * Method: findMaximum -
     * Called by selectionSort to find the index of the member with the highest
     * rank from a given index to the end of the ArrayList.
     * @param ArrayList: memberDatabase
     * @param int i: index from which to search for the max >= 0
     * @return int: position or index where maximum is located.
     * pre-condition: ArrayList memberDatabse filled-in with MemberInformation objects. int i >=0.
     * post-condition: members ArrayList is sorted by level.
     */
    private int findTopRank(ArrayList <MemberInformation> members, int i)
    {
        int j, max = i;
        for (j = i + 1; j < members.size(); j++)
        {
            if (members.get(j).getRank() < members.get(max).getRank())
                max = j;
        }    
        return max;
    }
    
     /**
     * Method: swap -
     * Called by selectionSort to find the index of the member with the maximum
     * level from a given index to the end of the ArrayList
     * @parem ArrayList: members
     * @parem  int i: index of element to be swapped >= 0
     * @parem  int j: index of element to be swapped >= 0
     * @return void
     * pre-condition: ArrayList members filled-in with members objects, int i, j >= 0.
     * post-condition: members ArrayList with two members swapped.
     */
    private void swap(ArrayList <MemberInformation> members, int i, int j)
    {
        MemberInformation temp = members.get(i);
        members.set(i, members.get(j));
        members.set(j, temp);
    }
    
    /**
     * Method: binarySearch -
     * Called by addJButtonActionPerformed and searchJMenuItemActionPerformed
     * searchJButtonActionPerformedto find the index of the members array list searched.
     * @parem String[]: array of cities to be searched
     * @parem  String key: member to be found
     * @return int: index of where member is found; -1 if not found
     * pre-condition: String array filled-in with member objects
     * pre-condition: String key of valid members.
     * post-condition: int in the range of [0,array.length - 1] or -1
     */
    public static int binarySearch(String[] array, String key)
    {
        int low = 0;
        int high = array.length -1;
        int middle;

        while ( low <= high )
        {
            middle = (low + high ) / 2;

            if ( key.equalsIgnoreCase(array[middle])) //match
                return middle;
            else if ( key.compareToIgnoreCase(array[middle])<0)
                high = middle -1; //search low end of array
            else 
                low = middle + 1; //search high end of array
        }
        return -1; //search high end of the array.
    }

    /**
     * Method saveMembers -
     * A method to save changes made to the city data. It opens the file, writes
     * the changes made in the ArrayList to the data text file and closes it after
     * it finishes writing to it.
     * @return void
     * Author Ryu Muthui
     */
    private void saveMembers()
    {
        try
        {
            WriteFile MyWriteFile = new WriteFile(fileName);
            MemberInformation tempNewMember; 
            for(int index = 0; index < memberDatabase.size(); index++)
            {
                tempNewMember = memberDatabase.get(index);
                String output = tempNewMember.getFirstName() + "," + 
                                tempNewMember.getLastName() + "," +
                                tempNewMember.getEmail() + "," + 
                                tempNewMember.getPhoneNumber() + "," +
                                tempNewMember.getRank();
                MyWriteFile.Write(output);
            }
            
            MyWriteFile.close(); //close file
        }
        catch(Exception ex)
        {
            Logger.getLogger(MemberInformation.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
     /**
     * Method: memberExists -
     * Method to determine if member already exists.
     * @param memberExists
     * @return 
     */
    private boolean memberExists(MemberInformation memberExists)
    {
        boolean thereIsOne = false;
        for(int index = 0; index < memberDatabase.size(); index++)
        {
            if (memberDatabase.get(index).equals(memberExists))
                thereIsOne = true;
        }
        return thereIsOne;
    }
    
    /**
     * Method: Swap -
     * Method to swap 2 elements in the array list.
     * @param element1 
     * @param element2
     * @return void
     * Author Siqi Zhang
     */
    private void Swap(MemberInformation element1, MemberInformation element2)
    {    
         int indexOfElement2 = memberDatabase.indexOf(element2);
         MemberInformation temp;
         temp = element1;      
         memberDatabase.set(memberDatabase.indexOf(element1), element2);
         memberDatabase.set(indexOfElement2, temp);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sortingJButtonGroup = new javax.swing.ButtonGroup();
        titleJPanel = new javax.swing.JPanel();
        titleImageJLabel = new javax.swing.JLabel();
        titleJLabel = new javax.swing.JLabel();
        informationJPanel = new javax.swing.JPanel();
        firstNameJLabel = new javax.swing.JLabel();
        lastNameJLabel = new javax.swing.JLabel();
        emailJLabel = new javax.swing.JLabel();
        phoneNumberJLabel = new javax.swing.JLabel();
        rankJLabel = new javax.swing.JLabel();
        firstNameJTextField = new javax.swing.JTextField();
        lastNameJTextField = new javax.swing.JTextField();
        emailJTextField = new javax.swing.JTextField();
        phoneNumberJFormattedTextField = new javax.swing.JFormattedTextField();
        rankJFormattedTextField = new javax.swing.JFormattedTextField();
        golfImageJLabel = new javax.swing.JLabel();
        controlJPanel = new javax.swing.JPanel();
        addJButton = new javax.swing.JButton();
        editJButton = new javax.swing.JButton();
        updateJButton = new javax.swing.JButton();
        deleteJButton = new javax.swing.JButton();
        searchJButton = new javax.swing.JButton();
        exitJButton = new javax.swing.JButton();
        playersJPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerJList = new javax.swing.JList();
        fileJMenuBar = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        openNewJMenuItem = new javax.swing.JMenuItem();
        openNewJSeparator = new javax.swing.JPopupMenu.Separator();
        saveJMenuItem = new javax.swing.JMenuItem();
        saveAsJMenuItem = new javax.swing.JMenuItem();
        saveJSeparator = new javax.swing.JPopupMenu.Separator();
        clearJMenuItem = new javax.swing.JMenuItem();
        clearJSeparator = new javax.swing.JPopupMenu.Separator();
        printJMenuItem = new javax.swing.JMenuItem();
        printJSeparator = new javax.swing.JPopupMenu.Separator();
        exitJMenuItem = new javax.swing.JMenuItem();
        sortJMenu = new javax.swing.JMenu();
        sortByLastNameJRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        sortbyRankJRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        actionJMenu = new javax.swing.JMenu();
        addJMenuItem = new javax.swing.JMenuItem();
        editJMenuItem = new javax.swing.JMenuItem();
        cancelEditJMenuItem = new javax.swing.JMenuItem();
        updateJMenuItem = new javax.swing.JMenuItem();
        deleteJMenuItem = new javax.swing.JMenuItem();
        searchJMenuItem = new javax.swing.JMenuItem();
        helpJMenu = new javax.swing.JMenu();
        aboutJMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Golf Data Base");
        setBackground(new java.awt.Color(153, 255, 255));
        setResizable(false);

        titleJPanel.setBackground(new java.awt.Color(204, 255, 255));

        titleImageJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/GolfDB_Title_Logo.png"))); // NOI18N

        titleJLabel.setBackground(new java.awt.Color(153, 255, 255));
        titleJLabel.setFont(new java.awt.Font("Vijaya", 2, 48)); // NOI18N
        titleJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleJLabel.setText("Golf Database");

        javax.swing.GroupLayout titleJPanelLayout = new javax.swing.GroupLayout(titleJPanel);
        titleJPanel.setLayout(titleJPanelLayout);
        titleJPanelLayout.setHorizontalGroup(
            titleJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleJPanelLayout.createSequentialGroup()
                .addComponent(titleImageJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addContainerGap())
        );
        titleJPanelLayout.setVerticalGroup(
            titleJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(titleImageJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        informationJPanel.setBackground(new java.awt.Color(204, 255, 255));
        informationJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Player Information:"));

        firstNameJLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        firstNameJLabel.setText("First Name:");

        lastNameJLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lastNameJLabel.setText("Last Name:");

        emailJLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        emailJLabel.setText("Email Address:");

        phoneNumberJLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        phoneNumberJLabel.setText("Phone Number:");

        rankJLabel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        rankJLabel.setText("Player Rank:");

        firstNameJTextField.setEditable(false);
        firstNameJTextField.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        lastNameJTextField.setEditable(false);
        lastNameJTextField.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        emailJTextField.setEditable(false);
        emailJTextField.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        phoneNumberJFormattedTextField.setEditable(false);
        try {
            phoneNumberJFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        rankJFormattedTextField.setEditable(false);
        try {
            rankJFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        golfImageJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/GolfDB_Hole_Image.png"))); // NOI18N

        javax.swing.GroupLayout informationJPanelLayout = new javax.swing.GroupLayout(informationJPanel);
        informationJPanel.setLayout(informationJPanelLayout);
        informationJPanelLayout.setHorizontalGroup(
            informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(informationJPanelLayout.createSequentialGroup()
                        .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lastNameJLabel)
                            .addComponent(emailJLabel)
                            .addComponent(firstNameJLabel))
                        .addGap(18, 18, 18)
                        .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastNameJTextField)
                            .addComponent(firstNameJTextField)
                            .addComponent(emailJTextField)))
                    .addGroup(informationJPanelLayout.createSequentialGroup()
                        .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rankJLabel)
                            .addComponent(phoneNumberJLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(phoneNumberJFormattedTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(rankJFormattedTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(golfImageJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        informationJPanelLayout.setVerticalGroup(
            informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationJPanelLayout.createSequentialGroup()
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(firstNameJLabel))
                    .addComponent(firstNameJTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lastNameJLabel)
                    .addComponent(lastNameJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailJLabel)
                    .addComponent(emailJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationJPanelLayout.createSequentialGroup()
                        .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(informationJPanelLayout.createSequentialGroup()
                                .addComponent(phoneNumberJLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(informationJPanelLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(phoneNumberJFormattedTextField)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(informationJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rankJLabel)
                            .addComponent(rankJFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(golfImageJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        controlJPanel.setBackground(new java.awt.Color(204, 255, 255));
        controlJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        controlJPanel.setLayout(new java.awt.GridLayout(1, 5));

        addJButton.setMnemonic('A');
        addJButton.setText("Add");
        addJButton.setToolTipText("Select to add a new member to the Database.");
        addJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJButtonActionPerformed(evt);
            }
        });
        controlJPanel.add(addJButton);

        editJButton.setMnemonic('E');
        editJButton.setText("Edit");
        editJButton.setToolTipText("Select to edit the current member's information.");
        editJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJButtonActionPerformed(evt);
            }
        });
        controlJPanel.add(editJButton);

        updateJButton.setMnemonic('U');
        updateJButton.setText("Update");
        updateJButton.setToolTipText("Select to confirm the changes made to the member info.");
        updateJButton.setEnabled(false);
        updateJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateJButtonActionPerformed(evt);
            }
        });
        controlJPanel.add(updateJButton);

        deleteJButton.setMnemonic('D');
        deleteJButton.setText("Delete");
        deleteJButton.setToolTipText("Select to remove the current member.");
        deleteJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJButtonActionPerformed(evt);
            }
        });
        controlJPanel.add(deleteJButton);

        searchJButton.setMnemonic('S');
        searchJButton.setText("Search");
        searchJButton.setToolTipText("Search for member in the Database.");
        searchJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJButtonActionPerformed(evt);
            }
        });
        controlJPanel.add(searchJButton);

        exitJButton.setMnemonic('X');
        exitJButton.setText("Exit");
        exitJButton.setToolTipText("Select to Exit the program.");
        exitJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJButtonActionPerformed(evt);
            }
        });
        controlJPanel.add(exitJButton);

        playersJPanel.setBackground(new java.awt.Color(204, 255, 255));
        playersJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Players:"));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        playerJList.setToolTipText("Select a member from the list to view their information.");
        playerJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                playerJListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(playerJList);

        javax.swing.GroupLayout playersJPanelLayout = new javax.swing.GroupLayout(playersJPanel);
        playersJPanel.setLayout(playersJPanelLayout);
        playersJPanelLayout.setHorizontalGroup(
            playersJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
        );
        playersJPanelLayout.setVerticalGroup(
            playersJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        fileJMenuBar.setBackground(new java.awt.Color(204, 255, 255));
        fileJMenuBar.setBorder(null);

        fileJMenu.setMnemonic('F');
        fileJMenu.setText("File");

        openNewJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/New.png"))); // NOI18N
        openNewJMenuItem.setMnemonic('O');
        openNewJMenuItem.setText("Open New");
        openNewJMenuItem.setToolTipText("Select to Open a New Database file.");
        openNewJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openNewJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(openNewJMenuItem);
        fileJMenu.add(openNewJSeparator);

        saveJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Save.png"))); // NOI18N
        saveJMenuItem.setMnemonic('S');
        saveJMenuItem.setText("Save");
        saveJMenuItem.setToolTipText("Select to save the changes made to the current Database.");
        saveJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(saveJMenuItem);

        saveAsJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/SaveAs.png"))); // NOI18N
        saveAsJMenuItem.setMnemonic('A');
        saveAsJMenuItem.setText("Save As");
        saveAsJMenuItem.setToolTipText("Select to save the current Database as a new item.");
        saveAsJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(saveAsJMenuItem);
        fileJMenu.add(saveJSeparator);

        clearJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Clear.png"))); // NOI18N
        clearJMenuItem.setMnemonic('C');
        clearJMenuItem.setText("Clear");
        clearJMenuItem.setToolTipText("Select to Clear out the current Database.");
        clearJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(clearJMenuItem);
        fileJMenu.add(clearJSeparator);

        printJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Print.png"))); // NOI18N
        printJMenuItem.setMnemonic('P');
        printJMenuItem.setText("Print");
        printJMenuItem.setToolTipText("Select to Print out the currently selected member info.");
        printJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printJMenuItem);
        fileJMenu.add(printJSeparator);

        exitJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Exit.png"))); // NOI18N
        exitJMenuItem.setMnemonic('E');
        exitJMenuItem.setText("Exit");
        exitJMenuItem.setToolTipText("Select to Exit the program.");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMenuItem);

        fileJMenuBar.add(fileJMenu);

        sortJMenu.setMnemonic('S');
        sortJMenu.setText("Sort");

        sortingJButtonGroup.add(sortByLastNameJRadioButtonMenuItem);
        sortByLastNameJRadioButtonMenuItem.setSelected(true);
        sortByLastNameJRadioButtonMenuItem.setText("Sort by Last Name");
        sortByLastNameJRadioButtonMenuItem.setToolTipText("Sort the Database by Last Names.");
        sortByLastNameJRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortByLastNameJRadioButtonMenuItemActionPerformed(evt);
            }
        });
        sortJMenu.add(sortByLastNameJRadioButtonMenuItem);

        sortingJButtonGroup.add(sortbyRankJRadioButtonMenuItem);
        sortbyRankJRadioButtonMenuItem.setText("Sort by Rank");
        sortbyRankJRadioButtonMenuItem.setToolTipText("Sort the Database by Rank.");
        sortbyRankJRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortbyRankJRadioButtonMenuItemActionPerformed(evt);
            }
        });
        sortJMenu.add(sortbyRankJRadioButtonMenuItem);

        fileJMenuBar.add(sortJMenu);

        actionJMenu.setMnemonic('A');
        actionJMenu.setText("Action");
        actionJMenu.setToolTipText("Select to add a new member to the Database.");

        addJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Add.png"))); // NOI18N
        addJMenuItem.setMnemonic('D');
        addJMenuItem.setText("Add");
        addJMenuItem.setToolTipText("Select to add a new member to the Database.");
        addJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(addJMenuItem);

        editJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Edit.png"))); // NOI18N
        editJMenuItem.setMnemonic('I');
        editJMenuItem.setText("Edit");
        editJMenuItem.setToolTipText("Select to edit the current member's information.");
        editJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(editJMenuItem);

        cancelEditJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Cancel.png"))); // NOI18N
        cancelEditJMenuItem.setMnemonic('C');
        cancelEditJMenuItem.setText("Cancel Edit");
        cancelEditJMenuItem.setToolTipText("Cancel editting of member info.");
        cancelEditJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEditJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(cancelEditJMenuItem);

        updateJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Update.png"))); // NOI18N
        updateJMenuItem.setMnemonic('U');
        updateJMenuItem.setText("Update");
        updateJMenuItem.setToolTipText("Select to confirm the changes made to the member info.");
        updateJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(updateJMenuItem);

        deleteJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Remove.png"))); // NOI18N
        deleteJMenuItem.setMnemonic('E');
        deleteJMenuItem.setText("Delete");
        deleteJMenuItem.setToolTipText("Select to remove the current member.");
        deleteJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(deleteJMenuItem);

        searchJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Search.png"))); // NOI18N
        searchJMenuItem.setMnemonic('S');
        searchJMenuItem.setText("Search");
        searchJMenuItem.setToolTipText("Search for member in the Database.");
        searchJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJMenuItemActionPerformed(evt);
            }
        });
        actionJMenu.add(searchJMenuItem);

        fileJMenuBar.add(actionJMenu);

        helpJMenu.setMnemonic('H');
        helpJMenu.setText("Help");

        aboutJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/About.png"))); // NOI18N
        aboutJMenuItem.setMnemonic('A');
        aboutJMenuItem.setText("About");
        aboutJMenuItem.setToolTipText("Select to display additional information about the application.");
        aboutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJMenuItemActionPerformed(evt);
            }
        });
        helpJMenu.add(aboutJMenuItem);

        fileJMenuBar.add(helpJMenu);

        setJMenuBar(fileJMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(informationJPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titleJPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(controlJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playersJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(playersJPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titleJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(informationJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(controlJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Calls the AddmemberGUI JDialogue when selected. The user will enter new
     * member information in that GUI and the values are then passed back to the
     * main GUI and gets added to the memberDatabase.
     * @param evt addJButtonActionPerformed
     */
    private void addJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJButtonActionPerformed

        JDialog MyNewJDialog = new AddMemberGUI(this, true);
        MyNewJDialog.setVisible(true);
        
        if (addNewMemberComplete)
        {
            if (!memberExists(tempNewMember))
            {
                memberDatabase.add(tempNewMember);
                displayMembers();
                //saveMembers();
                //Grab the name of new member and create an array of all other member names
                String memberName = tempNewMember.getFirstName();
                String[] membersArray = new String[memberDatabase.size()];
                for (int i = 0; i < membersArray.length; i++)
                {
                    membersArray[i] = memberDatabase.get(i).getFirstName();
                }
                //Find index of member in newly sorted array of members
                //And select the index where it sits
                int index = binarySearch(membersArray, memberName);
                playerJList.setSelectedIndex(index);
            }
            else    //Handles if the player already exists.
            {
                JOptionPane.showMessageDialog(null, 
                    "This member already exists in the Database!", "Error",
                    JOptionPane.WARNING_MESSAGE);
                playerJList.setSelectedIndex(0);
            }
        }
        saveMembers();
        deleteJButton.setEnabled(true);
        searchJButton.setEnabled(true);
        editJButton.setEnabled(true);
        deleteJMenuItem.setEnabled(true);
        searchJMenuItem.setEnabled(true);
        editJMenuItem.setEnabled(true);
        setInformation();
    }//GEN-LAST:event_addJButtonActionPerformed

    /**
     * Invokes what the addJButton does.
     * @param evt addJMenuItemActionPerformed
     */
    private void addJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJMenuItemActionPerformed
        addJButton.doClick();
    }//GEN-LAST:event_addJMenuItemActionPerformed

    /**
     * Calls the enabledEditing Method to enable the appropriate buttons and menus
     * to allow for the user to edit member information.
     * Author Ryu Muthui
     * @param evt editJButtonActionPerformed
     */
    private void editJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJButtonActionPerformed
        //Enable the fields to be editable so the user can change current information.
        enabledEditing();
    }//GEN-LAST:event_editJButtonActionPerformed

    /**
     * Invoke what the editJButton does.
     * @param evt scanNewJMenuItemActionPerformed
     */
    private void editJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJMenuItemActionPerformed
         editJButton.doClick();
    }//GEN-LAST:event_editJMenuItemActionPerformed

    /**
     * Allows the user to delete a member from the database. The array list rank is 
     * also resorted so that all the ranks of the players are adjusted when a player is removed.
     * It re-sorts the array list and displays the updated member list in the JList.
     * Author Siki Zhang
     * @param evt deleteJButtonActionPerformed
     */
    private void deleteJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJButtonActionPerformed
        
       try
       {
            //Delete the selected member from the database.
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want " +
                    "to remove this member?","Delete member",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) //Confirmed with yest for deleting selected city.
            {   
                int index = playerJList.getSelectedIndex();

                //removes the selected member from the array list.
                memberDatabase.remove(index);
                
                for(int i = index; i<= (memberDatabase.size() -1); i++)
                {   
                    tempNewMember = new MemberInformation
                          (memberDatabase.get(i).getFirstName(),memberDatabase.get(i).getLastName(),
                           memberDatabase.get(i).getEmail(), memberDatabase.get(i).getPhoneNumber(),
                           memberDatabase.get(i).getRank()-1); memberDatabase.set(i, tempNewMember);
                }
                //re-sorts the array list and displays the updated memberlist in the JList.
                saveMembers();
                displayMembers();
            }  
        }
        catch(IndexOutOfBoundsException e)
        {
            emptyDatabase();
            memberDatabase.clear();
        }
    }//GEN-LAST:event_deleteJButtonActionPerformed

    /**
     * Invokes what the deleteJButtons does.
     * @param evt deleteJMenuItemActionPerformed
     */
    private void deleteJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJMenuItemActionPerformed
        deleteJButton.doClick();
    }//GEN-LAST:event_deleteJMenuItemActionPerformed

    /**
     * Displays an input Dialogue for the user to enter a name to search for.
     * The user must search by last name first then the first name in order to 
     * search the members list that would return results.
     * Author Siqi Zhang
     * @param evt searchButtonActionPerformed
     */
    private void searchJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJButtonActionPerformed
        
        // Find specified player by searching for their name via binary search
        try
        {
            String playerName = JOptionPane.showInputDialog(
                    "Enter name of player(LastName space FirstName)").trim();
            if ((playerName != null)||(!playerName.equals("")))
            {
                //Sort the JList of players by name
                sortByLastNameJRadioButtonMenuItem.setSelected(true);
                displayMembers();
                //Create a String array of player names
                String[] playerArray = new String[memberDatabase.size()];
                for (int i = 0; i < playerArray.length; i++)
                {
                    playerArray[i] = memberDatabase.get(i).getLastName() + " " +
                            memberDatabase.get(i).getFirstName();
                }
                //Find index of player
                int index = binarySearch(playerArray, playerName);
                if (index == -1)
                {
                    JOptionPane.showMessageDialog(null, playerName + " not Found",
                            "Search Result", JOptionPane.WARNING_MESSAGE);
                    playerJList.setSelectedIndex(0);
                }
                else
                    playerJList.setSelectedIndex(index);
             }
        }
        catch (NullPointerException nullex)
        {
            JOptionPane.showMessageDialog(null, "Member not searched", "Search Error",
            JOptionPane.WARNING_MESSAGE);
            playerJList.setVisible(true);
            playerJList.setSelectedIndex(0);
        }    
    }//GEN-LAST:event_searchJButtonActionPerformed

    /**
     * Invokes what the searchJButton does.
     * Author Siqi Zhang
     * @param evt searchJMenuItemActionPerformed
     */
    private void searchJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJMenuItemActionPerformed
        searchJButton.doClick();
    }//GEN-LAST:event_searchJMenuItemActionPerformed

    /**
     * Display the About Form when selected.
     * @param evt aboutJMenuItemActionPerformed
     */
    private void aboutJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutJMenuItemActionPerformed
        AboutForm myAbout = new AboutForm();
        myAbout.setVisible(true);
    }//GEN-LAST:event_aboutJMenuItemActionPerformed

    /**
     * Exit the application when selected.
     * Author Ryu Muthui
     * @param evt exitJButtonActionPerformed
     */
    private void exitJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitJButtonActionPerformed

    /**
     * Exit the application when selected.
     * Author Ryu Muthui
     * @param evt exitJMenuItemActionPerformed
     */
    private void exitJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitJMenuItemActionPerformed

    /**
     * Calls the Print Utilities class to print out the entire GUI.
     * Author Ryu Muthui
     * @param evt Menu Action Event.
     * @see PrintUtilities Class.
     */
    private void printJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJMenuItemActionPerformed
        try{PrintUtilities.printComponent(this);}
        catch(Exception e){} //Exception when printer is not found.
    }//GEN-LAST:event_printJMenuItemActionPerformed

    /**
     * This clears out the current member arrayList to no members and sets the state so that
     * an entirely new member database can be started by the user.
     * Author Ryu Muthui
     * @param evt clearJMenuItemActionPerformed
     */
    private void clearJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearJMenuItemActionPerformed
        
        int choice = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to clear the database?", "Clear database?",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if(choice == JOptionPane.YES_OPTION)  //Clear the database
        {
            memberDatabase.clear();
            addToList();
            emptyDatabase();
        }
    }//GEN-LAST:event_clearJMenuItemActionPerformed

    /**
     * Calls the JFileChooser to allow the user to select and find a database text
     * file they want to open and load into the program.
     * Author Siqi Zhang
     * @param evt openNewJMenuItemActionPerformed
     */
    private void openNewJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openNewJMenuItemActionPerformed
        
        String fileUserDirPath = System.getProperty("user.dir");
        JFileChooser chooser = new JFileChooser(fileUserDirPath);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Choose a Text(.txt) Files to load", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
           //read the new file and its data.
           fileName = chooser.getSelectedFile().getPath();                 
           readInfo(fileName);
           addToList(); 
           playerJList.setSelectedIndex(0);
         }
    }//GEN-LAST:event_openNewJMenuItemActionPerformed

    /**
     * Calls the saveMembers method so that the use can manually save changes to the '
     * membersDataArrayList manually.
     * Author Ryu Muthui
     * @param evt saveJMenuItemActionPerformed
     */
    private void saveJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJMenuItemActionPerformed
        //Saves the changes made by the user and updates the current Arraylist.
        saveMembers();
    }//GEN-LAST:event_saveJMenuItemActionPerformed

    /**
     * Calls the JFileChooser to allow the user to view the showSaveDialog options in 
     * order to name and save the memberDataArrayList as a text file.
     * Author Ryu Muthui
     * @param evt 
     */
    private void saveAsJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsJMenuItemActionPerformed
   
        //Sets the directory of where to save the file.
        String fileUserDirPath = System.getProperty("user.dir");
        //File filePath;
        JFileChooser chooser = new JFileChooser(fileUserDirPath); 
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Choose a Text(.txt) File to Save As", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(null);
          if(returnVal == JFileChooser.APPROVE_OPTION)
            {
               fileName = chooser.getSelectedFile().getPath(); 
               if (!fileName.endsWith(".txt"))
               {
               fileName += ".txt";
               }
               saveMembers();
            }
    }//GEN-LAST:event_saveAsJMenuItemActionPerformed

    /**
     * Sorts the member list by Last Name and display it accordingly in the playerJlist.
     * @param evt sortByLastNameJRadioButtonMenuItemActionPerformed
     * Author Ryu Muthui
     */
    private void sortByLastNameJRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortByLastNameJRadioButtonMenuItemActionPerformed
        displayMembers();    
    }//GEN-LAST:event_sortByLastNameJRadioButtonMenuItemActionPerformed

    /**
     * Sorts the member list by member Rank and display it accordingly in the playerJlist.
     * @param evt sortbyRankJRadioButtonMenuItemActionPerformed
     * Author Ryu Muthui
     */
    private void sortbyRankJRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortbyRankJRadioButtonMenuItemActionPerformed
        displayMembers();
    }//GEN-LAST:event_sortbyRankJRadioButtonMenuItemActionPerformed

    /**
    * Display the selected player information whenever the user selects a member from the
    * playerJlist.
    * @param evt playerJListValueChanged
    * Author Siqi Zhang
    */
    private void playerJListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_playerJListValueChanged
        currentlySelectedIndex = playerJList.getSelectedIndex();
        //check for valid index
        currentlySelectedIndex = (playerJList.getSelectedIndex() >=0 ? currentlySelectedIndex: 0);
        setInformation();
    }//GEN-LAST:event_playerJListValueChanged

    /**
     * A subroutine to update current members of the golf team with new information.
     * If the user confirms the changes will take place, if not it is disregarded.
     * @param evt updateJButtonActionPerformed
     * Author Ryu Muthui and Siqi Zhang
     */
    private void updateJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateJButtonActionPerformed
        
        //Display confirmation dialog for editing city
        int index = playerJList.getSelectedIndex();
        int choice = JOptionPane.showConfirmDialog(null, "Save Changes?", "Update Member Information",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if(choice == JOptionPane.NO_OPTION)  //Save changes
        {
            setInformation();
            index = playerJList.getSelectedIndex();
            playerJList.setSelectedIndex(index);
            playerJList.setEnabled(false);
            firstNameJTextField.requestFocus();
            addJButton.setEnabled(false);
            addJMenuItem.setEnabled(false);
        }
        else
        {   
            getInformation();
            
            //used for determining if email and phone number is valid inputs.
            boolean isEmail = Validation.isEmail(emailJTextField.getText().trim());
            boolean isPhoneNumber = Validation.isNumber(phoneNumberJFormattedTextField.getText().trim());
        
            //input validation for each entry fields
            try
            {   
                //Validate the firstname and lastname field so that they are not empty
                if(firstNameJTextField.getText().trim().isEmpty() || lastNameJTextField.getText().trim().isEmpty() )
                {
                    JOptionPane.showMessageDialog(null, "Name Fields can not be left blank.", "Invalid Entry!",
                    JOptionPane.WARNING_MESSAGE);
                    firstNameJTextField.requestFocus();
                    playerJList.setEnabled(false);
                }
                else 
                {   
                    //check for the validate email input
                    if(isEmail == false)
                    {
                        JOptionPane.showMessageDialog(null, "Email entry is invalid." + 
                        "Example: abc@abc.com", "Invalid Entry!", JOptionPane.WARNING_MESSAGE);
                        emailJTextField.requestFocus();
                        playerJList.setEnabled(false);
                    }
                    else
                    {
                         //check for the validate phonenumber input
                        if(isPhoneNumber == false)
                        {
                            JOptionPane.showMessageDialog(null, "Phone Number entry is invalid.", 
                            "Invalid Entry!", JOptionPane.WARNING_MESSAGE);
                            phoneNumberJFormattedTextField.requestFocus();
                            playerJList.setEnabled(false);
                        }
                        else
                        {   
                            //check to see if there is duplicate rank.
                            if(memberRank == 0)
                            {
                                JOptionPane.showMessageDialog(null, "Entry of Rank 0 is not allowed.",
                                "Invalid Input!", JOptionPane.WARNING_MESSAGE);
                                rankJFormattedTextField.selectAll();
                                rankJFormattedTextField.requestFocus();
                                playerJList.setEnabled(false);
                            }
                            else
                            {
                            //call this method to
                            uniqueRankValidator();
                            
                            //Create the updated member information.     
                            MemberInformation  updateMember = new MemberInformation (firstName,
                                    lastName, eMail, phoneNumber, memberRank);

                            //Delete old member information and replace it with new data.
                            memberDatabase.set(memberRank-1,updateMember);
                            
                            index = memberRank - 1;
                    
                            String[] memberList = new String [memberDatabase.size()];
                            for(int i = 0; i < memberDatabase.size(); i++)
                            {
                               memberList[i]= memberDatabase.get(i).getLastName() + ", " +
                                       memberDatabase.get(i).getFirstName() + ", " +
                                       " Rank: " + memberDatabase.get(i).getRank();
                            }
                            //Set the state of the program after member info update is finished.
                            playerJList.setListData(memberList);
                            playerJList.setSelectedIndex(index);
                            updateJButton.setEnabled(false);
                            addJButton.setEnabled(true);
                            editJButton.setEnabled(true);
                            deleteJButton.setEnabled(true);
                            searchJButton.setEnabled(true);
                            updateJMenuItem.setEnabled(false);
                            addJMenuItem.setEnabled(true);
                            editJMenuItem.setEnabled(true);
                            deleteJMenuItem.setEnabled(true);
                            searchJMenuItem.setEnabled(true);
                            playerJList.setEnabled(true);
                            disableEntryFields();
                            disableEditing();
                            }
                       }
                    } 
                }
            } 
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, "Enter valid numbers from 1 to 9 for rank.", "Invalid Entry!!",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_updateJButtonActionPerformed

    /**
     * Invokes what the updateJButton does.
     * @param evt updateJMenuItemActionPerformed
     * Author Ryu Muthui
     */
    private void updateJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateJMenuItemActionPerformed
        updateJButton.doClick();
    }//GEN-LAST:event_updateJMenuItemActionPerformed

    /**
     * Allows the user to cancel the editing process and resets the state of the program
     * back to before the user chose to select to update information.
     * @param evt cancelEditJMenuItemActionPerformed
     * Author Ryu Muthui
     */
    private void cancelEditJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelEditJMenuItemActionPerformed

        disableEntryFields();
        disableEditing();
        displayMembers(); 
        
        playerJList.setEnabled(true);
        deleteJButton.setEnabled(true);
        searchJButton.setEnabled(true);
        editJButton.setEnabled(true);
        deleteJMenuItem.setEnabled(true);
        searchJMenuItem.setEnabled(true);
        editJMenuItem.setEnabled(true);
        updateJMenuItem.setEnabled(false);
        updateJButton.setEnabled(false);
    }//GEN-LAST:event_cancelEditJMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GolfDataBaseGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GolfDataBaseGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GolfDataBaseGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GolfDataBaseGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GolfDataBaseGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.JMenu actionJMenu;
    private javax.swing.JButton addJButton;
    private javax.swing.JMenuItem addJMenuItem;
    private javax.swing.JMenuItem cancelEditJMenuItem;
    private javax.swing.JMenuItem clearJMenuItem;
    private javax.swing.JPopupMenu.Separator clearJSeparator;
    private javax.swing.JPanel controlJPanel;
    private javax.swing.JButton deleteJButton;
    private javax.swing.JMenuItem deleteJMenuItem;
    private javax.swing.JButton editJButton;
    private javax.swing.JMenuItem editJMenuItem;
    private javax.swing.JLabel emailJLabel;
    private javax.swing.JTextField emailJTextField;
    private javax.swing.JButton exitJButton;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JMenuBar fileJMenuBar;
    private javax.swing.JLabel firstNameJLabel;
    private javax.swing.JTextField firstNameJTextField;
    private javax.swing.JLabel golfImageJLabel;
    private javax.swing.JMenu helpJMenu;
    private javax.swing.JPanel informationJPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lastNameJLabel;
    private javax.swing.JTextField lastNameJTextField;
    private javax.swing.JMenuItem openNewJMenuItem;
    private javax.swing.JPopupMenu.Separator openNewJSeparator;
    private javax.swing.JFormattedTextField phoneNumberJFormattedTextField;
    private javax.swing.JLabel phoneNumberJLabel;
    private javax.swing.JList playerJList;
    private javax.swing.JPanel playersJPanel;
    private javax.swing.JMenuItem printJMenuItem;
    private javax.swing.JPopupMenu.Separator printJSeparator;
    private javax.swing.JFormattedTextField rankJFormattedTextField;
    private javax.swing.JLabel rankJLabel;
    private javax.swing.JMenuItem saveAsJMenuItem;
    private javax.swing.JMenuItem saveJMenuItem;
    private javax.swing.JPopupMenu.Separator saveJSeparator;
    private javax.swing.JButton searchJButton;
    private javax.swing.JMenuItem searchJMenuItem;
    private javax.swing.JRadioButtonMenuItem sortByLastNameJRadioButtonMenuItem;
    private javax.swing.JMenu sortJMenu;
    private javax.swing.JRadioButtonMenuItem sortbyRankJRadioButtonMenuItem;
    private javax.swing.ButtonGroup sortingJButtonGroup;
    private javax.swing.JLabel titleImageJLabel;
    private javax.swing.JLabel titleJLabel;
    private javax.swing.JPanel titleJPanel;
    private javax.swing.JButton updateJButton;
    private javax.swing.JMenuItem updateJMenuItem;
    // End of variables declaration//GEN-END:variables
}
