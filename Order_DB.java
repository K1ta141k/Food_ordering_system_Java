// Name: Ali Bauyrzhan
// StudentID: 57517720
// Lab Section: T01

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order_DB {
    public static ArrayList<String> restaurantids = new ArrayList<String>();
    public static ArrayList<String> restaurant_name = new ArrayList<String>();
    public static ArrayList<String> restaurant_address = new ArrayList<String>();
    public static ArrayList<String> restaurant_rating = new ArrayList<String>();
    public static ArrayList<String> num_of_ratings = new ArrayList<String>();
    
	// read all restaurants.txt data when initialized
    {

        restaurantids.clear();
        restaurant_name.clear();
        restaurant_address.clear();
        restaurant_rating.clear();
        num_of_ratings.clear();

        try{
            FileInputStream fstream = new FileInputStream("restaurants.txt");
		
		    // create a bufferedreader
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		    String StrLine;
            br.readLine();
		    //Read File Line By Line
		    while ((StrLine = br.readLine()) != null)   { 
			    String[] temp = StrLine.split(",");
                restaurantids.add(temp[0]);
                restaurant_name.add(temp[1]);
                restaurant_address.add(temp[2]);
                restaurant_rating.add(temp[3]);
                num_of_ratings.add(temp[4]);
		    }
		    //Close the input stream
		    fstream.close();
	        }
	        catch (Exception e) {
		        System.err.println("Error:"+e.getMessage());    
	        }   
        }
    

		//find food row by restaurantID, return the list of foods
    public static ArrayList<String> get_food_info(String restaurantID){
        ArrayList<String> foodlist = new ArrayList<String>();

        try{
        FileInputStream fstream = new FileInputStream("food.txt");
		
		    // create a bufferedreader
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		    String StrLine;
            
            br.readLine();
		    //Read File Line By Line
		    while ((StrLine = br.readLine()) != null)   { 
                String[] temp = StrLine.split(",");
                if(temp[0].equals(restaurantID)) foodlist.add(StrLine);
		    }
		    //Close the input stream
		    fstream.close();
	        }
	        catch (Exception e) {
		        System.err.println("Error:"+e.getMessage());    
	        } 

        return foodlist;
    }


	//read all the orders data
    public static ArrayList<String> read_order(){
        ArrayList<String> orderlist = new ArrayList<String>();

        try{
            FileInputStream fstream = new FileInputStream("orders.txt");
		
		    // create a bufferedreader
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		    String StrLine;
			br.readLine();
		    //Read File Line By Line
		    while ((StrLine = br.readLine()) != null)   { 
			    orderlist.add(StrLine);
		    }
		    //Close the input stream
		    fstream.close();
	    }
	    catch (Exception e) {
		    System.err.println("Error:"+e.getMessage());    
	    }
        return orderlist;
    }

	//read all the order by userID
    public static ArrayList<String> read_order_by_userID(String userID){
        ArrayList<String> orders = new ArrayList<String>();

        try{
            FileInputStream fstream = new FileInputStream("orders.txt");
		
		    // create a bufferedreader
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		    String StrLine;
            br.readLine();
		    //Read File Line By Line
		    while ((StrLine = br.readLine()) != null){
                if(userID.equals(StrLine.split(",")[0])) orders.add(StrLine);
		    }
		    //Close the input stream
		    fstream.close();
	        }
	        catch (Exception e) {
		        System.err.println("Error:"+e.getMessage());    
	        }
        return orders;
    }    


	//generates new OrderID by adding 1 to the largest OrderID's before.
    public static void post_order(String userID, String restaurantID, String foodID,
    String foodName, String foodPrice, String userAddress, String telephoneNumber, LocalDateTime orderTime, int requiredTime){
		
		
		ArrayList<String> order_data = read_order();
		ArrayList<String> user_order_data = read_order_by_userID(userID);
		int OrderID = 0;
		for (int i = 0; i<user_order_data.size(); i++){
			OrderID = Math.max(OrderID, Integer.parseInt(user_order_data.get(i).split(",")[1])+1);
		}
        // Update the .txt file
        try {

			File fout = new File("orders.txt");
			FileOutputStream fstream = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fstream));
			
            bw.write("UserID,OrderID,RestaurantID,FoodID,Food Name,Food Price,Delivery user address,user telephone number,order time,required time");
            bw.newLine();
			//write line by line
			for (int j =0; j<order_data.size();j++)
			{
				bw.write(order_data.get(j));
				bw.newLine();
			}
            bw.write(userID+","+OrderID+","+restaurantID+","+foodID+","+foodName+","+foodPrice+","+userAddress+","+telephoneNumber+","
            +orderTime+ ','+requiredTime);
			bw.close();
		}catch (Exception e) {
			System.err.println("Error:"+e.getMessage());
		}
    }

	public static void update_order(String userID, String orderID, String others){


		// Get the data, find by userID and orderID and update
		ArrayList<String> order_data = read_order();
		for (int i = 0; i<order_data.size(); i++){
			String[] temp = order_data.get(i).split(",");
			if (userID.equals(temp[0]) && orderID.equals(temp[1])){
				order_data.set(i, userID+","+orderID+","+others);
			}
		}

		//update the orders.txt file
		try {

			File fout = new File("orders.txt");
			FileOutputStream fstream = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fstream));
			
            bw.write("UserID,OrderID,RestaurantID,FoodID,Food Name,Food Price,Delivery user address,user telephone number,order time,required time");
            bw.newLine();
			//write line by line
			for (int j =0; j<order_data.size();j++)
			{
				bw.write(order_data.get(j));
				bw.newLine();
			}
			bw.close();
		}catch (Exception e) {
			System.err.println("Error:"+e.getMessage());
		}
	}

	public static void delete_order(String userID, String orderID){

		// Get the data, find by userID and orderID and delete
		ArrayList<String> order_data = read_order();
		for (int i = 0; i<order_data.size(); i++){
			String[] temp = order_data.get(i).split(",");
			if (userID.equals(temp[0]) && orderID.equals(temp[1])){
				order_data.remove(i);
			}
		}

		//update the orders.txt file
		try {

			File fout = new File("orders.txt");
			FileOutputStream fstream = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fstream));
			
            bw.write("UserID,OrderID,RestaurantID,FoodID,Food Name,Food Price,Delivery user address,user telephone number,order time,required time");
            bw.newLine();
			//write line by line
			for (int j =0; j<order_data.size();j++)
			{
				bw.write(order_data.get(j));
				bw.newLine();
			}
			bw.close();
		}catch (Exception e) {
			System.err.println("Error:"+e.getMessage());
		}

	}

	public static void rate_restaurant(String restaurantID, int rating){

		//update the restaurant rating
		int index = restaurantids.indexOf(restaurantID);
		double old_rating = Double.parseDouble(restaurant_rating.get(index));
		int old_number = Integer.parseInt(num_of_ratings.get(index));
		int new_number = old_number+1;
		double new_rating = (old_rating*old_number+rating)/new_number;
		System.out.println(new_rating);
		String str_new_rating = String.format("%f", new_rating);
		String[] temp = str_new_rating.split(","); //to put '.' separator instead of ','
		restaurant_rating.set(index, temp[0]+"."+temp[1]);
		num_of_ratings.set(index, Integer.toString(new_number));

		//update the restaurants.txt file
		try {

			File fout = new File("restaurants.txt");
			FileOutputStream fstream = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fstream));
			
            bw.write("RestaurantID,Restaurant Name,Restaurant Address,Restaurant Rating, Number of Ratings");
            bw.newLine();
			//write line by line
			for (int j =0; j<restaurantids.size();j++)
			{
				bw.write(restaurantids.get(j)+","+restaurant_name.get(j)+","+restaurant_address.get(j)+","+restaurant_rating.get(j)+","+num_of_ratings.get(j));
				bw.newLine();
			}
			bw.close();
		}catch (Exception e) {
			System.err.println("Error:"+e.getMessage());
		}

	}
}

