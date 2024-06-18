public interface AccountInterface {
    void withdraw(Double amount);
    void deposit(Double amount);
    Double queryBalance();
    Boolean login(String accountId, String password);
    default Boolean logout(){
        System.out.println("Log out...");
        return true;
    }
}