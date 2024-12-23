class Iuran {
    String name;
    double amount;

    public Iuran(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return name + "," + amount;
    }
}

class Payment {
    String username;
    String iuranName;
    double amount;
    String date;
    String imagePath; // Menambahkan field untuk path gambar

    public Payment(String username, String iuranName, double amount, String date, String imagePath) {
        this.username = username;
        this.iuranName = iuranName;
        this.amount = amount;
        this.date = date;
        this.imagePath = imagePath; // Menyimpan path gambar
    }

    @Override
    public String toString() {
        return username + "," + iuranName + "," + amount + "," + date + "," + imagePath; // Menyimpan path gambar di CSV
    }
}
