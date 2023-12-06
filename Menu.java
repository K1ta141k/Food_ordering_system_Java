// Name: Ali Bauyrzhan
// StudentID: 57517720
// Lab Section: T01

import java.util.HashSet;
import java.util.Scanner;

public class Menu {

    public Menu(String userID){
    
		clearScreen();
		Scanner input = new Scanner(System.in);
		HashSet<String> commands = new HashSet<String>();
		commands.add("1");
		commands.add("2");
		commands.add("5");
		String command;
		System.out.println("************************************************************************************************\n"
		+ "****************" + "     Main Menu     " + "****************" + "\n"
        + "****************" + "                                                                " + "****************" +"\n"
        + "****************" + "     Input 1: My personal information                           " + "****************" +"\n"
        + "****************" + "     Input 2: Order                                             " + "****************" +"\n"
        + "****************" + "     Input 5: Exit the program                                  " + "****************" +"\n"
        + "************************************************************************************************");
	
		while (true){
			command = input.next();
			if (commands.contains(command)) break;
			else System.out.println("Please, enter again");
		}

		if (command.equals("1")){
			PI_Menu pi_menu = new PI_Menu(userID);
      pi_menu.pi_main();
		}
		else if (command.equals("2")){
			Order_Menu order_menu = new Order_Menu(userID);
	        clearScreen();
      		order_menu.main_menu();
		}
		else System.exit(0);
	}

  public static void clearScreen() {  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
   }
}
