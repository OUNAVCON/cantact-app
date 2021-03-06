package org.cantact.ui;

import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

import org.cantact.core.CanListener;
import org.cantact.core.CanFrame;
import org.cantact.core.DeviceManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.cantact.ui//Trace//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "TraceTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.cantact.ui.TraceTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_TraceAction",
        preferredID = "TraceTopComponent"
)
@Messages({
    "CTL_TraceAction=Trace",
    "CTL_TraceTopComponent=Trace Window",
    "HINT_TraceTopComponent=This is a Trace window"
})
public final class TraceTopComponent extends TopComponent implements CanListener {
    private long startTime = 0;
    private boolean running = false;
    private int filterMinId = 0;
    private int filterMaxId = 0x7FFFFFFF;

    public TraceTopComponent() {
        initComponents();
        setName(Bundle.CTL_TraceTopComponent());
        setToolTipText(Bundle.HINT_TraceTopComponent());
        DeviceManager.addListener(this);
    }

    class TraceUpdater implements Runnable {

        private CanFrame frame;

        public TraceUpdater(CanFrame f) {
            frame = f;
        }

        public void run() {
            /* calculate the relative timestamp in seconds */
            float timestamp = (((float)(System.currentTimeMillis() - startTime)) 
                    / 1000);

            // format ID as hex
            String idString;
            if (frame.getId() < 0x7FF) {
                idString = String.format("0x%04X", frame.getId());
            } else {
                idString = String.format("0x%08X", frame.getId());
            }
            
            // format data as bytes in hex
            String dataString = "";
            for (int i = 0; i < frame.getDlc(); i++) {
                dataString = dataString + String.format("%02X ", 
                        frame.getData()[i]);
            }

            // set the row data and insert the row
            DefaultTableModel messageModel = (DefaultTableModel) messageTable.getModel();
            Object[] rowData = {(Object) timestamp, (Object) idString, 
                (Object) frame.getDlc(), dataString};
            messageModel.insertRow(0, rowData);
        }
    }

