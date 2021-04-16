import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Admin{

  private static List<List<String>> ShopHistoryList = new ArrayList<>(); //Updates shopHistory.csv after randomizing
  
  public static List<List<String>> getShopHistoryList() {
    return ShopHistoryList;
  }

  public static void setShopHistoryList(List<List<String>> shopHistoryList) {
    ShopHistoryList = shopHistoryList;
  }

  private static List<List<String>> CustomerLists = new ArrayList<>(); //Updates the customer status in customerdata.csv after randomizing

  private static List<List<String>> ShopLists = new ArrayList<>(); //Updates the shop status in shop.csv after randomizing

  public static void ReadCustomerHistory() { //Reads out customerdata.csv
    String customerName;
    String customerPhoneNumber;
    String customerStatus;

    String headername1 = "No";
    String headername2 = "Name";
    String headername3 = "Phone";
    String headername4 = "Status";

    System.out.printf("%-5s %-15s %-15s %-15s %n", headername1, headername2, headername3, headername4);

    for (int i = 1; i < Customer.ReadingFileCustomer().size(); i++) {

      customerName = Customer.ReadingFileCustomer().get(i).get(0);
      customerPhoneNumber = Customer.ReadingFileCustomer().get(i).get(1);
      customerStatus = Customer.ReadingFileCustomer().get(i).get(3);

      System.out.printf("%-5s %-15s %-15s %-15s %n", i, customerName, customerPhoneNumber, customerStatus);
    }
    System.out.println("\n");

  }

  public static void ReadShopHistory() { //Reads the data from shop.csv
    String shopName;
    String shopPhoneNumber;
    String shopManager;
    String shopStatus;

    String headername1 = "No";
    String headername2 = "Name";
    String headername3 = "Phone";
    String headername4 = "Manager";
    String headername5 = "Status";

    System.out.printf("%-5s %-15s %-15s %-15s %-15s %n", headername1, headername2, headername3, headername4,
        headername5);

    for (int i = 1; i < Shop.ReadShopFile().size(); i++) {

      shopName = Shop.ReadShopFile().get(i).get(0);
      shopPhoneNumber = Shop.ReadShopFile().get(i).get(1);
      shopManager = Shop.ReadShopFile().get(i).get(3);
      shopStatus = Shop.ReadShopFile().get(i).get(2);

      System.out.printf("%-5s %-15s %-15s %-15s %-15s %n", i, shopName, shopPhoneNumber, shopManager, shopStatus);
    }
    System.out.println("\n");
  }

  public static void ReadShopVisitHistory() { //Reading for checked in history from customers

    File file = new File("shopHistory.csv");
    BufferedReader br = null;

    ShopHistoryList.clear();

    try {
      FileReader fr = new FileReader(file);
      br = new BufferedReader(fr);

      String line;

      while ((line = br.readLine()) != null) { // Stores the value of customers inside an array
        String[] values = line.trim().split(",");
        ShopHistoryList.add(Arrays.asList(values));
      }

    } catch (FileNotFoundException e) {
      System.out.println("File is not found sorry " + file.toString());
    } catch (IOException e) {
      System.out.println("Unable to read file " + file.toString());
    } catch (NullPointerException ex) {
      // File was probably never opened!
    }

  }

  public static List<List<String>> MasterVisitHistory() { //Prints out the current master visit history for admin usage.

    // Sort the master visit history by date and time
    System.out.format("%3s %15s %15s %17s %15s %15s", "No ", "Date", "Time", "Customer", "Shop", "Status\n");

    ReadShopVisitHistory();

    for (int i = 1; i < ShopHistoryList.size(); i++) {

      System.out.format("%3s %15s %15s %17s %15s %15s", i, ShopHistoryList.get(i).get(0),
      ShopHistoryList.get(i).get(1), ShopHistoryList.get(i).get(2), ShopHistoryList.get(i).get(3),
      ShopHistoryList.get(i).get(4) + "\n");
    }

    System.out.println("\n");

    return ShopHistoryList;
  }

  static List<List<String>> StoreShopHistoryData = new ArrayList<>();

  public static void RandomMasterVisitHistory() {//Method to generate 30 random visits into master visit history

    ReadShopVisitHistory();

    if(StoreShopHistoryData.isEmpty()){
      StoreShopHistoryData = new ArrayList<>(ShopHistoryList);
    }

    ShopHistoryList.clear();

    ShopHistoryList = new ArrayList<>(StoreShopHistoryData);
    
    int randomCustomer = 0;
    int randomShop = 0;
    int randomHour = 0;
    int randomMinutes = 0;
    LocalDateTime datetime;
    DateTimeFormatter dateformatter;
    DateTimeFormatter timeformatter;

    Random random = new Random();

    System.out.println();

    List<String> dateTimeString = new ArrayList<>(); //Sort the original data before randomizing by date and time

    for (int i = 0; i < 30; i++) {

      randomCustomer = ThreadLocalRandom.current().nextInt(1, Customer.ReadingFileCustomer().size());
      randomShop = ThreadLocalRandom.current().nextInt(1, Shop.ReadShopFile().size());
      randomHour = random.nextInt(24 - 0 + 1) + 0;
      randomMinutes = random.nextInt(60 - 0 + 1) + 0;

      datetime = LocalDateTime.now().minusHours(randomHour).minusMinutes(randomMinutes);
      dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      timeformatter = DateTimeFormatter.ofPattern("HH:mm:ss");
      
      String line = datetime.format(dateformatter) + "," + datetime.format(timeformatter) + ","
          + Customer.ReadingFileCustomer().get(randomCustomer).get(0) + "," + Shop.ReadShopFile().get(randomShop).get(0) + "," 
          + "Normal"; //composition

      String[] values = line.trim().split(",");
      ShopHistoryList.add(Arrays.asList(values)); //aggregation 
    }

    for(int r = 1; r < ShopHistoryList.size(); r++){
      String str = ShopHistoryList.get(r).get(0) + " " + ShopHistoryList.get(r).get(1);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
      LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
      String DateTimeList = str;
      dateTimeString.add(DateTimeList);
    }
    
    Collections.sort(dateTimeString, new Comparator<String>() {
      SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      public int compare(String o1, String o2) {
      try {
        return f.parse(o1).compareTo(f.parse(o2));
          } catch (ParseException e) {
            throw new IllegalArgumentException(e);
          }
      }
    });

    System.out.format("%3s %15s %15s %17s %15s %15s", "No ", "Date", "Time", "Customer", "Shop", "Status\n");

    for (int j = 1; j < ShopHistoryList.size(); j++) {
      ShopHistoryList.get(j).set(4, "Normal"); // set the status of customers in shopHistory.csv to normal 
    }

      List<List<String>> tempList = new ArrayList<>();

      for(int g = 0; g < dateTimeString.size(); g++){

        for(int f = 1; f < ShopHistoryList.size(); f++){
          String line1 = ShopHistoryList.get(f).get(0) + " " + ShopHistoryList.get(f).get(1);

         if(dateTimeString.get(g).equals(line1)){// if time string == shophistory.csv
         
          String sortedLine = ShopHistoryList.get(f).get(0) + "," +
          ShopHistoryList.get(f).get(1) + "," + ShopHistoryList.get(f).get(2) + "," + ShopHistoryList.get(f).get(3) + "," +
          ShopHistoryList.get(f).get(4); 
          String[] values = sortedLine.trim().split(",");
          tempList.add(Arrays.asList(values));
          break;
        } 
      } 
    }
    
    ShopHistoryList.clear();
    ShopHistoryList = new ArrayList<>(tempList);


    for(int s = 0; s < ShopHistoryList.size(); s++){
      System.out.format("%3s %15s %15s %17s %15s %15s", s+1, ShopHistoryList.get(s).get(0),
      ShopHistoryList.get(s).get(1), ShopHistoryList.get(s).get(2), ShopHistoryList.get(s).get(3),
      ShopHistoryList.get(s).get(4) + "\n");
    }

    System.out.println();
  
      if (CustomerLists.isEmpty()) {
        CustomerLists = new ArrayList<>(Customer.ReadingFileCustomer());
      }
  
      if (ShopLists.isEmpty()) {
        ShopLists = new ArrayList<>(Shop.ReadShopFile());
      }
    
      for(int h = 1; h < CustomerLists.size(); h++){
        CustomerLists.get(h).set(3, "Normal");
      }
  
      CustomertoCSV(CustomerLists);
  
      for(int w = 1; w < ShopLists.size(); w++){
        ShopLists.get(w).set(2, "Normal");
      }
  
      ShoptoCSV(ShopLists);
  

    BufferedWriter writer;
        {
        try {
          writer = new BufferedWriter(new FileWriter("shopHistory.csv", false));
          writer.append("Date,Time,Customer,Shop,Status");
          
          for(int j = 0; j < ShopHistoryList.size(); j++)
          {
            writer.append("\n" + ShopHistoryList.get(j).get(0) + "," + ShopHistoryList.get(j).get(1) + "," +
            ShopHistoryList.get(j).get(2) + "," + ShopHistoryList.get(j).get(3) + "," +
            ShopHistoryList.get(j).get(4));
          }
          writer.close();
        } catch (IOException e) {
          System.out.println("File not found!");
        }
        }
  }

  public static void FlagCustomer() { //Method to flag the selected customer from "Normal" to "Case"

    File file = new File("shopHistory.csv");
    BufferedReader br = null;
    getShopHistoryList().clear();

    try {
      FileReader fr = new FileReader(file);
      br = new BufferedReader(fr);

      String line;

      while ((line = br.readLine()) != null) { // Stores the value of customers inside an array
        String[] values = line.trim().split(",");
        ShopHistoryList.add(Arrays.asList(values));
      }

    } catch (FileNotFoundException e) {
      System.out.println("File is not found sorry " + file.toString());
    } catch (IOException e) {
      System.out.println("Unable to read file " + file.toString());
    } catch (NullPointerException ex) {
      // File was probably never opened!
    }

    List<List<String>> CustomerLists = new ArrayList<>();

    if(CustomerLists.isEmpty()){
      CustomerLists = new ArrayList<>(Customer.ReadingFileCustomer());
    }
    
    List<List<String>> ShopLists = new ArrayList<>();
    if(ShopLists.isEmpty()){
      ShopLists = new ArrayList<>(Shop.ReadShopFile());
    }
    Scanner input = new Scanner(System.in);
    System.out.print("Choose a number to flag the customer: ");
    int flaggedCustomer = input.nextInt();

    System.out.println();

    for(int i = 0; i < ShopHistoryList.size(); i++){

      String CustomerFlaggedDateTime = ShopHistoryList.get(flaggedCustomer).get(0) + " " + ShopHistoryList.get(flaggedCustomer).get(1);
      String OtherCustomerFlaggedDateTime = ShopHistoryList.get(i).get(0) + " " + ShopHistoryList.get(i).get(1);

      Date date1;
      Date date2;
      long difference_In_Time = 0;
      long difference_In_Minutes = 0;
      long difference_In_Hours = 0;
      try {
        SimpleDateFormat formatDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        date1 = formatDateTime.parse(CustomerFlaggedDateTime); 
        date2 = formatDateTime.parse(OtherCustomerFlaggedDateTime); 

        difference_In_Time = date2.getTime() - date1.getTime();
        difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24; 
        difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;  

        for(int p = 0; p < difference_In_Hours; p++)
        {	
					difference_In_Minutes += 60;
        }

        for(int p = 0; p > difference_In_Hours; p--)
        {
					difference_In_Minutes -= 60;

        }

      
      } catch (ParseException e) {

      }
      
      
      //ShopHistoryList.get(flaggedCustomer).set(4, "Case"); //the flagged customer becomes case
      
      if(difference_In_Minutes >= -60 && difference_In_Minutes <= 60){ //time 
        if(ShopHistoryList.get(flaggedCustomer).get(3).equals(ShopHistoryList.get(i).get(3))){// check for the same shop
          ShopHistoryList.get(i).set(4, "Close"); // customer becomes close
        
        for(int k = 0; k < ShopLists.size(); k++){
          if(ShopHistoryList.get(flaggedCustomer).get(3).equals(ShopLists.get(k).get(0))) { //check shop for flagged shop
            ShopLists.get(k).set(2, "Case"); // shop becomes case
          }
        }
              
        for(int j = 0; j < CustomerLists.size(); j++) {
          if(ShopHistoryList.get(flaggedCustomer).get(2).equals(CustomerLists.get(j).get(0))) { //check name for flagged customer

            CustomerLists.get(j).set(3,"Case"); // flagged customer
            break;
          }
        }
        for(int m = 0; m < CustomerLists.size(); m++) {
          if(ShopHistoryList.get(i).get(2).equals(CustomerLists.get(m).get(0))) { // check name for Other customer

            if(ShopHistoryList.get(flaggedCustomer).get(2).equals(CustomerLists.get(m).get(0))){
              break;
            }
            else{
              CustomerLists.get(m).set(3,"Close"); // Other customer
              break;
            }
          }
        } 
      }         
    }    
    ShopHistoryList.get(flaggedCustomer).set(4, "Case"); //the flagged customer becomes case
       
} 
        //to change the status norm to case in csv file 
        BufferedWriter writer;

        try {
          writer = new BufferedWriter(new FileWriter("shopHistory.csv", false));
          
          for(int j = 0; j < getShopHistoryList().size(); j++)
          {
            writer.append(getShopHistoryList().get(j).get(0) + "," + getShopHistoryList().get(j).get(1) + "," +
            getShopHistoryList().get(j).get(2) + "," + getShopHistoryList().get(j).get(3) + "," +
            getShopHistoryList().get(j).get(4));
            if(j == getShopHistoryList().size()- 1){
              break;
            }
            else{
              writer.append("\n");
            }
          }
          writer.close();

          
        } catch (IOException e) {
          System.out.println("File not found!");
        }
        CustomertoCSV(CustomerLists);
        ShoptoCSV(ShopLists);
        
      }
      

public static void CustomertoCSV(List<List<String>> CustomerLists) { //Writes to the customer.csv file
  BufferedWriter writer;

  try {

    writer = new BufferedWriter(new FileWriter("customerdata.csv", false));

    for(int j = 0; j < CustomerLists.size(); j++)
    {
      writer.append(CustomerLists.get(j).get(0) + "," + CustomerLists.get(j).get(1) + "," +
      CustomerLists.get(j).get(2) + "," + CustomerLists.get(j).get(3));
      if(j == CustomerLists.size()- 1){
        break;
      }
      else{
        writer.append("\n");
      }
    }

    writer.close();

    
  } catch (IOException e) {
    System.out.println("File not found!");
  }
}

public static void ShoptoCSV(List<List<String>> ShopLists) { //Writes to the Shophistory.csv file
  BufferedWriter writer;

  try {

    writer = new BufferedWriter(new FileWriter("shop.csv", false));

    for(int j = 0; j < ShopLists.size(); j++)
    {
      writer.append(ShopLists.get(j).get(0) + "," + ShopLists.get(j).get(1) + "," +
      ShopLists.get(j).get(2) + "," + ShopLists.get(j).get(3));
    
    if(j == ShopLists.size()- 1){
      break;
    }
    else{
      writer.append("\n");
    }
    }
    writer.close();

    
  } catch (IOException e) {
    System.out.println("File not found!");
  }
}

}
