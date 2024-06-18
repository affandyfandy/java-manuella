import java.util.Scanner;

public class Lab2 {
    private static String[] menu = {"Bread", "Pudding", "Soda", "Apple", "Rice"};

    public static void main(String[] args) throws Lab2Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select item:");
        int userInput = scanner.nextInt();

        try {
            String selectedMenuItem = getMenuOption(userInput);
            System.out.println("You selected: " + selectedMenuItem);
        } catch (Lab2Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String getMenuOption(int number) throws Lab2Exception {
        if (number < 1 || number > menu.length) {
            throw new Lab2Exception("Invalid menu option. Please enter a number between 1 and 5");
        }
        return menu[number - 1];
    }
}
