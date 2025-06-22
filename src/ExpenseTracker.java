//import java.sql.SQLOutput;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//import java.util.Date;
//import java.text.SimpleDateFormat;
//
//class Expense{
//   private String Description;
//   private double amount;
//   private String category;
//   private Date date;
//
//
//   public Expense(String Description, double amount, String category, Date date){
//       this.Description = Description;
//       this.amount = amount;
//       this.category = category;
//       this.date = date;
//   }
//
//   public String getDescription(){
//       return Description;
//   }
//
//   public double getAmount(){
//       return amount;
//   }
//
//   public String getCategory(){
//       return category();
//   }
//   public Date getdate(){
//       return date();
//   }
//
//
//    @Override
//    public String toString() {
//        SimpleDateFormat formatter = new SimpleDateFormat("YYY-MM-DD");
//        return Description + "| Amount $" + amount + "| Category" + category + "| Date" + formatter.format(date);
//    }
//}
//
//
//
//public class ExpenseTracker {
//
//    private List<Expense> expenses;
//    private Scanner scanner;
//
//    public ExpenseTracker(){
//        expenses =  new ArrayList<>();
//        scanner  = new Scanner(System.in);
//    }
//
//    public void addExpense(){
//        System.out.println("Enter the description of the expense");
//        String Description = scanner.nextLine();
//        System.out.println("Enter the amount");
//        Double amount = scanner.nextDouble();
//        scanner.nextLine();
//        System.out.println("Enter the Category");
//        String Category = scanner.nextLine();
//        Date Date = new Date();
//
//
//        expenses.add(new Expense(Description, amount, Category, Date));
//        System.out.println("Expense Added");
//    }
//
//    public void viewExpense(){
//        if(expenses.isEmpty()){
//            System.out.println("There is no Expense left");
//        }else{
//            for(int i = 0; i<expenses.size(); i++){
//                System.out.println((i+1)+". "+expenses.get(i));
//            }
//        }
//    }
//
//    public boolean viewExpensesByCategory() {
//        System.out.println("Enter the Category to filter by: ");
//        String Category = scanner.nextLine();
//        boolean found = false;
//
//        System.out.println("Expenses Category ' " + Category + "':");
//        for (Expense expense : expenses) {
//            if (expense.getCategory().equalsIgnoreCase(Category)) {
//                System.out.println(expense);
//                found = true;
//            }
//        }
//        if(!found){
//            System.out.println("Expense did not found in this category: ");
//        }
//        return found;
//    }
//
//    public void run(){
//        while(true){
//            System.out.println("/nExpense Tracker");
//            System.out.println("1. Add Expenses");
//            System.out.println("2. View All Expenses");
//            System.out.println("3. View Expenses by Category");
//            System.out.println("4. Exit");
//            System.out.println("Choose an option");
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//
//            switch(choice){
//                case 1:
//                   addExpense();
//                   break;
//                case 2:
//                    viewExpense();
//                    break;
//                case 3:
//                    viewExpensesByCategory();
//                case 4:
//                    System.out.println("Existing... ");
//                    return;
//                default:
//                    System.out.println("Invalid choice, pleas try again");
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//      ExpenseTracker et = new ExpenseTracker();
//      et.run();
//    }
//}
