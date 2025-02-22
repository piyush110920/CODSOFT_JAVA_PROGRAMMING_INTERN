import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

// Course Class (Represents a course)
class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    int availableSlots;

    public Course(String courseCode, String title, String description, int capacity, int availableSlots) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.availableSlots = availableSlots;
    }
}

// Student Class (Represents a student)
class Student {
    String studentID;
    String name;
    List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (course.availableSlots > 0) {
            registeredCourses.add(course);
            course.availableSlots--;
        }
    }

    public void removeCourse(Course course) {
        registeredCourses.remove(course);
        course.availableSlots++;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentID + ", Name: " + name;
    }

    public String getRegisteredCourses() {
        StringBuilder courses = new StringBuilder();
        for (Course course : registeredCourses) {
            courses.append(course.title).append("\n");
        }
        return courses.toString();
    }
}

// Login Frame (Handles student login)
class LoginFrame extends JFrame {
    private JTextField studentIDField, studentNameField;
    private JButton loginButton;
    private CourseRegistrationSystem courseSystem;

    public LoginFrame(CourseRegistrationSystem courseSystem) {
        this.courseSystem = courseSystem;
        
        // Frame Setup
        setTitle("Student Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create UI components
        JLabel studentIDLabel = new JLabel("Student ID:");
        studentIDField = new JTextField(10);
        JLabel studentNameLabel = new JLabel("Name:");
        studentNameField = new JTextField(10);
        loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentID = studentIDField.getText();
                String studentName = studentNameField.getText();
                if (!studentID.isEmpty() && !studentName.isEmpty()) {
                    Student student = new Student(studentID, studentName);
                    courseSystem.setCurrentStudent(student);
                    JOptionPane.showMessageDialog(null, "Welcome " + studentName);
                    dispose();  // Close Login Frame
                    courseSystem.showRegistrationFrame();  // Show Registration Frame
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid information.");
                }
            }
        });

        // Add components to the frame
        add(studentIDLabel);
        add(studentIDField);
        add(studentNameLabel);
        add(studentNameField);
        add(loginButton);

        setVisible(true);
    }
}

// Course Registration Frame (Handles course selection and registration)
class RegistrationFrame extends JFrame {
    private JComboBox<String> courseList;
    private JTextArea registeredCoursesArea;
    private CourseRegistrationSystem courseSystem;
    private JButton logoutButton;

    public RegistrationFrame(CourseRegistrationSystem courseSystem) {
        this.courseSystem = courseSystem;
        
        // Frame Setup
        setTitle("Course Registration");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create UI components
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new FlowLayout());
        JLabel courseLabel = new JLabel("Select a Course:");
        courseList = new JComboBox<>();
        loadCourseList();
        coursePanel.add(courseLabel);
        coursePanel.add(courseList);

        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new FlowLayout());
        JButton registerButton = new JButton("Register for Course");
        JButton removeButton = new JButton("Remove Course");
        registeredCoursesArea = new JTextArea(10, 40);
        registeredCoursesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(registeredCoursesArea);

        // Register Button Action
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedCourseIndex = courseList.getSelectedIndex();
                if (selectedCourseIndex >= 0) {
                    Course selectedCourse = courseSystem.getCourseDatabase().get(selectedCourseIndex);
                    courseSystem.getCurrentStudent().registerCourse(selectedCourse);
                    loadRegisteredCourses();
                    JOptionPane.showMessageDialog(null, "Successfully registered for " + selectedCourse.title);
                }
            }
        });

        // Remove Button Action
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String courseCode = JOptionPane.showInputDialog("Enter course code to remove:");
                if (courseCode != null && !courseCode.isEmpty()) {
                    Course courseToRemove = null;
                    for (Course c : courseSystem.getCurrentStudent().registeredCourses) {
                        if (c.courseCode.equals(courseCode)) {
                            courseToRemove = c;
                            break;
                        }
                    }
                    if (courseToRemove != null) {
                        courseSystem.getCurrentStudent().removeCourse(courseToRemove);
                        loadRegisteredCourses();
                        JOptionPane.showMessageDialog(null, "Successfully removed " + courseToRemove.title);
                    } else {
                        JOptionPane.showMessageDialog(null, "Course not found in your registered courses.");
                    }
                }
            }
        });

        // Logout Button (Go back to Login)
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    courseSystem.showAllStudents(); // Show all students' info
                    courseSystem.showLoginFrame();  // Show Login Frame
                    dispose();  // Close Registration Frame
                }
            }
        });

        registrationPanel.add(registerButton);
        registrationPanel.add(removeButton);
        registrationPanel.add(scrollPane);
        registrationPanel.add(logoutButton);

        // Add panels to the frame
        add(coursePanel, BorderLayout.NORTH);
        add(registrationPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void loadCourseList() {
        courseList.removeAllItems();
        for (Course course : courseSystem.getCourseDatabase()) {
            courseList.addItem(course.title + " (" + course.availableSlots + " slots available)");
        }
    }

    public void loadRegisteredCourses() {
        registeredCoursesArea.setText("");
        for (Course course : courseSystem.getCurrentStudent().registeredCourses) {
            registeredCoursesArea.append(course.title + "\n");
        }
    }
}

// Main Course Registration System Class
public class CourseRegistrationSystem {
    private List<Course> courseDatabase;
    private Student currentStudent;
    private List<Student> allStudents;
    private LoginFrame loginFrame;
    private RegistrationFrame registrationFrame;

    public CourseRegistrationSystem() {
        courseDatabase = new ArrayList<>();
        allStudents = new ArrayList<>();
        // Sample courses
        courseDatabase.add(new Course("CS101", "Introduction to Computer Science", "Basic concepts of programming.", 30, 30));
        courseDatabase.add(new Course("MATH101", "Calculus I", "Introduction to Calculus.", 25, 25));
        courseDatabase.add(new Course("BIO101", "Introduction to Biology", "Basic concepts of biology.", 20, 20));
    }

    public List<Course> getCourseDatabase() {
        return courseDatabase;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
        allStudents.add(currentStudent);  // Add the student to the list
    }

    public List<Student> getAllStudents() {
        return allStudents;
    }

    public void showLoginFrame() {
        loginFrame = new LoginFrame(this);
    }

    public void showRegistrationFrame() {
        registrationFrame = new RegistrationFrame(this);
    }

    public void showAllStudents() {
        StringBuilder studentDetails = new StringBuilder("All Registered Students and Their Courses:\n\n");
        for (Student student : allStudents) {
            studentDetails.append(student).append("\nRegistered Courses:\n")
                .append(student.getRegisteredCourses()).append("\n");
        }
        JOptionPane.showMessageDialog(null, studentDetails.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CourseRegistrationSystem courseSystem = new CourseRegistrationSystem();
                courseSystem.showLoginFrame();  // Show Login Frame
            }
        });
    }
}
