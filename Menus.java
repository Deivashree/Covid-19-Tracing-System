import java.util.Scanner;

public class Menus extends Customer{

  public void GeneralMenu() {

    int MenuOption = 0;
    System.out.println("Welcome to Covid-19 Contact Tracing System\n");

  do{
    System.out.println("Role Menu\n");

    System.out.println("1.Customer");
    System.out.println("2.Shop");
    System.out.println("3.Admin");
    System.out.println("4.Exit Program");
    System.out.print("Choose an option: ");

    Scanner input = new Scanner(System.in);
    MenuOption = input.nextInt();

    System.out.println();

    switch (MenuOption) {
      case 1:
        CustomerMenu();
        break;
      case 2:
        ShopMenu();
        break;
      case 3:
        AdminMenu();
        break;
      case 4:
        System.exit(0);
        break;
      default:
        System.out.println("Wrong input! Please enter again.\n");
      }
    }while(MenuOption != 4);
  }

  public void CustomerMenu() {
    Scanner input = new Scanner(System.in);
    boolean signInValue = false;
    int CustomerMenuOption;

    do{
      System.out.println("Customer Menu\n");
      System.out.println("1.Register");
      System.out.println("2.Sign In");
      System.out.println("3.Check in a shop");
      System.out.println("4.View history of visited shops");
      System.out.println("5.View your status");
      System.out.println("6.Exit to Role Menu");
      System.out.print("Choose an option: ");

      CustomerMenuOption = input.nextInt();

      System.out.println();

        switch(CustomerMenuOption) {
            case 1:
              WritingFileCustomer();
              break;
            case 2:
              signInValue = CustomerSignIn(); 
              break;     
            case 3:
              if(signInValue == true){
                CustomerCheckIn();
              }
              else{
                CustomerMenu();
              } 
              break;        
            case 4:
              if(signInValue == true){
                  System.out.format("No " + "%5s %10s %9s", "Date", "Time", "Shop\n");
                  ViewCustomerShopHistory();
                }
              else {
                CustomerMenu();
              }
              break;
            case 5:
              if(signInValue == true){
                ViewCustomerStatus();
              }
              else {
                CustomerMenu();
              }
              break;
            case 6:
              GeneralMenu();
              break;
            default:
              System.out.println("Wrong input! Please enter again.\n");
            break;
          } 
        }while(CustomerMenuOption != 6);
    }

  public void ShopMenu() {
      
    Scanner input = new Scanner(System.in);
    int ShopMenuOption;
    String ShopChoice;
        
    do{
      System.out.println("Shop Menu\n");
      System.out.println("1.Louis Vuitton");
      System.out.println("2.Gucci");
      System.out.println("3.Zara");
      System.out.println("4.Uniqlo");
      System.out.println("5.Exit to Role Menu");
      System.out.print("Choose an option: ");
  
      ShopMenuOption = input.nextInt();
  
      System.out.println();
  
      switch(ShopMenuOption){
        case 1:
          ShopChoice = "LouisVuitton";
          Shop.DisplayShopStatus(ShopChoice);
          break;
        case 2:
          ShopChoice = "Gucci";
          Shop.DisplayShopStatus(ShopChoice);
          break;
        case 3:
          ShopChoice = "Zara";
          Shop.DisplayShopStatus(ShopChoice);
          break;
        case 4:
          ShopChoice = "Uniqlo";
          Shop.DisplayShopStatus(ShopChoice);
          break;
        case 5:
          GeneralMenu();
        default:
          System.out.println("Wrong input! Please enter again.\n");
          break;
        }
      }while(ShopMenuOption != 5);
    }   

    public void AdminMenu(){
        Scanner input = new Scanner(System.in);
        int AdminMenuOption;

        do{
        System.out.println("Admin Menu\n");
        System.out.println("1.View details of all customers");
        System.out.println("2.View details of all shops");
        System.out.println("3.View the master visit history");
        System.out.println("4.Flag a customer");
        System.out.println("5.Generate 30 random visits");
        System.out.println("6.Exit to Role Menu");
        System.out.print("Choose an option: ");

        AdminMenuOption = input.nextInt();

        System.out.println();

        switch(AdminMenuOption){
          case 1:
            Admin.ReadCustomerHistory();
            break;
          case 2:
            Admin.ReadShopHistory();
            break;
          case 3: 
            Admin.MasterVisitHistory();
            break;
          case 4: 
            Admin.FlagCustomer();
            break;
          case 5: 
            Admin.RandomMasterVisitHistory();
            break;
          case 6: 
            GeneralMenu();
            break;
          default:
            System.out.println("Wrong input! Please enter again.\n");
            break;           
        }

      }while(AdminMenuOption != 6);
    }
  }
