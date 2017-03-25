
package PrintQueueSimulator;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Class:	<b>PrinterSimGUI</b>
*  File:	PrinterSimGUI.java
* <pre>
*  Description:	This is a program is designed to simulate a print queue. It allows
*               the user to enter the duration of the simulation, mean inter arrival
*               time and number of servers. With this information, it will generate
*               random print jobs with random lengths, add them to a queue. From the
*               queue it is passed to the next available printer and processed until
*               the simulation ends. The statistics and print simulation is then 
*               displayed for the user to see.
*  @author:	<i>Ryu Muthui and Siqi Zhang</i>
*  Environment:	PC, Windows 7, jdk1.7_17, NetBeans 7.3
*  Date:	6/07/2013
*  Hours:       30 hours
*  @version	1.01
*  @see         java.awt.Toolkit;
*  @see         java.util.ArrayList;
*  @see         java.util.logging.Level;
*  @see         java.util.logging.Logger;
* </pre>
*  History Log:	Started 5/20/2013, Completed 6/07/2013.
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class PrinterSimGUI extends javax.swing.JFrame 
{

    //Instance variables
    public static int jobCounter = 0;
    static Queue queue  = new Queue();
    public static int timer = 0;
    public static final int SECONDS_IN_MINUTE = 60;
    public static int totalInPrinter= 0,
                      totalInQueue= 0,
                      totalFinished = 0;
    
    static ArrayList<Client> queueList = new ArrayList<Client>();
    
    static StringBuffer myBuffer = new StringBuffer();
    
    
    /**
     * Creates new form PrinterSimGUI
     */
    public PrinterSimGUI() 
    {   
        initComponents();
        //Sets the form to display at the center.
        setLocationRelativeTo(null);
        
        //Sets icon image on form.
        this.setIconImage(Toolkit.getDefaultToolkit()
                .getImage("src\\Resources\\Print.png"));
        //set the statistics area invisiable
        totaltimeallJobsJLabel.setVisible(false);
        totalJobTimeJLabel.setVisible(false);
        totalTimeInQueueJLabel.setVisible(false);
        totalQueueTimeJLabel.setVisible(false);
        totalCompletedJLabel.setVisible(false);
        totalJobJLabel.setVisible(false);     
    }

    
    /**
     * Method: sortByTime-
     * This method sorts the arrayList by calling the timeServiceEnds method from
     * the Server Class.
     * @param server 
     * Author: Siqi Zhang
     */
    private static void sortByTime(ArrayList<Server> server)
    {
        for(int i = 0; i < server.size()-1;i++)
        {
            for(int j = 0;j < server.size()-i;j++)
            {   
                if(server.get(i).timeServiceEnds > server.get(i+1).timeServiceEnds)
                {
                   swap(server,server.get(i),server.get(i+1));
                }
                j++;
            }       
        }
    }
    
    /**
     * Method: swap-
     * This method swaps the listed 2 elements in the arrayList.
     * @param serverList
     * @param server1
     * @param server2 
     * Author: Siqi Zhang
     */
    private static void swap(ArrayList<Server> serverList, Server server1,Server server2)
    {
        Server temp;
        temp = server1;
        serverList.set(serverList.indexOf(server1), server2);
        serverList.set(serverList.indexOf(server2), server1);
    }
    

    /**
     * Method: nextAvailable -
     * This method gets the next available printer and return the index of it.
     * It then checks which printer is available.
     * @param server
     * @return the next available printer
     * Author Siqi Zhang
     */
    private static Server nextAvailable(ArrayList<Server> server)
    {   
        ArrayList<Server> freePrinter = new ArrayList<Server>();
        //check to see if there are any available printers
        for(int i=0; i <server.size();i++)
        {
           if(server.get(i).isFree())
           freePrinter.add(server.get(i));
        }     
        return freePrinter.get(0);
    }
    
     /**
     * Method: available- 
     * This method checks the printer arrayList to see if there is any free printers.
     * @param server
     * @return true or false depending on if there is any free printer.
     * Author Siqi Zhang
     */
    private static boolean available(ArrayList<Server> server)
    {   
        int freeCounter=0;
        for(int i=0; i <server.size();i++)
        {
            if(server.get(i).isFree())
            freeCounter++;     
        }
        if(freeCounter == 0)
            return false;
        else
            return true;
    }
    
    
    /**
     * Method: Simulation-
     * This method simulates the printer simulation of this project. First it gathers
     * the data specified by the user. It passes those data accordingly. It generates
     * print jobs and passes those jobs to the jobList Array List. From there they are passed
     * to the Server. The time associated with each job is recorded and sent out to 
     * print methods to be displayed and tracked then they are shown to the user
     * in attempts to simulate actual printing jobs.
     * @param total
     * @param avg
     * @param printerArrayList
     * @throws InterruptedException 
     */
    private static void simulation(int total,int avg,ArrayList<Server>  printerArrayList) 
            throws InterruptedException
    {         
        ArrayList<Client> jobList = new ArrayList<Client>(); 
        int actualInterarrival;
        int timer = 0;
        int jobId = 1;
        Server current;
        Client dequeuedJob = null;
        
        //The following loop will generate the random jobs and assign them to the arrayList
        do 
        { 
            Client job = new Client(timer);
            job.setId(jobId);
            jobList.add(job);
            Random randomNumber = new Random(avg);
            actualInterarrival = randomNumber.intNextExponential();
            timer +=actualInterarrival;
            jobId ++;
        }  while(timer < total * SECONDS_IN_MINUTE);
        
        //Assigning a "tracker" job which will have the time when the simulation ends.
        Client job = new Client(total * SECONDS_IN_MINUTE);
        jobList.add(job);
        
        //The following loop will do print out all the events that will happen before the simulation ends.
        for(int i = 0; i < jobList.size()-1; i++)
        {
             //Print the job status.
             myBuffer.append("Job #" + jobList.get(i).getId() + " arrives at time " 
                     + jobList.get(i).getArrivalTime() + " with "+ jobList.get(i).getJobSize() +
                     " pages." + "\n");
         
                 //try to assign the job to the print, if not, enqueue the job to the queue. 
                 if(available(printerArrayList) == false)
                 {  
                     queue.enqueue(jobList.get(i));
                     queueList.add(jobList.get(i));
                     myBuffer.append("Job #" + jobList.get(i).getId() + " enters the queue at " +
                             jobList.get(i).getArrivalTime() + " seconds" + "\n");
                 } 
                 // else assign the job to the free printer.
                 else
                 { 
                       current = nextAvailable(printerArrayList);
                       current.beginServing(jobList.get(i), jobList.get(i).getArrivalTime());
                       myBuffer.append("Printer " + current.getId() + "(" + 
                               current.getMeanServiceRate() + "%," + current.getServiceRate() +
                               "%)" + " begins Job #" + jobList.get(i).getId() + " at time " +
                               jobList.get(i).getArrivalTime() + " seconds." + "\n" );
                 }   
         
                 //check the current queue
                 if(queue.isEmpty())
                 {
                     myBuffer.append("The queue is currently empty." + "\n" + "\n");  
                 }
                 else
                 {   
                     String temp = "";
                     for(int j=0; j<queueList.size();j++)
                     {
                         temp += "#"+queueList.get(j).getId()+"("+queueList.get(j).getJobSize() + "),  ";
                     }
                     myBuffer.append("The queue currently contains job:[ " + temp 
                              + " ]" + "\n" + "\n");
                 }
                 //Check the printers that are not free and assign to the busyPrinter arrayList.
                 ArrayList<Server> busyPrinter = new ArrayList<Server>();
                 for(int k = 0; k < printerArrayList.size(); k++)
                 {
                     if(!printerArrayList.get(k).isFree())
                         busyPrinter.add(printerArrayList.get(k));
                 }    
                 //sort the  busyPrinter arrayList by the current job ends time 
                 sortByTime(busyPrinter);

                 //Add the busytime printer array to a temp array and compare the arrival times to the end time.
                 ArrayList<Server> printerTemp = new ArrayList<Server>();
                 for(int j = 0; j < busyPrinter.size(); j++)
                 {
                     if(busyPrinter.get(j).timeServiceEnds >= jobList.get(i).getArrivalTime() &&
                             busyPrinter.get(j).timeServiceEnds <= jobList.get(i+1).getArrivalTime())
                     {
                         printerTemp.add(busyPrinter.get(j));
                     }
                 }

                 if(busyPrinter.size() == printerArrayList.size())
                 {    
                    if((!printerTemp.isEmpty()) && (!queue.isEmpty()))
                    {
                        for(int k = 0; k < printerTemp.size(); k++)
                        {    
                             int temp = 0;
                             myBuffer.append("Printer " + printerTemp.get(k).getId() +
                                     "(" + printerTemp.get(k).getMeanServiceRate() + "%," +
                                     printerTemp.get(k).getServiceRate() + "%)" + " ends Job #" +
                                     printerTemp.get(k).getClient().getId() + " at time " +
                                     printerTemp.get(k).timeServiceEnds + " seconds." + "\n");
                             totalFinished++;
                             totalInPrinter+= printerTemp.get(k).serviceTime;
                             totalInQueue+=  printerTemp.get(k).getTimeServiceStarts() - printerTemp.get(k).getClient().getArrivalTime();
                             
                             temp = printerTemp.get(k).timeServiceEnds;
                             try
                             {
                                dequeuedJob =(Client)queue.dequeue();
                                queueList.remove(0);
                             }
                             catch(EmptyListException e){}
                             printerTemp.get(k).beginServing(dequeuedJob, printerTemp.get(k).timeServiceEnds);
                             myBuffer.append("Printer " +  printerTemp.get(k).getId() + "(" +  
                                     printerTemp.get(k).getMeanServiceRate() + "%," +
                                     printerTemp.get(k).getServiceRate() + "%)" + " begins Job #" +
                                     dequeuedJob.getId() + " at time " + temp + " seconds." + "\n");
                        }
                    }
                 }
                 else
                 {
                  if(!printerTemp.isEmpty())
                    {
                        for(int k = 0; k < printerTemp.size(); k++)
                        {    
                             myBuffer.append("Printer " + printerTemp.get(k).getId() + "(" +
                                     printerTemp.get(k).getMeanServiceRate() + "%," +
                                     printerTemp.get(k).getServiceRate()+"%)"+" ends Job #" +
                                     printerTemp.get(k).getClient().getId() + " at time" +
                                     printerTemp.get(k).timeServiceEnds + "seconds." + "\n");
                             totalFinished++;
                             totalInPrinter+= printerTemp.get(k).serviceTime;
                             totalInQueue+=  printerTemp.get(k).getTimeServiceStarts() - printerTemp.get(k).getClient().getArrivalTime();
                         }
                    }  
                 }
           }
        myBuffer.append("The simulation is now complete." + "\n");   
    } 
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        simControlJLabel = new javax.swing.JLabel();
        simTimeJLabel = new javax.swing.JLabel();
        numPrintersJLabel = new javax.swing.JLabel();
        meanJobTimeJLabel = new javax.swing.JLabel();
        simTimeJSpinner = new javax.swing.JSpinner();
        numOfPrinterJSpinner = new javax.swing.JSpinner();
        meanInterArrivalJSpinner = new javax.swing.JSpinner();
        startSimJButton = new javax.swing.JButton();
        exitJLabel = new javax.swing.JLabel();
        displayJPanel = new javax.swing.JPanel();
        displayJScrollPane = new javax.swing.JScrollPane();
        displayJTextArea = new javax.swing.JTextArea();
        resetJButton = new javax.swing.JButton();
        totaltimeallJobsJLabel = new javax.swing.JLabel();
        totalJobTimeJLabel = new javax.swing.JLabel();
        totalTimeInQueueJLabel = new javax.swing.JLabel();
        totalQueueTimeJLabel = new javax.swing.JLabel();
        totalCompletedJLabel = new javax.swing.JLabel();
        totalJobJLabel = new javax.swing.JLabel();
        printerBackGroundJLabel = new javax.swing.JLabel();
        exitJButton = new javax.swing.JButton();
        fileJMenuBar = new javax.swing.JMenuBar();
        fileJMenu = new javax.swing.JMenu();
        startSimJMenuItem = new javax.swing.JMenuItem();
        resetJMenuItem = new javax.swing.JMenuItem();
        printJMenuItem = new javax.swing.JMenuItem();
        exitJMenuItem = new javax.swing.JMenuItem();
        helpJMenu = new javax.swing.JMenu();
        aboutJMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Printer Queue Simulator");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(800, 800));
        setPreferredSize(new java.awt.Dimension(800, 800));
        setResizable(false);
        getContentPane().setLayout(null);

        simControlJLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        simControlJLabel.setText("Simulation Controls:");
        getContentPane().add(simControlJLabel);
        simControlJLabel.setBounds(320, 30, 140, 34);

        simTimeJLabel.setText("Select simulation time (minutes):");
        getContentPane().add(simTimeJLabel);
        simTimeJLabel.setBounds(310, 80, 200, 26);

        numPrintersJLabel.setText("Select number of printer to be used:");
        getContentPane().add(numPrintersJLabel);
        numPrintersJLabel.setBounds(290, 130, 210, 30);

        meanJobTimeJLabel.setText("Select mean inter-arrival time of print jobs (seconds):");
        getContentPane().add(meanJobTimeJLabel);
        meanJobTimeJLabel.setBounds(250, 190, 300, 20);

        simTimeJSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 60, 1));
        simTimeJSpinner.setToolTipText("Select duration of simulation in minutes (1-60).");
        getContentPane().add(simTimeJSpinner);
        simTimeJSpinner.setBounds(350, 110, 50, 30);

        numOfPrinterJSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 8, 1));
        numOfPrinterJSpinner.setToolTipText("Select Number of Printers to be used (1-8).");
        getContentPane().add(numOfPrinterJSpinner);
        numOfPrinterJSpinner.setBounds(350, 160, 50, 30);

        meanInterArrivalJSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 59, 1));
        meanInterArrivalJSpinner.setToolTipText("Select themean inter-arrival time in seconds (1-59).");
        getContentPane().add(meanInterArrivalJSpinner);
        meanInterArrivalJSpinner.setBounds(350, 210, 50, 30);

        startSimJButton.setText("Start Simulation");
        startSimJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startSimJButtonActionPerformed(evt);
            }
        });
        getContentPane().add(startSimJButton);
        startSimJButton.setBounds(330, 250, 130, 33);

        exitJLabel.setText("Exit Simulation");
        getContentPane().add(exitJLabel);
        exitJLabel.setBounds(630, 300, 120, 14);

        displayJPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        displayJPanel.setToolTipText("Simulation is displayed here.");

        displayJTextArea.setEditable(false);
        displayJTextArea.setColumns(20);
        displayJTextArea.setRows(5);
        displayJScrollPane.setViewportView(displayJTextArea);

        javax.swing.GroupLayout displayJPanelLayout = new javax.swing.GroupLayout(displayJPanel);
        displayJPanel.setLayout(displayJPanelLayout);
        displayJPanelLayout.setHorizontalGroup(
            displayJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayJScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
        );
        displayJPanelLayout.setVerticalGroup(
            displayJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayJScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );

        getContentPane().add(displayJPanel);
        displayJPanel.setBounds(214, 550, 360, 170);

        resetJButton.setText("Reset Simulation");
        resetJButton.setToolTipText("Select to reset the simulation back to default set.");
        resetJButton.setEnabled(false);
        resetJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetJButtonActionPerformed(evt);
            }
        });
        getContentPane().add(resetJButton);
        resetJButton.setBounds(60, 330, 130, 30);

        totaltimeallJobsJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totaltimeallJobsJLabel.setText("Total time spent by all jobs in the system:");
        getContentPane().add(totaltimeallJobsJLabel);
        totaltimeallJobsJLabel.setBounds(270, 300, 250, 14);
        getContentPane().add(totalJobTimeJLabel);
        totalJobTimeJLabel.setBounds(360, 320, 60, 30);

        totalTimeInQueueJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalTimeInQueueJLabel.setText("Total time spent by all jobs in the queue:");
        getContentPane().add(totalTimeInQueueJLabel);
        totalTimeInQueueJLabel.setBounds(270, 360, 250, 20);
        getContentPane().add(totalQueueTimeJLabel);
        totalQueueTimeJLabel.setBounds(360, 380, 60, 30);

        totalCompletedJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalCompletedJLabel.setText("Total number of jobs completed:");
        getContentPane().add(totalCompletedJLabel);
        totalCompletedJLabel.setBounds(290, 420, 210, 14);
        getContentPane().add(totalJobJLabel);
        totalJobJLabel.setBounds(360, 440, 60, 30);

        printerBackGroundJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/PrinterBG.png"))); // NOI18N
        getContentPane().add(printerBackGroundJLabel);
        printerBackGroundJLabel.setBounds(0, 0, 780, 730);

        exitJButton.setText("Exit");
        exitJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJButtonActionPerformed(evt);
            }
        });
        getContentPane().add(exitJButton);
        exitJButton.setBounds(630, 313, 51, 40);

        fileJMenu.setText("File");

        startSimJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Simulation.png"))); // NOI18N
        startSimJMenuItem.setMnemonic('S');
        startSimJMenuItem.setText("Start Simulation");
        startSimJMenuItem.setToolTipText("Select to begin simulation");
        startSimJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startSimJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(startSimJMenuItem);

        resetJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Reset.png"))); // NOI18N
        resetJMenuItem.setMnemonic('R');
        resetJMenuItem.setText("Reset Simulation");
        resetJMenuItem.setToolTipText("Select to reset the simulation.");
        resetJMenuItem.setEnabled(false);
        resetJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(resetJMenuItem);

        printJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Print.png"))); // NOI18N
        printJMenuItem.setMnemonic('P');
        printJMenuItem.setText("Print");
        printJMenuItem.setToolTipText("select to print the simulation.");
        printJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(printJMenuItem);

        exitJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Exit.png"))); // NOI18N
        exitJMenuItem.setMnemonic('E');
        exitJMenuItem.setText("Exit");
        exitJMenuItem.setToolTipText("Select to exit the simulation");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        fileJMenu.add(exitJMenuItem);

        fileJMenuBar.add(fileJMenu);

        helpJMenu.setText("Help");
        helpJMenu.setToolTipText("");

        aboutJMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/About.png"))); // NOI18N
        aboutJMenuItem.setText("About");
        aboutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJMenuItemActionPerformed(evt);
            }
        });
        helpJMenu.add(aboutJMenuItem);

        fileJMenuBar.add(helpJMenu);

        setJMenuBar(fileJMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exits the program when selected
     * @param evt 
     */
    private void exitJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJButtonActionPerformed
       System.exit(0);
    }//GEN-LAST:event_exitJButtonActionPerformed

    /**
     * Begins the simulation when selected by calling the simulation method.
     * @param evt 
     */
    private void startSimJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startSimJButtonActionPerformed
        //enable the reset button and disable the start button
        startSimJButton.setEnabled(false);
        startSimJMenuItem.setEnabled(false);
        resetJButton.setEnabled(true);
        resetJMenuItem.setEnabled(true);
        //diable those JSpinners
        simTimeJSpinner.setEnabled(false);
        numOfPrinterJSpinner.setEnabled(false);
        meanInterArrivalJSpinner.setEnabled(false);
        
        int numPrinters, totalTime, avgArrivals;
        char ID = 'A';
        
        totalTime = (int)simTimeJSpinner.getValue();
        numPrinters = (int)numOfPrinterJSpinner.getValue();
        avgArrivals = (int)meanInterArrivalJSpinner.getValue();
        
        //Create an arraylist that contains all the available printers and their information.  
        ArrayList<Server> printerArrayList = new ArrayList<Server>(); 
        for(int i = 0; i< numPrinters;i++)
        {     
             Server printer = new Server();         
             printer.setId(ID);
             printerArrayList.add(printer);
             ID++;
        }
        try {
            //run the simulation
            simulation(totalTime,avgArrivals,printerArrayList);
        } catch (InterruptedException ex) 
        {
            Logger.getLogger(PrinterSimGUI.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //set the statistics area visible and display the values
        totaltimeallJobsJLabel.setVisible(true);
        totalJobTimeJLabel.setVisible(true);
        totalTimeInQueueJLabel.setVisible(true);
        totalQueueTimeJLabel.setVisible(true);
        totalCompletedJLabel.setVisible(true);
        totalJobJLabel.setVisible(true);
        
        totalJobTimeJLabel.setText(String.valueOf(totalInPrinter));
        totalQueueTimeJLabel.setText(String.valueOf(totalInQueue));
        totalJobJLabel.setText(String.valueOf(totalFinished));   
        
        
        displayJTextArea.setText(myBuffer.toString());
    }//GEN-LAST:event_startSimJButtonActionPerformed

    /**
     * Resets the buttons and text area when selected.
     * @param evt 
     */
    private void resetJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetJButtonActionPerformed
        //set statistics area invisiable
        totaltimeallJobsJLabel.setVisible(false);
        totalJobTimeJLabel.setVisible(false);
        totalTimeInQueueJLabel.setVisible(false);
        totalQueueTimeJLabel.setVisible(false);
        totalCompletedJLabel.setVisible(false);
        totalJobJLabel.setVisible(false);   
        //enable the start button and disable the reset button
        startSimJButton.setEnabled(true);
        startSimJMenuItem.setEnabled(true);
        resetJButton.setEnabled(false);
        resetJMenuItem.setEnabled(false);
        //enable the JSpinner and set their value to 1
        simTimeJSpinner.setEnabled(true);
        simTimeJSpinner.setValue(1);
        numOfPrinterJSpinner.setEnabled(true);
        numOfPrinterJSpinner.setValue(1);
        meanInterArrivalJSpinner.setEnabled(true);
        meanInterArrivalJSpinner.setValue(1);
        //reset the textarea and public variables
        displayJTextArea.setText("");
        jobCounter = 0;
        timer = 0;
        totalInPrinter = 0;
        totalInQueue = 0;
        totalFinished = 0;
        Queue queue  = new Queue();
    }//GEN-LAST:event_resetJButtonActionPerformed

    private void exitJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitJMenuItemActionPerformed

    /**
     * Display the About Form when selected.
     * @param evt aboutJMenuItemActionPerformed
     */
    private void aboutJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutJMenuItemActionPerformed
        AboutForm myAbout = new AboutForm();
        myAbout.setVisible(true);
    }//GEN-LAST:event_aboutJMenuItemActionPerformed

    /**
     * Calls the Print Utilities class to print out the entire GUI.
     * Author Ryu Muthui
     * @param evt Menu Action Event.
     * @see PrintUtilities Class.
     */
    private void printJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJMenuItemActionPerformed

        try{PrintUtilities.printComponent(displayJTextArea);}
        catch(Exception e){} //Exception when printer is not found.
    }//GEN-LAST:event_printJMenuItemActionPerformed

    /**
     * Invokes the startSimJButton action event.
     * @param evt 
     */
    private void startSimJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startSimJMenuItemActionPerformed
        startSimJButton.doClick();
    }//GEN-LAST:event_startSimJMenuItemActionPerformed

    /**
     * Invokes the resetJButton action event.
     * @param evt 
     */
    private void resetJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetJMenuItemActionPerformed
        resetJButton.doClick();
    }//GEN-LAST:event_resetJMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) 
    {
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
            java.util.logging.Logger.getLogger(PrinterSimGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrinterSimGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrinterSimGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrinterSimGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrinterSimGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.JPanel displayJPanel;
    private javax.swing.JScrollPane displayJScrollPane;
    private javax.swing.JTextArea displayJTextArea;
    private javax.swing.JButton exitJButton;
    private javax.swing.JLabel exitJLabel;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JMenu fileJMenu;
    private javax.swing.JMenuBar fileJMenuBar;
    private javax.swing.JMenu helpJMenu;
    private javax.swing.JSpinner meanInterArrivalJSpinner;
    private javax.swing.JLabel meanJobTimeJLabel;
    private javax.swing.JSpinner numOfPrinterJSpinner;
    private javax.swing.JLabel numPrintersJLabel;
    private javax.swing.JMenuItem printJMenuItem;
    private javax.swing.JLabel printerBackGroundJLabel;
    private javax.swing.JButton resetJButton;
    private javax.swing.JMenuItem resetJMenuItem;
    private javax.swing.JLabel simControlJLabel;
    private javax.swing.JLabel simTimeJLabel;
    private javax.swing.JSpinner simTimeJSpinner;
    private javax.swing.JButton startSimJButton;
    private javax.swing.JMenuItem startSimJMenuItem;
    private javax.swing.JLabel totalCompletedJLabel;
    private javax.swing.JLabel totalJobJLabel;
    private javax.swing.JLabel totalJobTimeJLabel;
    private javax.swing.JLabel totalQueueTimeJLabel;
    private javax.swing.JLabel totalTimeInQueueJLabel;
    private javax.swing.JLabel totaltimeallJobsJLabel;
    // End of variables declaration//GEN-END:variables
}
