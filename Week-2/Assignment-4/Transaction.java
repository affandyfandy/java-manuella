public class Transaction implements Runnable {
    private BankAccount account;
    private boolean isDeposit;
    private int amount;

    public Transaction(BankAccount account, boolean isDeposit, int amount) {
        this.account = account;
        this.isDeposit = isDeposit;
        this.amount = amount;
    }

    @Override
    public void run() {
        if (this.isDeposit) {
            this.account.deposit(this.amount);
        } else {
            this.account.withdraw(this.amount);
        }
    }
}