    @Override
    public void canReceived(CanFrame f) {
        // only receive the frame if trace is running and value is within filter
        if (running && f.getId() > filterMinId && f.getId() < filterMaxId) {
            java.awt.EventQueue.invokeLater(new TraceUpdater(f));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filterDialog = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        minIdTextField = new javax.swing.JTextField();
        maxIdTextField = new javax.swing.JTextField();
        filterApplyButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageTable = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        filterButton = new javax.swing.JButton();

        filterDialog.setLocation(new java.awt.Point(100, 100));
        filterDialog.setMinimumSize(new java.awt.Dimension(300, 130));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.jLabel2.text")); // NOI18N

        minIdTextField.setText(org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.minIdTextField.text")); // NOI18N

        maxIdTextField.setText(org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.maxIdTextField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(filterApplyButton, org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.filterApplyButton.text")); // NOI18N
        filterApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterApplyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout filterDialogLayout = new javax.swing.GroupLayout(filterDialog.getContentPane());
        filterDialog.getContentPane().setLayout(filterDialogLayout);
        filterDialogLayout.setHorizontalGroup(
            filterDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(filterDialogLayout.createSequentialGroup()
                        .addGroup(filterDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(filterDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(minIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maxIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(filterApplyButton))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        filterDialogLayout.setVerticalGroup(
            filterDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(minIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(filterDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(maxIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterApplyButton)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        messageTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Timestamp", "ID", "DLC", "Data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(messageTable);
        if (messageTable.getColumnModel().getColumnCount() > 0) {
            messageTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.messageTable.columnModel.title0")); // NOI18N
            messageTable.getColumnModel().getColumn(1).setMinWidth(150);
            messageTable.getColumnModel().getColumn(1).setMaxWidth(150);
            messageTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.messageTable.columnModel.title1")); // NOI18N
            messageTable.getColumnModel().getColumn(2).setMinWidth(35);
            messageTable.getColumnModel().getColumn(2).setMaxWidth(35);
            messageTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.messageTable.columnModel.title2")); // NOI18N
            messageTable.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.messageTable.columnModel.title3")); // NOI18N
        }

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        startButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cantact/ui/record.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(startButton, org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.startButton.text")); // NOI18N
        startButton.setMaximumSize(new java.awt.Dimension(30, 30));
        startButton.setMinimumSize(new java.awt.Dimension(30, 30));
        startButton.setPreferredSize(new java.awt.Dimension(50, 50));
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(startButton);

        stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cantact/ui/stop.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(stopButton, org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.stopButton.text")); // NOI18N
        stopButton.setEnabled(false);
        stopButton.setMaximumSize(new java.awt.Dimension(30, 30));
        stopButton.setMinimumSize(new java.awt.Dimension(30, 30));
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(stopButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cantact/ui/save.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(saveButton, org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.saveButton.text")); // NOI18N
        saveButton.setMaximumSize(new java.awt.Dimension(30, 30));
        saveButton.setMinimumSize(new java.awt.Dimension(30, 30));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(saveButton);

        filterButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/cantact/ui/filter.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(filterButton, org.openide.util.NbBundle.getMessage(TraceTopComponent.class, "TraceTopComponent.filterButton.text")); // NOI18N
        filterButton.setFocusable(false);
        filterButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        filterButton.setMaximumSize(new java.awt.Dimension(30, 30));
        filterButton.setMinimumSize(new java.awt.Dimension(30, 30));
        filterButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        filterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(filterButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        /* clear the table */
        DefaultTableModel messageModel = (DefaultTableModel) messageTable.getModel();
        while (messageModel.getRowCount() > 0) {
            for (int i = 0; i < messageModel.getRowCount(); i++) {
                messageModel.removeRow(i);
            }
        }

        /* set the relative start time to now */
        startTime = System.currentTimeMillis();
        /* set running flag, start receiving messages */
        running = true;

        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }//GEN-LAST:event_startButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        /* clear running flag, stop receiving messages */
        running = false;

        stopButton.setEnabled(false);
        startButton.setEnabled(true);
    }//GEN-LAST:event_stopButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ASCII Logs", "asc");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            /* save table contents as file */
            FileWriter os;
            try {
                os = new FileWriter(chooser.getSelectedFile());

                /* get the model of the table */
                DefaultTableModel model = (DefaultTableModel) messageTable.getModel();

                /* iterate backwards from the oldest ot newest message */
                for (int row = model.getRowCount() - 1; row >= 0; row--) {
                    /* get the values from the table model */
                    float timestamp = (float) model.getValueAt(row, 0);
                    int id = Integer.valueOf(
                            model.getValueAt(row, 1).toString().substring(2), 
                            16);
                    String data = (String) model.getValueAt(row, 3);

                    /* create candump formatted string */
                    data = data.replace(" ", "");
                    String out;
                    if (id < 0x7FF) {
                    out = String.format("(%05f) can0 %03X#%s\n",
                            timestamp, id, data);
                    } else {
                     out = String.format("(%05f) can0 %08X#%s\n",
                            timestamp, id, data);                       
                    }
                    os.write(out);
                }
                os.close();
            } catch (IOException e) {
                System.out.println("error writing file");
            }
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void filterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterButtonActionPerformed
        filterDialog.setVisible(true);
    }//GEN-LAST:event_filterButtonActionPerformed

    private void filterApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterApplyButtonActionPerformed
        try {
            filterMinId = Integer.decode(minIdTextField.getText());
            filterMaxId = Integer.decode(maxIdTextField.getText());
        } catch (NumberFormatException e) {
            // bad values, but do nothing
        }
    }//GEN-LAST:event_filterApplyButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton filterApplyButton;
    private javax.swing.JButton filterButton;
    private javax.swing.JDialog filterDialog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField maxIdTextField;
    private javax.swing.JTable messageTable;
    private javax.swing.JTextField minIdTextField;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
