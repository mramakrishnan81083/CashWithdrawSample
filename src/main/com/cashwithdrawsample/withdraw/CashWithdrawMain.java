package main.com.cashwithdrawsample.withdraw;

import java.util.Scanner;
/**
 * @author manoj
 * CashWithdrawMain is the main class for cash withdraw Implementation
 * User will input numeric pin
 * Then enter cash to be dispensed in multiple of ten
 * Based on denomination the cash will be dispensed
 * Multiple thread can be executed
 * Single instance to access the cash withdraw method
 * 
 */
public class CashWithdrawMain {
	//Total Amount in System
	public static int Total_Amount = 50000;
	// Available cash denomination
	public static int[] cashDenominations = { 100, 10, 20 };
	// Denomination Count
	public static int[] cashDenominationCount = { 200, 1000, 1000 };
	
	public static void main (String args[]) {
		
		boolean continueTransaction = true;
		Scanner scanner = new Scanner(System.in);
		int MAXLength = 5;
		CashDispenser cashdispenser= CashDispenser.getInstance();
		
		try {
			
			System.out.println("Welcome to UserBank");
			while (continueTransaction) {
				System.out.println("Enter the Pin:");
				int userPin= scanner.nextInt();
				int pinLength = String.valueOf(userPin).length();
				if (userPin <=0 || pinLength >= MAXLength) {
					System.out.println("Invalid Pin");
					break;
				}
				System.out.println("Please enter the Cash to Withdraw (Multiples of 10)");
				int userAmountToWithdraw = scanner.nextInt();
				
				if(userAmountToWithdraw % 10 != 0) {
					System.out.print("Invalid Amount. Please enter a multiple of 10 amount");
					continue;
				}
				
				cashdispenser.withdraw(userAmountToWithdraw);
				System.out.println("Do you want to perform another Transaction (yes/no)");
				
				String continueResponse= scanner.next();
				
				if(!continueResponse.equalsIgnoreCase("yes")){
					
					continueTransaction = false;
					System.out.println("Thanks for Banking with us. Transaction Completed");
				}
			}
		}catch (Exception e){
			System.out.append("System Error. Please try again later");
		}
		scanner.close();
	}
    
}
