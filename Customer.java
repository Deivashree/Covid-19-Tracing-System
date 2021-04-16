import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import java.time.format.DateTimeFormatter;

public class Customer{
    private static String name;
    private String phoneNumber;
    private String password;
    private String status;

    public static List<List<String>> ReadingFileCustomer() { // Reads customerdata.csv
        File file = new File("customerdata.csv");
        BufferedReader br = null;
        List<List<String>> CustArr = new ArrayList<>(); // Creates list of list of strings to store inside an array

        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) { // Stores the value of customers inside an array
                String[] values = line.trim().split(",");
                CustArr.add(Arrays.asList(values));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File is not found sorry " + file.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file " + file.toString());
        } catch (NullPointerException ex) {
            // File was probably never opened!
        }
        return CustArr;
    }

    public void WritingFileCustomer() { //Register user data to test.csv 
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter your name: ");
        this.name = input.nextLine();

        System.out.print("Enter your phone number: ");
        this.phoneNumber = input.nextLine();

        System.out.print("Enter a password: ");
        this.password = input.next();

        this.status = "Normal";

        System.out.println("\nRegistration successful!\n");

        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter("customerdata.csv", true));
            writer.write('\n' + name + ',' + phoneNumber + ',' + password + ',' + status);
            writer.close();
        } catch (IOException e) {
            System.out.println("File not found!");
        }

    }

    public boolean CustomerSignIn() { //Sign in Method for Customer
        boolean signInValue = false;

        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Sign In System");

            System.out.print("Enter your name: ");
            this.name = input.next();

            System.out.print("Enter your phone number: ");
            this.phoneNumber = input.next();

            System.out.print("Enter your password: ");
            this.password = input.next();

            for (int i = 1; i < ReadingFileCustomer().size(); i++) {

                signInValue = (ReadingFileCustomer().get(i).get(0).equals(name))
                        & (ReadingFileCustomer().get(i).get(1).equals(phoneNumber))
                        & (ReadingFileCustomer().get(i).get(2).equals(password));

                if (signInValue == true) {
                    break;
                }

            }
            
            int customerLine = 0;

            for(int i = 0; i < ReadingFileCustomer().size(); i++) {
			if(ReadingFileCustomer().get(i).get(0).equals(name)) {
                customerLine = i; 
                break;
                }
            }

            this.status = ReadingFileCustomer().get(customerLine).get(3);
          
            if (signInValue == true) {
                System.out.println("\nSign in successful!\n");
            } else {
                System.out.println("\nWrong input! Please try again.\n");
            }

        } while (signInValue != true);

        // if customer exit return this
        return signInValue;
    }

    public List<List<String>> CustomerCheckIn() { //Check in to a shop method for customer
        Scanner input = new Scanner(System.in);

        int inputShop = 0;
        String shopChoice = "";

        System.out.println("Shop List\n");
        System.out.println("1.LouisVuitton");
        System.out.println("2.Gucci");
        System.out.println("3.Zara");
        System.out.println("4.Uniqlo");
        System.out.print("Choose a shop to check-in: ");

        inputShop = input.nextInt();

        switch (inputShop) {
            case 1:
                shopChoice = "LouisVuitton";
                break;
            case 2:
                shopChoice = "Gucci";
                break;
            case 3:
                shopChoice = "Zara";
                break;
            case 4:
                shopChoice = "Uniqlo";
                break;
            default:
                System.out.println("Wrong input! Please enter again.");
                break;
        }

        List<List<String>> CustShopHistory = new ArrayList<>();

        LocalDate date = LocalDate.now(); // Gets the current date
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalTime time = LocalTime.now(); // Gets the current time

        DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String line = name + " " + "Visited: " + shopChoice + " " + date.format(dateformatter) +
                " " + time.format(timeformatter);
        
        System.out.println(line);
        CustShopHistory.add(Arrays.asList(line));

        System.out.println();
        // write the shop choice into a csv file
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter("shopHistory.csv", true));
            writer.write("\n" + date.format(dateformatter) + ',' + time.format(timeformatter) + ',' + name + ',' 
                        + shopChoice + ',' + status);
            writer.close();
        } catch (IOException e) {
            System.out.println("File not found!");
        }

        return CustShopHistory;
    }

    public static List<List<String>> ViewCustomerShopHistory() { // View the customer's visited shops history

        File file = new File("shopHistory.csv");
        List<List<String>> ShopHistoryList = new ArrayList<>();
        BufferedReader br = null;


        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) { // Stores the value of customers inside an array
                String[] values = line.trim().split(",");
                ShopHistoryList.add(Arrays.asList(values));
            }

            int count = 0;

            for (int i = 1; i < ShopHistoryList.size(); i++) {
                if (ShopHistoryList.get(i).get(2).equals(name)) {
                count += 1;
                System.out.println(count + "   " + ShopHistoryList.get(i).get(0) + " "
                + ShopHistoryList.get(i).get(1) + " " + ShopHistoryList.get(i).get(3));
            }
            
        }
        System.out.println("\n");
        } catch (FileNotFoundException e) {
            System.out.println("File is not found sorry " + file.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file " + file.toString());
        }
        catch(NullPointerException ex){
            // File was probably never opened!
        }

        return ShopHistoryList;
    }

    public void ViewCustomerStatus(){ // Views the status for the current customer signed in

        int customerLine = 0;

        for(int i = 0; i < ReadingFileCustomer().size(); i++) {
			if(ReadingFileCustomer().get(i).get(0).equals(name)) {
                customerLine = i; 
                break;
            }
        }

        name = ReadingFileCustomer().get(customerLine).get(0);
        status = ReadingFileCustomer().get(customerLine).get(3);
        System.out.println("Status\n" + name + ": " + status+"\n");
    }

}