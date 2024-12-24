import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KasApp {
    private static List<User> users = new ArrayList<>();
    private static List<Iuran> iurans = new ArrayList<>();
    private static List<Payment> payments = new ArrayList<>();
    private static JFrame mainFrame;

    public static void main(String[] args) {
        loadUsers();
        loadIurans();
        loadPayments();
        // Menambahkan pengguna admin default jika tidak ada
        if (users.isEmpty()) {
            users.add(new User("admin", "admin123"));
        }
        createLoginWindow();
    }

    private static void createLoginWindow() {
        mainFrame = new JFrame("Login");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 200);
        mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        mainFrame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        mainFrame.setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(24);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(24);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (isAdmin(username, password)) {
                    mainFrame.dispose();
                    createAdminMenu();
                } else if (isUser (username, password)) {
                    mainFrame.dispose();
                    createUserMenu(username);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Username atau Password salah! Silakan coba lagi.");
                }
            }
            private static boolean isAdmin(String username, String password) {
                return username.equals("admin") && password.equals("admin123");
            }

            private static boolean isUser (String username, String password) {
                return users.stream().anyMatch(user -> user.username.equals(username) && user.password.equals(password));
            }
        });

        mainFrame.add(usernameLabel);
        mainFrame.add(usernameField);
        mainFrame.add(passwordLabel);
        mainFrame.add(passwordField);
        mainFrame.add(loginButton);
        mainFrame.setVisible(true);
    }

    private static void createAdminMenu() {
        JFrame adminFrame = new JFrame("Menu Admin");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(400, 200);
        adminFrame.setLayout(new FlowLayout(FlowLayout.CENTER,100,10));
        adminFrame.getRootPane().setBorder(BorderFactory.createEmptyBorder(20,100,20,100));
        adminFrame.setLocationRelativeTo(null);

        JButton manageUsersButton = new JButton("Kelola Pengguna");
        JButton manageIuranButton = new JButton("Kelola Iuran");
        JButton logoutButton = new JButton("Logout");

        manageUsersButton.addActionListener(e -> manageUsers());
        manageIuranButton.addActionListener(e -> manageIuran());
        logoutButton.addActionListener(e -> {
            adminFrame.dispose();
            createLoginWindow();
        });

        adminFrame.add(manageUsersButton);
        adminFrame.add(manageIuranButton);
        adminFrame.add(logoutButton);
        adminFrame.setVisible(true);
    }

    private static void createUserMenu (String username) {
        JFrame userFrame = new JFrame("Menu Pengguna");
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setSize(400, 225);
        userFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        userFrame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        userFrame.setLocationRelativeTo(null);

        JButton viewStatusButton = new JButton("Lihat Status Iuran");
        JButton payIuranButton = new JButton("Bayar Iuran");
        JButton viewHistoryButton = new JButton("Lihat Riwayat Pembayaran ");
        JButton logoutButton = new JButton("Logout");

        viewStatusButton.addActionListener(e -> viewStatusIuran(username));
        payIuranButton.addActionListener(e -> payIuran(username));
        viewHistoryButton.addActionListener(e -> viewPaymentHistory(username));
        logoutButton.addActionListener(e -> {
            userFrame.dispose();
            createLoginWindow();
        });

        userFrame.add(viewStatusButton);
        userFrame.add(payIuranButton);
        userFrame.add(viewHistoryButton);
        userFrame.add(logoutButton);
        userFrame.setVisible(true);
    }

    private static void manageUsers() {
        JFrame manageUsersFrame = new JFrame("Kelola Pengguna");
        manageUsersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        manageUsersFrame.setSize(400, 200);
        manageUsersFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 15));
        manageUsersFrame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,20,30,20));
        manageUsersFrame.setLocationRelativeTo(null);

        JButton addUserButton = new JButton("Tambah Pengguna");
        JButton viewUsersButton = new JButton("Lihat Pengguna");
        JButton editUserButton = new JButton("Edit Pengguna");
        JButton deleteUserButton = new JButton("Hapus Pengguna");
        JButton backButton = new JButton("Kembali");

        addUserButton.addActionListener(e -> addUser ());
        viewUsersButton.addActionListener(e -> viewUsers());
        editUserButton.addActionListener(e -> editUser ());
        deleteUserButton.addActionListener(e -> deleteUser ());
        backButton.addActionListener(e -> manageUsersFrame.dispose());

        manageUsersFrame.add(addUserButton);
        manageUsersFrame.add(viewUsersButton);
        manageUsersFrame.add(editUserButton);
        manageUsersFrame.add(deleteUserButton);
        manageUsersFrame.add(backButton);
        manageUsersFrame.setVisible(true);
    }

    private static void manageIuran() {
        JFrame manageIuranFrame = new JFrame("Kelola Iuran");
        manageIuranFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        manageIuranFrame.setSize(400, 200);
        manageIuranFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        manageIuranFrame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        manageIuranFrame.setLocationRelativeTo(null);

        JButton addIuranButton = new JButton("Tambah Iuran");
        JButton viewIuranButton = new JButton("Lihat Iuran");
        JButton viewReportsButton = new JButton("Lihat Laporan");
        JButton backButton = new JButton("Kembali");

        addIuranButton.addActionListener(e -> addIuran());
        viewIuranButton.addActionListener(e -> viewIuran());
        viewReportsButton.addActionListener(e -> viewReports());
        backButton.addActionListener(e -> manageIuranFrame.dispose());

        manageIuranFrame.add(addIuranButton);
        manageIuranFrame.add(viewIuranButton);
        manageIuranFrame.add(viewReportsButton);
        manageIuranFrame.add(backButton);
        manageIuranFrame.setVisible(true);
    }

    private static void addUser () {
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Tambah Pengguna", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            users.add(new User(username, password));
            saveUsers();
            JOptionPane.showMessageDialog(null, "Pengguna berhasil ditambahkan.");
        }
    }

    private static void viewUsers() {
        StringBuilder userList = new StringBuilder("Daftar Pengguna:\n");
        for (User  user : users) {
            userList.append("Username: ").append(user.username).append("\n");
        }
        JOptionPane.showMessageDialog(null, userList.toString());
    }

    private static void editUser () {
        String username = JOptionPane.showInputDialog("Masukkan username yang ingin diedit:");
        User user = users.stream().filter(u -> u.username.equals(username)).findFirst().orElse(null);

        if (user != null) {
            String newPassword = JOptionPane.showInputDialog("Masukkan password baru:");
            user.password = newPassword;
            saveUsers();
            JOptionPane.showMessageDialog(null, "Pengguna berhasil diperbarui.");
        } else {
            JOptionPane.showMessageDialog(null, "Pengguna tidak ditemukan.");
        }
    }

    private static void deleteUser () {
        String username = JOptionPane.showInputDialog("Masukkan username yang ingin dihapus:");
        User user = users.stream().filter(u -> u.username.equals(username)).findFirst().orElse(null);

        if (user != null) {
            users.remove(user);
            saveUsers();
            JOptionPane.showMessageDialog(null, "Pengguna berhasil dihapus.");
        } else {
            JOptionPane.showMessageDialog(null, "Pengguna tidak ditemukan.");
        }
    }

    private static void addIuran() {
        JTextField nameField = new JTextField();
        JTextField amountField = new JTextField();
        Object[] message = {
                "Nama Iuran:", nameField,
                "Jumlah Iuran:", amountField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Tambah Iuran", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            double amount = Double.parseDouble(amountField.getText());
            iurans.add(new Iuran(name, amount));
            saveIurans();
            JOptionPane.showMessageDialog(null, "Iuran berhasil ditambahkan.");
        }
    }

    private static void viewIuran() {
        StringBuilder iuranList = new StringBuilder("Daftar Iuran:\n");
        for (Iuran iuran : iurans) {
            iuranList.append("Nama: ").append(iuran.name).append(", Jumlah: ").append(iuran.amount).append("\n");
        }
        JOptionPane.showMessageDialog(null, iuranList.toString());
    }

    private static void viewReports() {
        StringBuilder report = new StringBuilder("Laporan Iuran:\n");
        double totalPayments = 0;
        List<String> paidUsers = new ArrayList<>();
        List<String> unpaidUsers = new ArrayList<>();

        for (Iuran iuran : iurans) {
            boolean hasPaid = false;
            for (Payment payment : payments) {
                if (payment.iuranName.equals(iuran.name)) {
                    totalPayments += payment.amount;
                    hasPaid = true;
                    paidUsers.add(payment.username + " telah membayar " + iuran.name);
                }
            }
            if (!hasPaid) {
                for (User  user : users) {
                    unpaidUsers.add(user.username + " belum membayar " + iuran.name);
                }
            }
        }

        report.append("Total Pembayaran yang Diterima: ").append(totalPayments).append("\n");
        report.append("Pengguna yang Telah Membayar Iuran:\n");
        for (String paidUser  : paidUsers) {
            report.append(paidUser ).append("\n");
        }
        report.append("Pengguna yang Belum Membayar Iuran:\n");
        for (String unpaidUser  : unpaidUsers) {
            report.append(unpaidUser ).append("\n");
        }
        JOptionPane.showMessageDialog(null, report.toString());
    }

    private static void viewStatusIuran(String username) {
        StringBuilder statusList = new StringBuilder("Status Iuran untuk " + username + ":\n");
        for (Iuran iuran : iurans) {
            boolean paid = payments.stream().anyMatch(p -> p.username.equals(username) && p.iuranName.equals(iuran.name));
            statusList.append("Iuran: ").append(iuran.name).append(", Status: ").append(paid ? "Sudah Dibayar" : "Belum Dibayar").append("\n");
        }
        JOptionPane.showMessageDialog(null, statusList.toString());
    }

    private static void payIuran(String username) {
        StringBuilder iuranList = new StringBuilder("Daftar Iuran:\n");
        for (Iuran iuran : iurans) {
            iuranList.append("Nama: ").append(iuran.name).append(", Jumlah: ").append(iuran.amount).append("\n");
        }
        String iuranName = JOptionPane.showInputDialog(iuranList.toString() + "Masukkan nama iuran yang ingin dibayar:");
        Iuran selectedIuran = iurans.stream().filter(i -> i.name.equals(iuranName)).findFirst().orElse(null);

        if (selectedIuran != null) {
            String amountStr = JOptionPane.showInputDialog("Masukkan jumlah pembayaran:");
            double amount = Double.parseDouble(amountStr);
            if (amount == selectedIuran.amount) {
                String date = JOptionPane.showInputDialog("Masukkan tanggal pembayaran:");
                String imagePath = JOptionPane.showInputDialog("Masukkan path bukti gambar:");
                payments.add(new Payment(username, iuranName, amount, date, imagePath));
                savePayments();
                JOptionPane.showMessageDialog(null, "Pembayaran berhasil.");
            } else {
                JOptionPane.showMessageDialog(null, "Jumlah pembayaran tidak sesuai.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Iuran tidak ditemukan.");
        }
    }

    private static void viewPaymentHistory(String username) {
        StringBuilder historyList = new StringBuilder("Riwayat Pembayaran untuk " + username + ":\n");
        for (Payment payment : payments) {
            if (payment.username .equals(username)) {
                historyList.append("Iuran: ").append(payment.iuranName)
                        .append(", Jumlah: ").append(payment.amount)
                        .append(", Tanggal: ").append(payment.date)
                        .append(", Bukti: ").append(payment.imagePath).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, historyList.toString());
    }

    private static void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv"))) {
            for (User  user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private static void saveIurans() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("iurans.csv"))) {
            for (Iuran iuran : iurans) {
                writer.write(iuran.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving iurans: " + e.getMessage());
        }
    }

    private static void loadIurans() {
        try (BufferedReader reader = new BufferedReader(new FileReader("iurans.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    iurans.add(new Iuran(parts[0], Double.parseDouble(parts[1])));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading iurans: " + e.getMessage());
        }
    }

    private static void savePayments() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("payments.csv"))) {
            for (Payment payment : payments) {
                writer.write(payment.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving payments: " + e.getMessage());
        }
    }

    private static void loadPayments() {
        try (BufferedReader reader = new BufferedReader(new FileReader("payments.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    payments.add(new Payment(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3], parts[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading payments: " + e.getMessage());
        }
    }
}
