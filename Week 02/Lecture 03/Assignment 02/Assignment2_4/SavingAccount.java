public class SavingAccount implements Account {
    private Integer accountId;
    private String password;
    private Double balance;
    private Double interestRate;

    public SavingAccount(Integer accountId, String password, Double interestRate){
        this.accountId = accountId;
        this.password = password;
        this.balance = Double.valueOf(0);
        this.interestRate = interestRate;
    }

    public Integer getAccountId(){
        return this.accountId;
    }

    public void withdraw(Double amount){
        this.balance -= amount;
        System.out.println("Withdraw " + amount + ".");
    }

    public void deposit(Double amount){
        this.balance += amount;
        System.out.println("Deposit " + amount + ".");
    }

    public Double queryBalance(){
        return this.balance;
    }

    public Boolean login(Integer accountId, String password){
        if (this.accountId.equals(accountId) && this.password.equals(password)){
            System.out.println("Login success.");
            return true;
        }
        else{
            System.out.println("Login failed.");
            return false;
        }
    }

    public void updateBalance(Integer year){
        this.balance += this.balance * this.interestRate;
    }
}