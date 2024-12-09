package attendance_register;

import java.io.*;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class RegisterFrame extends javax.swing.JFrame {

    private Attendance_Register register;
    
    
    private void loadStudentData(JTable regTable) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = formatter.format(new Date());
        
        Attendance_Register.getInstance();
        lastSavedDate = loadLastSavedDate();
        
        String in_grade = "";
        
        // Load student data into the table
        DefaultTableModel model = (DefaultTableModel) regTable.getModel();
        regTable.setModel(model);
        model.setRowCount(0); // Clear existing rows

        // Load data from the first file
        Map<String, Student> studentData = register.loadStudentFile("student_file.dat");

        Set<String> availableGrades = new HashSet<>();
        for (Student student : studentData.values()) {
            availableGrades.add(student.grade);
        }
        
        if (!lastSavedDate.equals(currentDate)) {
   
            boolean valid = false;
            
            while (!valid) {
                in_grade = JOptionPane.showInputDialog(this, "Enter the grade for attendance marking");
                if (in_grade != null && !in_grade.trim().isEmpty()) {
                    if (availableGrades.contains(in_grade)) {
                        valid = true;
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Grade not found. Please enter a valid grade from the available Grades: "+availableGrades);
                    }
                }
            }
        }
        
        // Load data from the AttendanceRecord 
        Map<String, List<AttendanceRecord>> attendanceData = register.loadRecordFile("record_file.dat");

        // Process both sets of data
        for (Student student : studentData.values()) {
            
            if (student.grade.equals(in_grade)) {
                Object[] row = new Object[5];
                row[0] = student.id;
                row[1] = student.names;
                row[2] = student.grade;
            
            // Check attendance status from the second file
                Boolean status = Boolean.FALSE;
                if (attendanceData.containsKey(student.id)) {
                    List<AttendanceRecord> records = attendanceData.get(student.id);
                    for (AttendanceRecord record : records) {
                        if (record.date.equals(currentDate)) { // or some other logic to pick the right record
                            status = record.isPresent;
                            break; // Exit loop once you find the relevant record
                        }
                    }
                }
                row[3] = status;         // Present checkbox
                row[4] = currentDate;    // Date
                model.addRow(row);

            }
        }
    }

    
    private void re_loadStudentData(JTable regTable) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String currentDate = formatter.format(new Date());
    
    Attendance_Register.getInstance();
    lastSavedDate = loadLastSavedDate();
    
    // Load student data into the table
    DefaultTableModel model = (DefaultTableModel) regTable.getModel();
    model.setRowCount(0); // Clear existing rows

    // Load data from the student file
    Map<String, Student> studentData = register.loadStudentFile("student_file.dat");

    // Load data from the attendance record file
    Map<String, List<AttendanceRecord>> attendanceData = register.loadRecordFile("record_file.dat");

    // Process and display both sets of data
    for (Student student : studentData.values()) {
        Object[] row = new Object[5];
        row[0] = student.id;
        row[1] = student.names;
        row[2] = student.grade;

        // Check attendance status from the second file
        Boolean status = Boolean.FALSE;
        String attendanceDate = "N/A";  // Default if no record found

        if (attendanceData.containsKey(student.id)) {
            List<AttendanceRecord> records = attendanceData.get(student.id);
            for (AttendanceRecord record : records) {
                // Display the most recent attendance record
                status = record.isPresent;
                attendanceDate = record.date;
            }
        }

        row[3] = status;        // Present checkbox
        row[4] = attendanceDate; // Attendance date
        
        model.addRow(row); // Add the row to the table
    }
}
    
    
    private static String lastSavedDate = "default";
    private void saveAttendance() {
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String curDate = formatter.format(new Date());
        
        if (lastSavedDate.equals(curDate)) {
            JOptionPane.showMessageDialog(this, "Today's Attendance has already been marked and saved.");
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) regTable.getModel();
       
            for (int i = 0; i < model.getRowCount(); i++) {
                String studentId = (String) model.getValueAt(i, 0);
                boolean isPresent = (Boolean) model.getValueAt(i, 3);
                String date = (String) model.getValueAt(i, 4);
            
                register.addAttendanceRecord(studentId, date, isPresent);
            }
        lastSavedDate = curDate;
        
        saveLastSavedDate(lastSavedDate);
            
        JOptionPane.showMessageDialog(this, curDate+" attendance saved successfully");
       
    }
    
    
    private void saveLastSavedDate(String date) {
        File dataDir = new File(System.getProperty("user.home"), "attendancefiles/data");
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create directories if they do not exist
        }
        File dateFile = new File(dataDir, "last_saved_date.txt");
        date = lastSavedDate;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dateFile))) {
            writer.write(date);
        } 
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save the last saved date.");
        }
    }

    
    private String loadLastSavedDate() {
        File dataDir = new File(System.getProperty("user.home"), "attendancefiles/data");
        
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create directories if they do not exist
        }
        File dateFile = new File(dataDir, "last_saved_date.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(dateFile))) {
            return reader.readLine();
        } 
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found: last saved date"+dateFile );
        }   
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong while reading the Last Saved Date");
            e.printStackTrace();
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to read last saved date due to an unknown error.");
            e.printStackTrace();
        }
        // Return an empty string or a default value if an error occurs
        return "";
    }


     public RegisterFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Windhoek Technical HighSchool Class Register");
        register = Attendance_Register.getInstance(); // Ensure register is initialized
        
        saveLastSavedDate(lastSavedDate);
        lastSavedDate = loadLastSavedDate();
        
        loadStudentData(regTable);
        
        setupUI();
     
    }
    
     
     

    private void setupUI() {
        
        searchTxtField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateResults();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateResults();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateResults();
            }

        });

    }




    // Method to display the filtered results in the JTable
