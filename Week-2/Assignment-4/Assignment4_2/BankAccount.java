public class BankAccount {
    private int balance;

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(int amount) {
        this.balance += amount;
        System.out.println("Deposit: " + amount + ", current balance: " + this.balance);
    }

    public synchronized void withdraw(int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            System.out.println("Withdraw: " + amount + ", current balance: " + this.balance);
        } else {
            System.out.println("Insufficient balance for withdrawal: Current Balance: " + this.balance);
        }
    }

    public synchronized int getBalance() {
        return this.balance;
    }
}