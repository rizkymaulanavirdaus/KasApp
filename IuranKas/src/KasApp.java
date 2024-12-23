import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KasApp {
    private static List<User> users = new ArrayList<>();
    private static List<Iuran> iurans = new ArrayList<>();
    private static List<Payment> payments = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadUsers();
        loadIurans();
        loadPayments();
        // Menambahkan pengguna admin default jika tidak ada
        if (users.isEmpty()) {
            users.add(new User("admin", "admin123"));
        }
        login();
    }

    private static void login() {
        while (true) {
            System.out.println("Selamat datang di Aplikasi Iuran Kas");
            System.out.print("Masukkan username: ");
            String username = scanner.nextLine();
            System.out.print("Masukkan password: ");
            String password = scanner.nextLine();

            if (isAdmin(username, password)) {
                adminMenu();
            } else if (isUser (username, password)) {
                userMenu(username);
            } else {
                System.out.println("Kredensial salah! Silakan coba lagi.");
            }
        }
    }

    private static boolean isAdmin(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }

    private static boolean isUser (String username, String password) {
        return users.stream().anyMatch(user -> user.username.equals(username) && user.password.equals(password));
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nMenu Admin:");
            System.out.println("1. Kelola Pengguna");
            System.out.println("2. Kelola Iuran");
            System.out.println("3. Logout");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    manageUsers();
                    break;
                case 2:
                    manageIuran();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void manageUsers() {
        while (true) {
            System.out.println("\nKelola Pengguna:");
            System.out.println("1. Tambah Pengguna");
            System.out.println("2. Lihat Pengguna");
            System.out.println("3. Edit Pengguna");
            System.out.println("4. Hapus Pengguna");
            System.out.println("5. Kembali ke Menu Admin");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    addUser ();
                    break;
                case 2:
                    viewUsers();
                    break;
                case 3:
                    editUser ();
                    break;
                case 4:
                    deleteUser ();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void manageIuran() {
        while (true) {
            System.out.println("\nKelola Iuran:");
            System.out.println(" 1. Tambah Iuran");
            System.out.println("2. Lihat Iuran");
            System.out.println("3. Lihat Laporan");
            System.out.println("4. Kembali ke Menu Admin");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    addIuran();
                    break;
                case 2:
                    viewIuran();
                    break;
                case 3:
                    viewReports();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void userMenu(String username) {
        while (true) {
            System.out.println("\nMenu Pengguna:");
            System.out.println("1. Lihat Status Iuran");
            System.out.println("2. Bayar Iuran");
            System.out.println("3. Lihat Riwayat Pembayaran");
            System.out.println("4. Logout");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    viewStatusIuran(username);
                    break;
                case 2:
                    payIuran(username);
                    break;
                case 3:
                    viewPaymentHistory(username);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void addUser  () {
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();
        users.add(new User(username, password));
        saveUsers();
        System.out.println("Pengguna berhasil ditambahkan.");
    }

    private static void viewUsers() {
        System.out.println("Daftar Pengguna:");
        for (User   user : users) {
            System.out.println("Username: " + user.username);
        }
    }

    private static void editUser  () {
        System.out.print("Masukkan username yang ingin diedit: ");
        String username = scanner.nextLine();
        User user = users.stream().filter(u -> u.username.equals(username)).findFirst().orElse(null);

        if (user != null) {
            System.out.print("Masukkan password baru: ");
            String newPassword = scanner.nextLine();
            user.password = newPassword;
            saveUsers();
            System.out.println("Pengguna berhasil diperbarui.");
        } else {
            System.out.println("Pengguna tidak ditemukan.");
        }
    }

    private static void deleteUser  () {
        System.out.print("Masukkan username yang ingin dihapus: ");
        String username = scanner.nextLine();
        User user = users.stream().filter(u -> u.username.equals(username)).findFirst().orElse(null);

        if (user != null) {
            users.remove(user);
            saveUsers();
            System.out.println("Pengguna berhasil dihapus.");
        } else {
            System.out.println("Pengguna tidak ditemukan.");
        }
    }

    private static void addIuran() {
        System.out.print("Masukkan nama iuran: ");
        String name = scanner.nextLine();
        System.out.print("Masukkan jumlah iuran: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        iurans.add(new Iuran(name, amount));
        saveIurans();
        System.out.println("Iuran berhasil ditambahkan.");
    }

    private static void viewIuran() {
        System.out.println("Daftar Iuran:");
        for (Iuran iuran : iurans) {
            System.out.println("Nama: " + iuran.name + ", Jumlah: " + iuran.amount);
        }
    }

    private static void viewReports() {
        System.out.println("Laporan Iuran:");
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

        System.out.println("Total Pembayaran yang Diterima: " + totalPayments);
        System.out.println("Pengguna yang Telah Membayar Iuran:");
        for (String paidUser  : paidUsers) {
            System.out.println(paidUser );
        }
        System.out.println("Pengguna yang Belum Membayar Iuran:");
        for (String unpaidUser  : unpaidUsers) {
            System.out.println(unpaidUser );
        }
    }

    private static void viewStatusIuran(String username) {
        System.out.println("Status Iuran untuk " + username + ":");
        for (Iuran iuran : iurans) {
            boolean paid = payments.stream().anyMatch(p -> p.username.equals(username) && p.iuranName.equals(iuran.name));
            System.out.println("Iuran: " + iuran.name + ", Status: " + (paid ? "Sudah Dibayar" : "Belum Dibayar"));
        }
    }

    private static void payIuran(String username) {
        System.out.println("Daftar Iuran:");
        for (Iuran iuran : iurans) {
            System.out.println("Nama: " + iuran.name + ", Jumlah: " + iuran.amount);
        }
        System.out.print("Masukkan nama iuran yang ingin dibayar: ");
        String iuranName = scanner.nextLine();
        Iuran selectedIuran = iurans.stream().filter(i -> i.name.equals(iuranName)).findFirst().orElse(null);

        if (selectedIuran != null) {
            System.out.print("Masukkan jumlah pembayaran: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Clear buffer
            if (amount == selectedIuran.amount) {
                System.out.print("Masukkan tanggal pembayaran: ");
                String date = scanner.nextLine();
                System.out.print("Masukkan path bukti gambar: ");
                String imagePath = scanner.nextLine(); // Input path gambar
                payments.add(new Payment(username, iuranName, amount, date, imagePath));
                savePayments();
                System.out.println("Pembayaran berhasil.");
            } else {
                System.out.println("Jumlah pembayaran tidak sesu ai.");
            }
        } else {
            System.out.println("Iuran tidak ditemukan.");
        }
    }

    private static void viewPaymentHistory(String username) {
        System.out.println("Riwayat Pembayaran untuk " + username + ":");
        for (Payment payment : payments) {
            if (payment.username.equals(username)) {
                System.out.println("Iuran: " + payment.iuranName + ", Jumlah: " + payment.amount + ", Tanggal: " + payment.date + ", Bukti: " + payment.imagePath);
            }
        }
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
