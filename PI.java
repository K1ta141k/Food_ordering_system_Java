import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class PI {

    private static ArrayList<String> userids = new ArrayList<String>();
    private static ArrayList<String> usernames = new ArrayList<String>();
    private static ArrayList<String> passwords = new ArrayList<String>();
    

    //read the data from users.txt and assign it to our arrays
    public static void read_data(){

    try {
		FileInputStream fstream = new FileInputStream("users.txt");
		
		// create a bufferedreader
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String StrLine;
        br.readLine();
		//Read File Line By Line
		while ((StrLine = br.readLine()) != null)   { 
			String[] temp = StrLine.split(",");
            userids.add(temp[0]);
            passwords.add(temp[2]);
            usernames.add(temp[1]);
		}
		//Close the input stream
		fstream.close();
	}
	catch (Exception e) {
		System.err.println("Error:"+e.getMessage());
	}

}

    //find password by userid and check with the password
    public static boolean login(String id, String pass){
        for (int i=0; i<userids.size(); i++){
            if(userids.get(i).equals(id)){
                if(passwords.get(i).equals(pass)) return true;
                else return false;
            }
        }
        return false;
    }


    //find by id and return username and password
    public static String[] get_info(String userID){
        String[] temp = new String[2];
        int i = userids.indexOf(userID);
        temp[0] = usernames.get(i);
        temp[1] = passwords.get(i);
        return temp;
    }


    //find the row by userid, change the array, write the array in the users.txt
    public static void update_info(String[] user_info){
        int i = userids.indexOf(user_info[0]);
        
        // Update the temp DB
        usernames.set(i, user_info[1]);
        passwords.set(i, user_info[2]);

        // Update the .txt file
        try {

			File fout = new File("users.txt");
			FileOutputStream fstream = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fstream));
			

            bw.write("userID,username,password");
            bw.newLine();
			//write line by line
			for (int j =0; j<userids.size();j++)
			{
				bw.write(userids.get(j)+","+usernames.get(j)+","+passwords.get(j));
				bw.newLine();
			}
			bw.close();
		}catch (Exception e) {
			System.err.println("Error:"+e.getMessage());
		}
    }
}
