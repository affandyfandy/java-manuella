public class CurrentAccount implements Account {
    private Integer accountId;
    private String password;
    private Double balance;
    private static final Double TRANSACTION_FEE = 1.0;

    public CurrentAccount(Integer accountId, String password){
        this.accountId = accountId;
        this.password = password;
        this.balance = Double.valueOf(0);
    }

    public Integer getAccountId(){
        return this.accountId;
    }

    public void withdraw(Double amount){
        if (this.balance < (amount + TRANSACTION_FEE)){
            System.out.println("Not enough balance.");
        }
        else{
            this.balance -= (amount + TRANSACTION_FEE);
            System.out.println("Withdraw " + amount + ".");
        }
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
}