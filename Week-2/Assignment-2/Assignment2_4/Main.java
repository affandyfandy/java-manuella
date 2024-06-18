public class Main {

    public static void main(String[] args) {
        testCurrentAccount();
        testSavingAccount();
    }

    public static void testCurrentAccount() {
        System.out.println("Testing CurrentAccount:");

        Account currentAccount = new CurrentAccount(1001, "pass123");

        System.out.println("Testing login:");
        currentAccount.login(1001, "pass123");
        currentAccount.login(1002, "pass123"); // Incorrect password

        System.out.println("\nTesting deposit:");
        currentAccount.deposit(500.0);
        System.out.println("Current balance: $" + currentAccount.queryBalance());

        System.out.println("\nTesting withdraw:");
        currentAccount.withdraw(200.0);
        System.out.println("Current balance after withdrawal: $" + currentAccount.queryBalance());

        System.out.println("\nTesting logout:");
        currentAccount.logout();

        System.out.println("\nPrinting account info:");
        Account.printAccountInfo(currentAccount);
    }

    public static void testSavingAccount() {
        System.out.println("\nTesting SavingAccount:");

        Account savingAccount = new SavingAccount(2001, "pass456", 0.05);

        System.out.println("Testing login:");
        savingAccount.login(2001, "pass456");
        savingAccount.login(2002, "pass456"); // Incorrect password

        System.out.println("\nTesting deposit:");
        savingAccount.deposit(1000.0);
        System.out.println("Current balance: $" + savingAccount.queryBalance());

        System.out.println("\nTesting withdraw:");
        savingAccount.withdraw(200.0);
        System.out.println("Current balance after withdrawal: $" + savingAccount.queryBalance());

        System.out.println("\nTesting interest rate update (1 year):");
        ((SavingAccount) savingAccount).updateBalance(1);
        System.out.println("Balance after one year: $" + savingAccount.queryBalance());

        System.out.println("\nPrinting account info:");
        Account.printAccountInfo(savingAccount);
    }
}
