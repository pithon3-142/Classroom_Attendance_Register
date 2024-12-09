package attendance_register;

/*
Author : Peniel Ndlela 
AKA    : Pi
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;
import java.util.Map;
import javax.swing.JOptionPane;

//Create class Teacher
class Teacher implements Serializable {
    String id;
    String name;
    String subject;
    String username;
    String password;
    
    public Teacher(String id, String name, String subject, String username, String password) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.username = username;
        this.password = password;
    }

    public String toString() {
        return name+ "\tID No. : "+id;
    }
    
}

//Create class Student
class Student implements Serializable {
    String id;
    String names;
    String grade;

    public Student(String id, String names, String grade) {
        this.id = id;
        this.names = names;
        this.grade = grade;
    }

    public String toString() {
        return "Student ID : "+id+"\tStudent Names : "+names+"\tGrade : "+grade;
    }
}

//Create class Attendance Record
class AttendanceRecord implements Serializable {
    String date;
    boolean isPresent;

    AttendanceRecord(String date, boolean isPresent) {
        this.date = date;
        this.isPresent = isPresent;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Present: " + isPresent;
    }
}

//Main class -> Contains Main Method
public class Attendance_Register {
    private static Attendance_Register instance;

    private HashMap <String, Student> student = new HashMap<>();
    private HashMap <String, Teacher> teacher = new HashMap<>();
    private HashMap <String, List<AttendanceRecord>> attendanceRecord = new HashMap<>();

    static {
        instance = new Attendance_Register();
    }
    
    public static Attendance_Register getInstance() {
        return instance;
    }
    //Method to add a Student
    public void addStudent(String id, String names, String grade) {
        if (!student.containsKey(id)) {
            student.put(id, new Student(id, names, grade));
            saveStudentToFile("student_file.dat");
        } 
        else {
            JOptionPane.showMessageDialog(null, "Student with ID " + id + " already exists.");
    }
    }
    
    //Method to save student details to file
    public void saveStudentToFile(String student_file) {
        File dataDir = new File(System.getProperty("user.home"), "attendancefiles/data");
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create directories if they do not exist
        }
        File file = new File(dataDir, student_file);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)))
        {
            out.writeObject(student);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to save Student");
        }
    }
    
    //Method to load Student data from file
    public HashMap <String, Student> loadStudentFile(String student_file) {
        File dataDir = new File(System.getProperty("user.home"), "attendancefiles/data");
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create directories if they do not exist
        }
        File file = new File(dataDir, student_file);
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file)))
        {
            student = (HashMap <String, Student>) in.readObject();
            
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to retrieve Student ");
        }
        return student;
    }
    
    //Method to delete Student
    public void delStudent(String id, String student_file) {
        File dataDir = new File(System.getProperty("user.home"), "attendancefiles/data");
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create directories if they do not exist
        }
        File file = new File(dataDir, student_file);
        if (student.containsKey(id)) {
            student.remove(id);
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(student);
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Unable to save Changes");
            }
        
            Map<String, List<AttendanceRecord>> attendanceData = loadRecordFile("record_file.dat");
            if (attendanceData.containsKey(id)) {
                attendanceData.remove(id);
                // Save the updated attendance data back to the file
                saveRecord(attendanceData,"record_file.dat");
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Error !!!\nStudent with ID '"+id+"' does not exist");
        }
    }

    //Method to add a Teacher
    public void addTeacher(String id, String name, String subject, String username, String password) {
        if (!teacher.containsKey(id)) {
            teacher.put(id, new Teacher(id, name, subject, username, password));
            saveTeacherToFile("teacher_file.dat");
        }
        else {
            JOptionPane.showMessageDialog(null, "Teacher with ID : "+id+" already exists");
        }
    }
    
    //Method to save Teacher to file
    public void saveTeacherToFile(String teacher_file) {
        File dataDir = new File(System.getProperty("user.home"), "attendancefiles/data");
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create directories if they do not exist
        }
        File file = new File(dataDir, teacher_file);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)))
        {
            out.writeObject(teacher);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to save Teacher");
        }
    }
    
    //Method to load Teacher from file
    public HashMap <String, Teacher> loadTeacherFile (String teacher_file) {
        File dataDir = new File(System.getProperty("user.home"), "attendancefiles/data");
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create directories if they do not exist
        }
        File file = new File(dataDir, teacher_file);
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            teacher = (HashMap <String, Teacher>) in.readObject();
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found: " + teacher_file);
        }   
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong while reading the Teacher File");
            e.printStackTrace();
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to read Teacher due to an unknown error.");
            e.printStackTrace();
        }
        
        return teacher;
    }
    
    //Method to delete Teacher
    public void delTeacher(String id) {
        if (teacher.containsKey(id)) {
            teacher.remove(id);
        }
        else {
            JOptionPane.showMessageDialog(null, "Error !!!\nTeacher with ID '"+id+"' does not exist");
        }
    }
    
    //Method to authenticate a Teacher's credentials
    public Teacher authenticateTeacher(String username, String password) {
        loadTeacherFile("teacher_file.dat");
        
        for (Teacher teach : teacher.values()) {
        if (teach.username.equals(username) && teach.password.equals(password)) {
            return teach; // Authentication successful, return the teacher
        }
    }
    return null; // Authentication failed
}

    //Method to add attendance record
    public void addAttendanceRecord(String id, String date, boolean isPresent) {
        AttendanceRecord record = new AttendanceRecord(date, isPresent);
        if (attendanceRecord.computeIfAbsent(id, k-> new ArrayList<>()).add(record)) {
            saveRecord(attendanceRecord,"record_file.dat");
        }
        else {
            JOptionPane.showMessageDialog(null, "This record already exists");
        }
    }
    
    //Method to retrieve load record_file
    public HashMap<String, List<AttendanceRecord>> loadRecordFile(String record_file) {
        File dataDir = new File(System.getProperty("user.home"), "attendancefiles/data");
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create directories if they do not exist
        }
        File file = new File(dataDir, record_file);
        
         if (!file.exists()) {
            try {
                file.createNewFile(); // Create the file
                // Initialize the file with default data
                HashMap<String, List<AttendanceRecord>> defaultData = new HashMap<>();
                defaultData.put("defaultID", new ArrayList<AttendanceRecord>()); // Add a default record
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                    out.writeObject(defaultData);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error initializing file with default data: " + file.getAbsolutePath());
                    e.printStackTrace();
                    return new HashMap<>(); // Return an empty map in case of an error
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error creating file: " + file.getAbsolutePath());
                e.printStackTrace();
                return new HashMap<>(); // Return an empty map in case of an error
            }
        }
        
        try (ObjectInputStream record_file_in = new ObjectInputStream(new FileInputStream(file))) {
            attendanceRecord = (HashMap<String, List<AttendanceRecord>>) record_file_in.readObject();
        } 
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found: " + record_file);
        }   
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO error while reading the file: " + record_file);
            e.printStackTrace();
        }   
        catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Class not found when reading the file: " + record_file);
            e.printStackTrace();
        }   catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to read attendance record due to an unknown error.");
            e.printStackTrace();
    }
    return attendanceRecord;
}
    
    
    //Method to save attendance record to file
    public void saveRecord(Map<String, List<AttendanceRecord>> attendanceData, String record_file) {
        File dataDir = new File(System.getProperty("user.home"), "attendancefiles/data");
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create directories if they do not exist
        }
        File file = new File(dataDir, record_file);
       
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(attendanceRecord);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to add Attendance record");
        }
    }
    


    
    public static void main(String[] args) {
        Attendance_Register register = Attendance_Register.getInstance();
        /*File dataDir = new File(System.getProperty("user.home"), "attendancefiles/data");
        if (!dataDir.exists()) {
            System.out.println("Doesnt exist");
            dataDir.mkdirs();
            System.out.println("Success");// Create directories if they do not exist
        }
        else {
            System.out.println("Failed to create");
        }*/
        register.addTeacher("A01", "Administrator", "Administration" , "admin", "admin");
        register.addTeacher("T01", "Miss Pretty Nina", "Computer Science", "Nina", "12345");
        register.addStudent("S001", "Miss Melody", "12");
        register.addStudent("S002", "John Doe", "11");
        register.addStudent("S003", "Jane Smith", "10");
        register.addStudent("S004", "Alex Johnson", "9");
        register.addStudent("S005", "Emily Davis", "12");
        register.addStudent("S006", "Michael Brown", "11");
        register.addStudent("S007", "Sarah Miller", "10");
        register.addStudent("S008", "David Wilson", "9");
        register.addStudent("S009", "Sophia Moore", "12");
        register.addStudent("S010", "Chris Taylor", "11");
        register.addStudent("S011", "Emma Anderson", "10");
        register.addStudent("S012", "Daniel Thomas", "9");
        register.addStudent("S013", "Olivia Jackson", "12");
        register.addStudent("S014", "James White", "11");
        register.addStudent("S015", "Isabella Harris", "10");
        register.addStudent("S016", "Matthew Martin", "9");
        register.addStudent("S017", "Mia Thompson", "12");
        register.addStudent("S018", "Ethan Garcia", "11");
        register.addStudent("S019", "Charlotte Martinez", "10");
        register.addStudent("S020", "Andrew Robinson", "9");
        register.addStudent("S021", "Amelia Clark", "12");
        register.addStudent("S022", "Joshua Lewis", "11");
        register.addStudent("S023", "Abigail Walker", "10");
        register.addStudent("S024", "William Young", "9");
        register.addStudent("S025", "Hannah King", "12");
        register.addStudent("S026", "Saitama Wong", "12");        
        register.addStudent("S027","Maria Milo", "12");
        
        new LoginPage().setVisible(true);
    }
}



