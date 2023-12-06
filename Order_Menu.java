// Name: Ali Bauyrzhan
// StudentID: 57517720
// Lab Section: T01

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Order_Menu {


    public static Order_DB order_db = new Order_DB();
    private static String userID;
    public Order_Menu(String userID){
        this.userID = userID;
    }

    public static void main_menu(){


		Scanner input = new Scanner(System.in);
		HashSet<String> commands = new HashSet<String>();
		commands.add("1");
		commands.add("2");
		commands.add("5");
		String command;
        System.out.println("************************************************************************************************\n"
		+ "****************" + "     Order     " + "****************" + "\n"
        + "****************" + "                                                                " + "****************" +"\n"
        + "****************" + "     Input 1: Add a new order                                   " + "****************" +"\n"
        + "****************" + "     Input 2: My orders                                         " + "****************" +"\n"
        + "****************" + "     Input 5: Return                                            " + "****************" +"\n"
        + "************************************************************************************************");

        while (true){
			command = input.nextLine();
			if (commands.contains(command)) break;
			else System.out.println("Please, enter again");
		}

        if (command.equals("1")) input_1();
        else if(command.equals("2")) input_2();
        else new Menu(userID);
    }

    public static void input_1(){
        clearScreen();
		Scanner input = new Scanner(System.in);
        String command;
        
        System.out.println("Please, enter the restaurant ID you want to order from.\n"
        +                  "------------------------------------------------------------------------------------------------\n");
        for(int i=0; i<order_db.restaurantids.size(); i++){
            System.out.println("RestaurantID: " + order_db.restaurantids.get(i) + " | Restaurant name: " + order_db.restaurant_name.get(i)
            + " | Restaurant address: " + order_db.restaurant_address.get(i) + " | Rating: " + String.format("%.2f", Float.parseFloat(order_db.restaurant_rating.get(i))) + 
            " | Number of ratings: " + order_db.num_of_ratings.get(i));
        }
        
        while (true){
			command = input.nextLine();
			if (order_db.restaurantids.contains(command)) break;
			else System.out.println("Please, enter the restaurant ID again.");
		}

        String restaurantID = command;
        int index = order_db.restaurantids.indexOf(restaurantID);
        String restaurant_district = order_db.restaurant_address.get(index).split(" ")[1];
        String restaurantCity = order_db.restaurant_address.get(index).split(" ")[0];

        clearScreen();
        ArrayList<String> food_rows = order_db.get_food_info(restaurantID);
        ArrayList<String> foodids = new ArrayList<String>();
        ArrayList<String> foodnames = new ArrayList<String>();
        ArrayList<String> foodprices = new ArrayList<String>();
        for (int i=0; i<food_rows.size(); i++){
            String[] temp = food_rows.get(i).split(",");
            foodids.add(temp[1]);
            foodnames.add(temp[2]);
            foodprices.add(temp[3]);
            System.out.println("Food ID: " +temp[1] + " | "+ temp[2]+ " | "+temp[3]);
        }
        
        System.out.println("Please, enter the food ID you want to order from");

        while(true){
            command = input.nextLine();
            if (foodids.contains(command)) break;
            else System.out.println("The food does not exist");
        }

        String foodID = command;
        index = foodids.indexOf(foodID);
        String foodname = foodnames.get(index);
        String foodprice = foodprices.get(index);
        String delivery_address = "";
        String telephone_number = "";
        boolean change_restaurant = false;
        
        System.out.println("Please, enter the delivery address");
        command = input.nextLine();

        while(true){
            String[] temp = command.split(" ");
            if(temp.length>=2) {
                if(restaurantCity.equalsIgnoreCase(temp[0])){
                delivery_address = command;
                break;
                }
            }
            else{
                System.out.println("Your address is not within the delivery range of this restaurant.\n"
                + "Re-enter to input delivery address or Input 1 to change a restaurant.");
                command = input.nextLine();
                if(command.equals("1")){
                    change_restaurant = true;
                    break;
                }
            }
        }

        if (change_restaurant) input_1();
        else{

            System.out.println("Please, enter your telephone number");
            boolean incorrect_number = true;
            while(incorrect_number){
                command = input.nextLine();
                for (int i=0; i<command.length(); i++){
                    if (Character.isDigit(command.charAt(i))) incorrect_number = false;
                    else {
                        incorrect_number = true;
                        break;
                    }
                }
                telephone_number = command; 
                if(incorrect_number) System.out.println("The telephone number is entered incorrectly, please re-enter.");
            }
            int required_time = 2;
            if (delivery_address.split(" ")[1].equalsIgnoreCase(restaurant_district)) required_time = 1;
            order_db.post_order(userID, restaurantID, foodID, foodname, foodprice, delivery_address,telephone_number, LocalDateTime.now(), required_time);
            System.out.println("Order is complete");
            main_menu();
        }

    }

    public static void input_2(){
        clearScreen();
		Scanner input = new Scanner(System.in);
        String command;
        ArrayList<String> orders = order_db.read_order_by_userID(userID);
        int n = orders.size();

        String[] orderids = new String[n];
        String[] rest_ids = new String[n];
        String[] rest_names = new String[n];
        String[] rest_address = new String[n];
        String[] food_ids = new String[n];
        String[] food_names = new String[n];
        String[] food_prices = new String[n];
        String[] telephone = new String[n];
        String[] user_address = new String[n];
        String[] order_time = new String[n];
        String[] required_time = new String[n];
        String[] state = new String[n];

        for(int i=0; i<n; i++){
            String[] temp = orders.get(i).split(",");
            orderids[i] = temp[1];
            int index = order_db.restaurantids.indexOf(temp[2]);
            rest_ids[i] = temp[2];
            rest_names[i] = order_db.restaurant_name.get(index);
            rest_address[i] = order_db.restaurant_address.get(index);
            food_ids[i] = temp[3];
            food_names[i] = temp[4];
            food_prices[i] = temp[5];
            user_address[i] = temp[6];
            telephone[i] = temp[7];
            order_time[i] = temp[8];
            required_time[i] = temp[9];
            int delivery_time = 60* Integer.parseInt(temp[9]);
            Duration duration = Duration.between(LocalDateTime.parse(order_time[i]), LocalDateTime.now());
            if(duration.getSeconds()>delivery_time) state[i] = "Delivered";
            else state[i] = "Not Delivered";
        }

        System.out.println("Please, enter the order ID you want.\n"
        +                  "------------------------------------------------------------------------------------------------\n");

        for(int i=0; i<n; i++){
            String[] temp = order_time[i].split("T");
            System.out.println("Order ID: "+ orderids[i]+ " | Restaurant Name: " + rest_names[i] + " | Food Name: " + food_names[i]
            + " | Order Time: " + temp[0] + " " + temp[1] + " | State:" + state[i]);
        }

        boolean order_found = false;
        int index = 0;
        while(!order_found){
            command = input.nextLine();
            for(int i=0; i<n; i++){
                if(orderids[i].equals(command)){
                    index = i;
                    order_found = true;
                    break;
                }
            }
            if(!order_found) System.out.println("The order ID does not exist. Please, re-enter.");
        }

        clearScreen();
        String[] temp = order_time[index].split("T");
        System.out.println("User ID: "+userID+"\n" + "Order ID: " + orderids[index] + "\nRestaurant ID: " + rest_ids[index]
        + "\nRestaurant Adress: "+rest_address[index] + "\nFood ID: "+food_ids[index]+"\nFood Name: "+food_names[index]+"\nFood Price: $"
        +food_prices[index]+"\nDelivery user address: " + user_address[index] + "\nUser telephone number: " + telephone[index]
        + "\nOrder Time: "+ temp[0] + " "+ temp[1]+ "\nState:" + state[index]);


        System.out.println("************************************************************************************************\n" +
                "****************" + "     Please select the operation that needs to be performed     " + "****************\n" +
                "****************" + "     Input 1: Modify my address                                 " + "****************\n" +
                "****************" + "     Input 2: Modify my telephone number                        " + "****************\n" +
                "****************" + "     Input 3: Delete the order                                  " + "****************\n" +
                "****************" + "     Input 4: Rate the order                                    " + "****************\n" +
                "****************" + "     Input 5: Return                                            " + "****************\n" +
                "************************************************************************************************\n");

        boolean update_order = false;
        boolean delete_order = false;
        while(true){
            command = input.nextLine();
            if(command.equals("1")){
                if(state[index].equals("Delivered")) System.out.println("The time to modify the order has expired!");
                else{ 
                    update_order = true;
                    System.out.println("Please, enter your address.");
                    while(true){
                    command = input.nextLine();
                    if(command.split(" ")[0].equals(user_address[index].split(" ")[0])){
                    user_address[index] = command;
                    break;
                    }
                    else System.out.println("Your address is not within the delivery range of this restaurant, please re-enter your address");
                    }
                    break;
                }
            }
            else if(command.equals("2")){
                if(state[index].equals("Delivered")) System.out.println("The time to modify the order has expired!");
                else{
                    update_order = true;
                    System.out.println("Please, enter your telephone number");
                    boolean incorrect_number = true;
                    while(incorrect_number){
                        command = input.nextLine();
                        for (int i=0; i<command.length(); i++){
                            if (Character.isDigit(command.charAt(i))) incorrect_number = false;
                            else {
                                incorrect_number = true;
                                break;
                            }
                        }
                        telephone[index] = command; 
                        if(incorrect_number) System.out.println("The telephone number is entered incorrectly, please re-enter.");
                    }
                    break;
                }
            }
            else if(command.equals("3")){
                order_db.delete_order(userID, orderids[index]);
                break;
            }
            else if(command.equals("4")){
                System.out.println("Please, enter your rating for the order from 1 to 5");
                boolean correct_input = false;
                while(!correct_input){
                    command = input.next();
                    if(command.length()==1 && Character.isDigit(command.charAt(0))){
                        int new_rating = Integer.parseInt(command);
                        if (new_rating<=5 && new_rating>=1){
                            order_db.rate_restaurant(rest_ids[index], new_rating);
                            correct_input=true;
                        }
                        else{
                            System.out.println("The rating is entered incorrectly, please re-enter.");
                        }
                    }
                    else System.out.println("The rating is entered incorrectly, please re-enter.");
                }
                break;
            }
            else if(command.equals("5")){
                break;
            }
            else System.out.println("Please, enter again.");
        }


        if(update_order){
            order_db.update_order(userID, orderids[index], rest_ids[index]+","+food_ids[index]+","+food_names[index]+","+food_prices[index]+","
            +user_address[index]+","+telephone[index]+","+order_time[index]+","+required_time[index]);
        }

        clearScreen();
        main_menu();
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
       }    
}