private void updateResults() {
    String query = searchTxtField.getText().toLowerCase().trim(); // Convert to lowercase and trim for case-insensitive search
    DefaultTableModel model = (DefaultTableModel) regTable.getModel();
    model.setRowCount(0); // Clear existing rows

    if (query.isEmpty()) {
        re_loadStudentData(regTable); // Reload today's register
        return;
    }

    Map<String, Student> studentData = register.loadStudentFile("student_file.dat");
    Map<String, List<AttendanceRecord>> attendanceData = register.loadRecordFile("record_file.dat");

    boolean isGradeSearch = query.startsWith("g"); // Check if query is grade search (starts with 'G')
    String gradeQuery = query.length() > 1 ? query.substring(1) : ""; // Extract grade after 'G'

    for (Student student : studentData.values()) {
        boolean matchesStudent = student.id.toLowerCase().contains(query) || student.names.toLowerCase().contains(query);
        boolean matchesGrade = isGradeSearch && student.grade.equalsIgnoreCase(gradeQuery); // Check for grade match with 'G'
        boolean matchesQuery = false;
        Boolean status = Boolean.FALSE;
        String recordDate = "";

        if (attendanceData.containsKey(student.id)) {
            List<AttendanceRecord> records = attendanceData.get(student.id);
            for (AttendanceRecord record : records) {
                // Convert the presence status to a string for searching
                String presenceStatus = record.isPresent ? "present" : "absent";

                // Check if the query matches the student's ID, names, attendance date, or presence status
                if (matchesStudent || matchesGrade || record.date.contains(query) || presenceStatus.contains(query)) {
                    status = record.isPresent; // Set the attendance status
                    recordDate = record.date; // Set the correct date from the attendance record
                    matchesQuery = true; // Mark this student as matching the query
                    // Do not break; continue checking for more relevant records
                }
            }
        }

        // Only add the row if the student matches the query
        if (matchesQuery || matchesStudent || matchesGrade) {
            Object[] row = new Object[5];
            row[0] = student.id; // Student ID
            row[1] = student.names; // Student Names
            row[2] = student.grade; // Student Grade
            row[3] = status;   // Present checkbox
            row[4] = recordDate.isEmpty() ? "N/A" : recordDate; // Use recordDate if found, else "N/A"
            model.addRow(row);
        }
    }
}




    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        contentPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        regTable = new javax.swing.JTable();
        sectionPanel = new javax.swing.JPanel();
        saveBtn = new javax.swing.JButton();
        resetBtn = new javax.swing.JButton();
        addStudRecBtn = new javax.swing.JButton();
        addTeachBtn = new javax.swing.JButton();
        delStudRec = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        delTeachBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        refresh = new javax.swing.JButton();
        searchTxtField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 300));

        jPanel1.setLayout(new java.awt.BorderLayout());

        mainPanel.setBackground(new java.awt.Color(204, 204, 255));
        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        contentPanel.setBackground(new java.awt.Color(204, 204, 255));
        contentPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(102, 102, 255), new java.awt.Color(102, 102, 255), null, null));
        contentPanel.setAutoscrolls(true);

        regTable.setAutoCreateRowSorter(true);
        regTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        regTable.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        regTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Names", "Grade", "Attendance Status", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        regTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        regTable.setIntercellSpacing(new java.awt.Dimension(3, 3));
        regTable.setRequestFocusEnabled(false);
        regTable.setRowHeight(40);
        regTable.setSelectionBackground(new java.awt.Color(204, 204, 204));
        regTable.setSelectionForeground(new java.awt.Color(153, 153, 153));
        regTable.setShowGrid(true);
        jScrollPane1.setViewportView(regTable);

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1156, Short.MAX_VALUE)
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        sectionPanel.setBackground(new java.awt.Color(255, 255, 204));
        sectionPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, new java.awt.Color(255, 255, 255), null, null));

        saveBtn.setBackground(new java.awt.Color(51, 255, 51));
        saveBtn.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        saveBtn.setForeground(new java.awt.Color(51, 0, 0));
        saveBtn.setText("Save");
        saveBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 255, 204), new java.awt.Color(204, 255, 204), null, null));
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        resetBtn.setBackground(new java.awt.Color(255, 51, 51));
        resetBtn.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        resetBtn.setText("Reset");
        resetBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        addStudRecBtn.setText("Add Student");
        addStudRecBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudRecBtnActionPerformed(evt);
            }
        });

        addTeachBtn.setText("Add Teacher");
        addTeachBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTeachBtnActionPerformed(evt);
            }
        });

        delStudRec.setText("Delete Student");
        delStudRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delStudRecActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Administrative Priviledges");

        delTeachBtn.setText("Delete Teacher");
        delTeachBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delTeachBtnActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Class Register");

        refresh.setBackground(new java.awt.Color(51, 102, 255));
        refresh.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        refresh.setText("Refresh");
        refresh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 204, 255), new java.awt.Color(51, 204, 255), null, null));
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sectionPanelLayout = new javax.swing.GroupLayout(sectionPanel);
        sectionPanel.setLayout(sectionPanelLayout);
        sectionPanelLayout.setHorizontalGroup(
            sectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(refresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addStudRecBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resetBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addTeachBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delStudRec, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delTeachBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sectionPanelLayout.setVerticalGroup(
            sectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sectionPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(resetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(addStudRecBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(delStudRec, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(addTeachBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(delTeachBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        searchTxtField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        searchTxtField.setForeground(new java.awt.Color(204, 204, 204));
        searchTxtField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchTxtField.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Attendance Register", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Century", 0, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        searchTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTxtFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addComponent(sectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(searchTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(sectionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(mainPanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1415, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 817, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTxtFieldActionPerformed
        
    }//GEN-LAST:event_searchTxtFieldActionPerformed

    private void delTeachBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delTeachBtnActionPerformed
        JOptionPane.showMessageDialog(this, "Login as Admin to make these changes");
        AdminLogin admin = new AdminLogin();
        admin.setVisible(true);
        int a = 4;
        admin.bit(a);
        if (admin.isAdminAuth() == true) {    
            String id = JOptionPane.showInputDialog(this, "Enter the Teacher's ID:");
        
            // Check if input is not null and not empty
            if (id != null && !id.trim().isEmpty()) {
                // Perform action based on the input
                Attendance_Register.getInstance().delTeacher(id);
                JOptionPane.showMessageDialog(this, "Teacher '"+id+"' deleted successfully.");
            } 
            else {
                // Handle the case where no input was provided
                JOptionPane.showMessageDialog(this, "No ID entered. Action not performed.");
            }
        }
    }//GEN-LAST:event_delTeachBtnActionPerformed

    private void delStudRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delStudRecActionPerformed
        JOptionPane.showMessageDialog(this, "Login as Admin to make these changes");
        AdminLogin admin = new AdminLogin();
        admin.setVisible(true);
        int a = 2;
        admin.bit(a);
        
    }//GEN-LAST:event_delStudRecActionPerformed

    private void addTeachBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTeachBtnActionPerformed
        JOptionPane.showMessageDialog(this, "Login as Admin to make these changes");
        AdminLogin admin = new AdminLogin();
        admin.setVisible(true);
        int a = 3;
        admin.bit(a);
       
    }//GEN-LAST:event_addTeachBtnActionPerformed

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        DefaultTableModel model = (DefaultTableModel) regTable.getModel();

        model .setRowCount(0);

        loadStudentData(regTable);

        JOptionPane.showMessageDialog(this, "Register has been clear. You can now remark Attendance.");
    }//GEN-LAST:event_resetBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        saveAttendance();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void addStudRecBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudRecBtnActionPerformed
        JOptionPane.showMessageDialog(this, "Login as Admin to make these changes");
        AdminLogin admin = new AdminLogin();
        admin.setVisible(true);
        int a = 1;
        admin.bit(a);
    }//GEN-LAST:event_addStudRecBtnActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        re_loadStudentData(regTable);
    }//GEN-LAST:event_refreshActionPerformed

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
            java.util.logging.Logger.getLogger(RegisterFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterFrame().setVisible(true);
                
}
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addStudRecBtn;
    private javax.swing.JButton addTeachBtn;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton delStudRec;
    private javax.swing.JButton delTeachBtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton refresh;
    private javax.swing.JTable regTable;
    private javax.swing.JButton resetBtn;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTextField searchTxtField;
    private javax.swing.JPanel sectionPanel;
    // End of variables declaration//GEN-END:variables
}
