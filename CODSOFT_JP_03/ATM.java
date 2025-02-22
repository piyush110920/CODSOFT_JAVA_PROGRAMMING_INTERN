import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
        createUserInterface(); // Corrected method name
    }

    private void createUserInterface() { // Corrected method name
        JFrame frame = new JFrame("ATM Interface");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 1));

        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton exitButton = new JButton("Exit");

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog("Enter amount to withdraw:");
                if (amountStr != null) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        if (withdraw(amount)) {
                            JOptionPane.showMessageDialog(frame, "Withdrawal successful!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Insufficient balance!");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid amount entered!");
                    }
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog("Enter amount to deposit:");
                if (amountStr != null) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        deposit(amount);
                        JOptionPane.showMessageDialog(frame, "Deposit successful!");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid amount entered!");
                    }
                }
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Current Balance: " + account.getBalance());
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(withdrawButton);
        frame.add(depositButton);
        frame.add(checkBalanceButton);
        frame.add(exitButton);
        frame.setVisible(true);
    }

    private boolean withdraw(double amount) {
        if (amount > 0 && amount <= account.getBalance()) {
            account.withdraw(amount);
            return true;
        }
        return false;
    }

    private void deposit(double amount) {
        if (amount > 0) {
            account.deposit(amount);
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // Initial balance
        new ATM(account);
    }
}