import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shop which consists of the shop name, shop status, shop manager's name and phone number.
 * Retrieves and stores data for the shops while also displaying the status for each of them.
 * 
 * @author Ali Imran Bin Shahrin
 * @author Deivashree A/P Selva Rajoo
 */
public class Shop{
  private String name;
  private String phoneNumber;
  private static String status;
  private String manager;

  /**
   * Displays the selected shop status from the shop menu.
   * 
   * @param shopChoice the shop chosen by the user to view the status.
   */
  public static void DisplayShopStatus(String shopChoice){

    int shopLine = 0;

      for (int i = 0; i < ReadShopFile().size(); i++) {
        if (ReadShopFile().get(i).get(0).equals(shopChoice)) {
          shopLine = i;
          break;
        }
      }

    status = ReadShopFile().get(shopLine).get(2);
    System.out.println("Status\n" + shopChoice + ": " + status +"\n");
}

  /**
   * Reads the shop.csv and displays the data from the file.
   * 
   * @return an arraylist to display the data from shop.csv.
   * @throws FileNotFoundException the selected file is not found.
   * @throws IOException unable to read the selected file.
   * @throws NullPointerException calling the method on a null reference when reading the file.
   */
  public static List<List<String>> ReadShopFile() {

    File file = new File("shop.csv");
    List<List<String>> ShopList = new ArrayList<>();
    BufferedReader br = null;

    try {
      FileReader fr = new FileReader(file);
      br = new BufferedReader(fr);

      String line;

      while ((line = br.readLine()) != null) { // Stores the value of customers inside an array
        String[] values = line.trim().split(",");
        ShopList.add(Arrays.asList(values));
      }

} catch (FileNotFoundException e) {
  System.out.println("File is not found sorry " + file.toString());
} catch (IOException e) {
  System.out.println("Unable to read file " + file.toString());
}
catch(NullPointerException ex){
}

  return ShopList;
}

 
}


