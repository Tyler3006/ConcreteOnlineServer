package concreteonlineserver;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Tyler Costa
 */
public class MainDisplay extends javax.swing.JFrame implements Runnable {

    String orderNumber, contactName, address, phone, mixDesign, amountConcrete, timeSelect, message, terms, dateStamp, timeStamp, status;
    String[] ISO_Strings = new String[15];
    DefaultTableModel tblModel;
    int dataIndex;
    int DB_rowCount;
    String filePath;
    String outputString;
    int fetchedRows, fileCount, rowReCount;
    Thread dataBaseFetcherThread = new Thread(this);
    Connection connection;

    /**
     * Creates new form MainDisplay
     */
    public MainDisplay() throws SQLException, IOException {
        initComponents();
        //initialFetchData();
        //tableDataLoader();
        dataBaseFetcher();

        jobInfoPanel.setVisible(false);

        //     jTable1.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> ColSort = new TableRowSorter<>(jTable1.getModel());
        jTable1.setRowSorter(ColSort);
        List<RowSorter.SortKey> ColSortingKeys = new ArrayList<>();
        //"SortColNo" is use to set Column No for Sorting
        int SortColNo = 10;
        //"SortKey" is used for sort order for a particular column
        ColSortingKeys.add(new RowSorter.SortKey(SortColNo, SortOrder.ASCENDING));

        ColSort.setSortKeys(ColSortingKeys);
        ColSort.sort();

    }

    private String stringBuilderMethod(String filePath) {

        // Declaring object of StringBuilder class
        StringBuilder builder = new StringBuilder();

        // try block to check for exceptions where
        // object of BufferedReader class us created
        // to read filepath
        try ( BufferedReader buffer = new BufferedReader(
                new FileReader(filePath))) {

            String str;

            // Condition check via buffer.readLine() method
            // holding true upto that the while loop runs
            while ((str = buffer.readLine()) != null) {

                builder.append(str).append("\n");
            }
        } // Catch block to handle the exceptions
        catch (IOException e) {

            // Print the line number here exception occurred
            // using printStackTrace() method
            e.printStackTrace();
        }

        //outputString = builder.toString();
        // Returning a string
        return builder.toString();
    }

    public void dataBaseFetcher() throws IOException {
        dataBaseFetcherThread.start();
        //fs = new FileSender(fileName);

    }

