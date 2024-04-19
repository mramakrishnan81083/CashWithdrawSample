package main.com.cashwithdrawsample.withdraw;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author manoj
 * Cash Dispenser subclass to Main class
 * this class has the logic to check on the singleton instance
 * Thread executor method
 * cash withdraw logic to check on the denomination and available count
 * 
 */
public class CashDispenser extends CashWithdrawMain {

	private static final int THREAD_POOL_SIZE = 5;
	
	private ExecutorService executorService;
	private static CashDispenser cashDispsenserInstance;
	
	private CashDispenser() {
		executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}
	
	public static synchronized CashDispenser getInstance() {
		if (cashDispsenserInstance == null) {
			
			cashDispsenserInstance= new CashDispenser();
		}
		return cashDispsenserInstance;
	}
	
	public void withdraw (int cashAmount) {
		CashTransaction cashTranscation = new CashTransaction(cashAmount);
		executorService.execute(cashTranscation);
	}
	
	private synchronized boolean performWithdrawal(int cashAmount) {
		
		
		if(cashAmount > Total_Amount) {
			System.out.println("Sorry, the ATM is out of Cash");
			return false;
		}
		
		int remainingAmount = cashAmount;
		
		int[] dispenseNotes= new int[cashDenominations.length];
		
		for(int i=0; i < cashDenominations.length; i++) {
			dispenseNotes[i] = Math.min(remainingAmount / cashDenominations[i] , cashDenominationCount[i]);
			remainingAmount -= dispenseNotes[i] * cashDenominations[i];
			cashDenominationCount[i] -= dispenseNotes[i];
		}
		
		if(remainingAmount == 0) {
			Total_Amount -= cashAmount;
			displayDispensedNotes(dispenseNotes);
			return true;
		} else {
			resetAvailableNotes(dispenseNotes);
			System.out.println("Sorry ATM doesnt have enough Cash Denominations to process the request. Please try again later");
			return false;
		}
		
		
	}
	
	private void displayDispensedNotes (int [] dispenseNotes) {
		System.out.println("Dispensed Notes:");
		
		for(int i =0; i < cashDenominations.length; i++) {
			if (dispenseNotes[i] > 0) {
				System.out.println("$" + cashDenominations[i] + " X " + dispenseNotes[i]);
			}
		}
		
	}
	
	private void resetAvailableNotes (int [] dispenseNotes) {
		
		for(int i =0; i < cashDenominations.length; i++) {
			cashDenominationCount[i] += dispenseNotes[i];
		}
	}
	
	private class CashTransaction implements Runnable {
		
		private int cashAmount;
		
		public CashTransaction(int cashAmount) {
			this.cashAmount = cashAmount;
		}
		
		@Override
		public void run() {
			
			if(performWithdrawal(cashAmount)) {
				System.out.println("Transaction Successful");
			}
		}
	}
}
