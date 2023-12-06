// Name: Ali Bauyrzhan
// StudentID: 57517720
// Lab Section: T01


import java.util.HashSet;
import java.util.Scanner;

public class PI_Menu {
    private static String nickname;
    private static String password;
    private static String userID;
    private static PI personal_information = new PI();


    //initialize with getting the nickname and password
    public PI_Menu(String userID){

        String[] temp = personal_information.get_info(userID);
        this.userID = userID;
        this.nickname = temp[0];
        this.password = temp[1];
    }

    public static void pi_main(){
        clearScreen();
        Scanner input = new Scanner(System.in);

        //add the command to the set
		HashSet<String> commands = new HashSet<String>();
		commands.add("2");
		commands.add("3");
        commands.add("5");
		String command;
        System.out.println("************************************************************************************************\n"
		+ "****************" + "     My Personal Information     " + "****************" + "\n"
        + "****************" + "                                                                " + "****************" +"\n"
        + "****************" + "     Input 1: My user ID                                        " + "****************" +"\n"
        + "****************" + "     Input 2: My nickname                                       " + "****************" +"\n"
        + "****************" + "     Input 3: My password                                       " + "****************" +"\n"
        + "****************" + "     Input 5: Return                                            " + "****************" +"\n"
        + "************************************************************************************************"); 
        
        // if the input is a command, go next. Else, repeat
        while (true){
			command = input.next();
			if (commands.contains(command)) break;
            else if(command.equals("1")) System.out.println("User ID: "+ userID);
			else System.out.println("Please, enter again");
		}

        //call functions
        if(command.equals("2")) input_2(); 
        else if(command.equals("3")) input_3();
        else {
            new Menu(userID);
        }
    }

    public static void input_2(){
        clearScreen();
        Scanner input = new Scanner(System.in);
		HashSet<String> commands = new HashSet<String>();
        commands.add("1");
        commands.add("5");
        String command;
        System.out.println("************************************************************************************************\n"
        + "****************" + "     Nickname : " + nickname + "                                            " + "****************\n"
        + "****************" + "     Input 1: Change My Nickname                                " + "****************\n"
        + "****************" + "     Input 5: Return                                            " + "****************\n"
        + "************************************************************************************************\n"
        );
        while (true){
			command = input.next();
			if (commands.contains(command)) break;
			else System.out.println("Please, enter again");
		}

        if (command.equals("1")){
            System.out.println("Please input your new nick name");
            nickname = input.next(); //new nickname

            String[] temp = new String[3];
            temp[0] = userID;
            temp[1] = nickname;
            temp[2] = password;
            
            personal_information.update_info(temp);

            input_2();
        }
        else pi_main();
    }

    public static void input_3(){
        clearScreen();
        Scanner input = new Scanner(System.in);
		HashSet<String> commands = new HashSet<String>();
        commands.add("1");
        commands.add("5");
        String command;
        System.out.println("************************************************************************************************\n"
        + "****************" + "     Password : " + password + "                                            " + "****************\n"
        + "****************" + "     Input 1: Change My Password                                " + "****************\n"
        + "****************" + "     Input 5: Return                                            " + "****************\n"
        + "************************************************************************************************\n"
        );
        while (true){
			command = input.next();
			if (commands.contains(command)) break;
			else System.out.println("Please, enter again");
		}

        if (command.equals("1")){
            System.out.println("Please input your new password");
            password = input.next(); //new nickname

            String[] temp = new String[3];
            temp[0] = userID;
            temp[1] = nickname;
            temp[2] = password;
            
            personal_information.update_info(temp);

            input_3();
        }
        else pi_main();
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
       }
}
