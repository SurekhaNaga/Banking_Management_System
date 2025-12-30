import java.sql.*;
import java.util.Scanner;

public class Bankingsystem {
	
	//Database details
	static final String url = "jdbc:mysql://localhost:3306/BankingDB";
	static final String user = "root";
	static final String pass = "Renu@123";
	static Scanner sc = new Scanner(System.in);
	
	//Database connection
	public static Connection getConnection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url,user,pass);
	}
	
	//Creating Bank account
	public static void createAccount() throws Exception{
		
		System.out.println("Enter name: ");
		String name = sc.next();
		
		System.out.println("Enter Initial balance: ");
		double balance = sc.nextDouble();
		
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement("insert into accounts(name,balance) values(?,?)");
		ps.setString(1, name);
		ps.setDouble(2,balance);
		
		ps.executeUpdate();
		System.out.println("Account created successfully!");
		
		con.close();
	}
	
	//Deposit money
	public static void deposit() throws Exception{
		System.out.println("Enter account number: ");
		int acc = sc.nextInt();
		
		System.out.println("Enter ammount to deposit: ");
		double amount = sc.nextDouble();
		
		Connection con = getConnection();
	    PreparedStatement ps = con.prepareStatement("update accounts set balance = balance + ? where acc_num = ?");
	    ps.setDouble(1, amount);
	    ps.setInt(2, acc);
	    
	    int rows = ps.executeUpdate();
	    if(rows > 0) {
	    	System.out.println("Deposit successful");
	    }
	    else {
	    	System.out.println("Account not found");
	    }
	    con.close();
	}
	
	//Withdraw money
	public static void withdraw() throws Exception{
		System.out.print("Enter Account Number: ");
        int acc = sc.nextInt();

        System.out.print("Enter Amount to Withdraw: ");
        double amount = sc.nextDouble();
        
        Connection con = getConnection();
        
        PreparedStatement check = con.prepareStatement("select balance from accounts where acc_num = ?");
        check.setInt(1,acc);
        ResultSet rs = check.executeQuery();
        
        if(rs.next()) {
        	double bal = rs.getDouble(1);
        	if(bal >= amount) {
        		PreparedStatement ps = con.prepareStatement("update accounts set balance = balance - ? where acc_num = ?");
        		ps.setDouble(1, amount);
        		ps.setInt(2, acc);
        		ps.executeUpdate();
        		System.out.println("Withdrawel successful");
        	}
        	else {
        		System.out.println("Insufficient balance");
        	}
        }
        else {
        	System.out.println("Account not found");
        }
        con.close();
	}
	
	//Check account balance
	public static void checkbalance() throws Exception{
		System.out.println("Enter account number: ");
		int acc = sc.nextInt();
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement("select balance from accounts where acc_num = ?");
		ps.setInt(1, acc);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			System.out.println("Balance: "+rs.getDouble(1));
		}
		else {
			System.out.println("Account not found!");
		}
		con.close();
	}
	
	public static void main(String[] args) {
		while(true) {
			try {
				System.out.println("\n--- BANKING SYSTEM ---");
                System.out.println("1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Check Balance");
                System.out.println("5. Exit");

                System.out.print("Choose Option: ");
                int choice = sc.nextInt();
                
                switch(choice) {
                case 1 : createAccount();
                			break;
                case 2 : deposit();
                			break;
                case 3 : withdraw();
                            break;
                case 4 : checkbalance();
                			break;
                case 5 : {
                	System.out.println("Thank you");
                	System.exit(0);
                	break;
                }
                default : System.out.println("invalid choice!");
                }
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}