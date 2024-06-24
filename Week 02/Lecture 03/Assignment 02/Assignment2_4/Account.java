public interface Account {
    Integer getAccountId();
    void withdraw(Double amount);
    void deposit(Double amount);
    Double queryBalance();
    Boolean login(Integer accountId, String password);
    default Boolean logout(){
        System.out.println("Log out...");
        return true;
    }
    static void printAccountInfo(Account account) {
        System.out.println("Account ID: " + account.getAccountId());
        System.out.println("Balance: $" + account.queryBalance());
    }
}