    public void tableDataLoader() {

        File file = new File("jobs/");
        File[] files = file.listFiles();

        //System.out.println("Files are:");
        // Display the names of the files
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();

            // Custom input file path stored in string type
            filePath = "jobs/" + name;
            fileCount++;
            // Calling the Method 1 to
            // read file to a string
            //System.out.println(stringBuilderMethod(filePath));
            String builtString = stringBuilderMethod(filePath);
            //System.out.println("CHECKING THE STRING: "+builtString);

            //At this point, the file has been read into a single string that includes all line breaks
            //Next the string needs to be split back into each appropriate variable
            stringSplitter(builtString);

        }

    }

    public void stringSplitter(String builtString) {
        //String newString = builtString;
        String[] arrOfStr = builtString.split("\\|");
        // System.out.println("Cleaned String: ");

        for (int j = 0; j < arrOfStr.length; j++) {
            //System.out.println(ISO_Strings[i]);
            ISO_Strings[j] = arrOfStr[j];
        }
        //System.out.println("Array: " + ISO_Strings[11]);

//        for(int k = 0; k< arrOfStr.length; k ++){
//            System.out.print(ISO_Strings[k]);
//           
//        }
        addData();
    }

    public void packageJob() {
        DataPackager dp = new DataPackager(orderNumber, contactName, address, phone, mixDesign, amountConcrete, timeSelect, message, terms, dateStamp, timeStamp, status);

    }

    public void initialFetchData() throws SQLException {

        // TODO code application logic here
        ConnectionClass connectionClass = new ConnectionClass();
        connection = connectionClass.getConnection();

        //String sql = "INSERT INTO user VALUES ('Jon'); ";
        Statement statement = connection.createStatement();
        //statement.executeUpdate(sql);
        //System.out.println("data entered");

        String sqlRead = "SELECT * FROM ordertablenew";
        ResultSet rs = statement.executeQuery(sqlRead);

        while (rs.next()) {
            orderNumber = rs.getString("orderNumber");
            contactName = rs.getString("name");

            phone = rs.getString("phoneNumber");
            address = rs.getString("address");
            mixDesign = rs.getString("mixDesign");
            amountConcrete = rs.getString("amountConcrete");
            timeSelect = rs.getString("timeSelect");
            message = rs.getString("body");
            terms = rs.getString("terms");
            dateStamp = rs.getString("dateStamp");
            timeStamp = rs.getString("timeStamp");
            status = "processed";
            //System.out.println("\n\n*-------------------------------------------------------------------------*");
            //orderNumber, user, address, phone, mixDesign, amountConcrete, timeSelect, body (message), terms, dateStamp, timeStamp
//            System.out.println("\nUNCLEANED DATA From database: \n"
//                    + " OrderNo: " + orderNumber
//                    + ", \nUser: " + contactName
//                    + ", \nAddress: " + address
//                    + ", \nPhone: " + phone
//                    + ", \nMix Design: " + mixDesign
//                    + ", \nKgs per sqm: " + amountConcrete
//                    + ", \nTime selected: " + timeSelect
//                    + ", \nExtra information: " + message
//                    + ", \nAgreed to T&Cs: " + terms
//                    + ", \nDate ordered: " + dateStamp
//                    + ", \nTime ordered: " + timeStamp
//                    + ", \nStatus: " + status);
//System.out.println(rs.getString(1));
            fetchedRows++;
            cleanData();

//addData();
            packageJob();
        }

    }

    public void cleanData() {
        cleanMixDesign();
        cleanPreferredDelivery();
        cleanAcceptedTerms();
    }

    public void addData() {
        //This code currently Fetches data loaded from database, needs to fetch data from proccessed files
        String data[] = {ISO_Strings[0], ISO_Strings[1], ISO_Strings[2], ISO_Strings[3], ISO_Strings[4], ISO_Strings[5], ISO_Strings[6], ISO_Strings[7], ISO_Strings[8], ISO_Strings[9], ISO_Strings[10], ISO_Strings[11]};
        tblModel = (DefaultTableModel) jTable1.getModel();
        //jTable1.getSelectedRow();

        tblModel.addRow(data);

        //System.out.println("\n\nTESTING :: "+data[10]);   
    }

    public void infoPanelHandler() {
        jobInfoArea.setEditable(false);
        if (jobInfoPanel.isVisible()) {
            System.out.println("Its visible!");

        } else {
            System.out.println("Its not visible!");
        }

        String information;

        infoPanelLabel.setText("Information for Job no: " + tblModel.getValueAt(dataIndex, 0).toString());
        infoPanelTimeStamp.setText("Orded at: " + tblModel.getValueAt(dataIndex, 10).toString());
        information = "Order Number: \n" + tblModel.getValueAt(dataIndex, 0).toString()
                + "\n\nStatus: " + tblModel.getValueAt(dataIndex, 11).toString()
                + "\n\nContact Name: " + tblModel.getValueAt(dataIndex, 1).toString()
                + "\n\nContact Phone: " + tblModel.getValueAt(dataIndex, 2).toString()
                + "\n\nDelivery Address: " + tblModel.getValueAt(dataIndex, 3).toString()
                + "\n\nMix Design: " + tblModel.getValueAt(dataIndex, 4).toString()
                + "\n\nKgs per sqm: " + tblModel.getValueAt(dataIndex, 5).toString()
                + "\n\nPreferred Delivery: " + tblModel.getValueAt(dataIndex, 6).toString()
                + "\n\n----------------------------------------"
                + "\n\nAdditional Information: \n" + tblModel.getValueAt(dataIndex, 7).toString()
                + "\n\n----------------------------------------"
                + "\n\nAccepted Terms: " + tblModel.getValueAt(dataIndex, 8).toString()
                + "\n\nDate Stamp: " + tblModel.getValueAt(dataIndex, 9).toString()
                + "\n\nTime Stamp: " + tblModel.getValueAt(dataIndex, 10).toString();

        jobInfoArea.setText(information);

    }

    // private void jTable1.
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        sendFileButton = new javax.swing.JButton();
        RemoveRowButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        fetchedRowsLabel = new javax.swing.JLabel();
        filesCreatedLabel = new javax.swing.JLabel();
        serverTickedLabel = new javax.swing.JLabel();
        numOfRowsDBLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jobInfoPanel = new javax.swing.JPanel();
        infoPanelLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jobInfoArea = new javax.swing.JTextArea();
        closeButton = new javax.swing.JButton();
        infoPanelTimeStamp = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        sendFileButton.setText("Send file");
        sendFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFileButtonActionPerformed(evt);
            }
        });

        RemoveRowButton.setText("Rmv Row");
        RemoveRowButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RemoveRowButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(sendFileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RemoveRowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(sendFileButton)
                .addGap(18, 18, 18)
                .addComponent(RemoveRowButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        fetchedRowsLabel.setText(" ");

        filesCreatedLabel.setText(" ");

        serverTickedLabel.setText(" ");

        numOfRowsDBLabel.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(numOfRowsDBLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                    .addComponent(filesCreatedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fetchedRowsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(serverTickedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fetchedRowsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filesCreatedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numOfRowsDBLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(serverTickedLabel)
                .addGap(23, 23, 23))
        );

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "OrderNumber", "Contact Name", "Contact Phone", "Delivery Address", "Mix Design", "kgs per sqm", "Preffered Delivery Time", "Additional Information", "Accepted Terms", "Date Stamp", "Time Stamp", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
            jTable1.getColumnModel().getColumn(8).setResizable(false);
            jTable1.getColumnModel().getColumn(9).setResizable(false);
            jTable1.getColumnModel().getColumn(10).setResizable(false);
            jTable1.getColumnModel().getColumn(11).setResizable(false);
        }

        jobInfoPanel.setBackground(new java.awt.Color(204, 204, 204));
        jobInfoPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jobInfoPanel.setForeground(new java.awt.Color(255, 255, 255));

        infoPanelLabel.setText("Info: ");

        jobInfoArea.setColumns(20);
        jobInfoArea.setRows(5);
        jScrollPane1.setViewportView(jobInfoArea);

        closeButton.setText("X");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        infoPanelTimeStamp.setFont(new java.awt.Font("Segoe UI Light", 0, 10)); // NOI18N
        infoPanelTimeStamp.setText("timeStamp");

        javax.swing.GroupLayout jobInfoPanelLayout = new javax.swing.GroupLayout(jobInfoPanel);
        jobInfoPanel.setLayout(jobInfoPanelLayout);
        jobInfoPanelLayout.setHorizontalGroup(
            jobInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jobInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jobInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jobInfoPanelLayout.createSequentialGroup()
                        .addGroup(jobInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(infoPanelLabel)
                            .addComponent(infoPanelTimeStamp))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeButton))
                    .addGroup(jobInfoPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jobInfoPanelLayout.setVerticalGroup(
            jobInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jobInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jobInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jobInfoPanelLayout.createSequentialGroup()
                        .addComponent(closeButton)
                        .addGap(18, 18, 18))
                    .addGroup(jobInfoPanelLayout.createSequentialGroup()
                        .addComponent(infoPanelLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(infoPanelTimeStamp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jobInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jobInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2) {
            // requires double click
            dataIndex = jTable1.getSelectedRow();

            // System.out.println("test: " + tblModel.getValueAt(dataIndex, jTable1.getSelectedColumn()).toString());
            jobInfoPanel.setVisible(true);

            infoPanelHandler();
        }


    }//GEN-LAST:event_jTable1MouseClicked

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        // TODO add your handling code here:
        jobInfoPanel.setVisible(false);
    }//GEN-LAST:event_closeButtonMouseClicked

    private void RemoveRowButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemoveRowButtonMouseClicked
        // TODO add your handling code here:
        tblModel.removeRow(0);

    }//GEN-LAST:event_RemoveRowButtonMouseClicked

    private void sendFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendFileButtonActionPerformed
        try {
            // TODO add your handling code here:
            sendFile();
        } catch (IOException ex) {
            System.out.println("Failed in button press func");
        }
        System.out.println("Send File pressed");
    }//GEN-LAST:event_sendFileButtonActionPerformed

    public void cleanPreferredDelivery() {
        switch (timeSelect) {
            case "1":
                timeSelect = "9:00";
                break;
            case "2":
                timeSelect = "9:30";
                break;
            case "3":
                timeSelect = "10:00";
                break;
            case "4":
                timeSelect = "10:30";
                break;
            case "5":
                timeSelect = "11:00";
                break;
            case "6":
                timeSelect = "11:30";
                break;
            case "7":
                timeSelect = "12:00";
                break;
            case "8":
                timeSelect = "12:30";
                break;
            case "9":
                timeSelect = "13:00";
                break;
            case "10":
                timeSelect = "13:30";
                break;
            case "11":
                timeSelect = "14:00";
                break;
            case "12":
                timeSelect = "14:30";
                break;
            case "13":
                timeSelect = "15:00";
                break;
            case "14":
                timeSelect = "15:30";
                break;
            case "15":
                timeSelect = "16:00";
                break;
            case "16":
                timeSelect = "16:30";
                break;
            case "17":
                timeSelect = "17:00";
                break;
            default:
                timeSelect = "Not Specified";
                break;
        }
    }

    public void cleanMixDesign() {
        switch (mixDesign) {
            case "1":
                mixDesign = "7";
                break;
            case "2":
                mixDesign = "7mpa";
                break;
            case "3":
                mixDesign = "10";
                break;
            case "4":
                mixDesign = "10mpa";
                break;
            case "5":
                mixDesign = "14";
                break;
            case "6":
                mixDesign = "14mpa";
                break;
            case "7":
                mixDesign = "20";
                break;
            case "8":
                mixDesign = "20mpa";
                break;
            default:
                mixDesign = "Not Specified";
                break;

        }
    }

    public void cleanAcceptedTerms() {
        switch (terms) {
            case "0":
                terms = "No";
                break;
            case "1":
                terms = "Yes";
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton RemoveRowButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel fetchedRowsLabel;
    private javax.swing.JLabel filesCreatedLabel;
    private javax.swing.JLabel infoPanelLabel;
    private javax.swing.JLabel infoPanelTimeStamp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jobInfoArea;
    private javax.swing.JPanel jobInfoPanel;
    private javax.swing.JLabel numOfRowsDBLabel;
    private javax.swing.JButton sendFileButton;
    private javax.swing.JLabel serverTickedLabel;
    // End of variables declaration//GEN-END:variables
        int threadTicks = 0;

    @Override
    public void run() {
        while (true) {

            try {

//                System.out.println(threadTicks + " ticks has passed");
//                System.out.println("Fetched: Rows: " + fetchedRows);
//                System.out.println("Files: " + fileCount);
//                System.out.println("Row ReCount: " + rowReCount);
                //fetchData();
                // rowReCount = 0;
                if (fetchedRows == 0) {
                    try {
                        initialFetchData();
                        tableDataLoader();

                    } catch (SQLException ex) {
                        Logger.getLogger(MainDisplay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //reCountRows();
                Statement count = connection.createStatement();
                String SQLcount = "SELECT COUNT(*) FROM ordertablenew";
                ResultSet crs = count.executeQuery(SQLcount);
                crs.next();
                System.out.println("balls");
                DB_rowCount = crs.getInt(1);

               
                labelUpdater();
                //sendFile();

                //System.out.println("Number of rows within the SQL DB: " + DB_rowCount);
                if (DB_rowCount > fetchedRows) {

                    System.out.println("WARNING: Rows in table does not match the number of rows in Database!");

                    for (int i = 0; i < fetchedRows; i++) {
                        tblModel.removeRow(0);
                    }
                    fetchedRows = 0;
                    fileCount = 0;

                    initialFetchData();
                    tableDataLoader();

                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("Thread killed");//Logger.getLogger(MainDisplay.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
                threadTicks++;
            } catch (SQLException ex) {
                Logger.getLogger(MainDisplay.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void labelUpdater() {
        fetchedRowsLabel.setText("Fetched Rows: " + fetchedRows);
        filesCreatedLabel.setText("Files created: " + fileCount);
        serverTickedLabel.setText("Server ticked: " + threadTicks);
        numOfRowsDBLabel.setText("Number of rows in the MySQL Database: " + DB_rowCount);
    }

    String fileName = "2-Tyler.txt";

    public void sendFile() throws IOException {
        FileSender fs = new FileSender(fileName);
        //fs.newFileOut();
    }

}
