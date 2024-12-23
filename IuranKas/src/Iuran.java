class Iuran {
    String name;
    double amount;

    public Iuran(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    static class Payment {
        String username;
        String iuranName;
        double amount;
        String date;

        public Payment(String username, String iuranName, double amount, String date) {
            this.username = username;
            this.iuranName = iuranName;
            this.amount = amount;
            this.date = date;
        }
    }
}
