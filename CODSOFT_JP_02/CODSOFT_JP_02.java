import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CODSOFT_JP_02 extends JFrame {
    private JTextField[] markFields;
    private JTextArea resultArea;
    private JButton calculateButton;
    private int numSubjects;
    public CODSOFT_JP_02() {
        setTitle("Student Grade Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        JLabel subjectLabel = new JLabel("Enter number of subjects:");
        JTextField subjectField = new JTextField(5);
        add(subjectLabel);
        add(subjectField);
        JButton createFieldsButton = new JButton("Create Fields");
        add(createFieldsButton);
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));
        calculateButton = new JButton("Calculate");
        calculateButton.setEnabled(false);
        add(calculateButton);
        createFieldsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numSubjects = Integer.parseInt(subjectField.getText());
                markFields = new JTextField[numSubjects];
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(numSubjects, 2));
                for (int i = 0; i < numSubjects; i++) {
                    panel.add(new JLabel("Subject " + (i + 1) + ":"));
                    markFields[i] = new JTextField(5);
                    panel.add(markFields[i]);
                }
                JOptionPane.showMessageDialog(null, panel, "Enter Marks", JOptionPane.PLAIN_MESSAGE);
                calculateButton.setEnabled(true);
            }
        });
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResults();
            }
        });
    }
    private void calculateResults() {
        double totalMarks = 0;
        for (JTextField markField : markFields) {
            totalMarks += Double.parseDouble(markField.getText());
        }
        double averagePercentage = totalMarks / numSubjects;
        String grade = calculateGrade(averagePercentage);
        resultArea.setText("Total Marks: " + totalMarks + "\n");
        resultArea.append("Average Percentage: " + averagePercentage + "%\n");
        resultArea.append("Grade: " + grade);
    }
    private String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A+";
        } else if (averagePercentage >= 80) {
            return "A";
        } else if (averagePercentage >= 70) {
            return "B";
        } else if (averagePercentage >= 60) {
            return "C";
        } else if (averagePercentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CODSOFT_JP_02 calculator = new CODSOFT_JP_02();
            calculator.setVisible(true);
        });
    }
}