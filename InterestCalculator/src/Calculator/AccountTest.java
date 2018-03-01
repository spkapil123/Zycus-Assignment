package Calculator;

import java.util.Scanner;

public class AccountTest {

	public static void main(String[] args) {
		
		/*the below code calculates and displays the bank interest for N bank
			accounts. In this example i took student ID as S334261 ,so accounding to instruction 
			the number of aaccounts he has is 6*/
		
		// local variable declarations 
		final int N=4;
		String starline="**********************************";
		String hyphenLine="----------------------------------";
		String spacer="                                          ";
		String welcome="Welcome to StarBank Interest Calculator";
		String exit="Thank you";
		String interestResult="Interest Details";
		Account account=new Account();
		Double highestInterest=0d;
		Double lowestInterest=0d;
		Double totalInterest=0d;
		Double averageInterest=0d;
		int zeroInterestAccounts=0;
		
		// display welcome message
		System.out.println(starline+starline+"\n");
		System.out.println(spacer.substring(0, starline.length()-welcome.length()/2)
				+welcome+spacer.substring(0, starline.length()-welcome.length()/2)+"\n");
		System.out.println(starline+starline+"\n\n");
		
		// loop to input amount, calculate and display interest, calculate
		//summary values
		Scanner input = new Scanner(System.in);
		for (int i = 0; i < N; i++) {
			int accountCount=i+1;
			
			System.out.print("Enter the amount for account number "+accountCount+": ");
			int amount=input.nextInt();
			Double interest=account.interest(amount);
			System.out.println("The interest for account number"+accountCount+"is $"+interest);
			int interestType=account.interestType(interest);
			if(interestType==0)zeroInterestAccounts++;
			totalInterest+=interest;
			if(interest>highestInterest)highestInterest=interest;
			if(interest<lowestInterest)lowestInterest=interest;
		}
		input.close();
		averageInterest=totalInterest/N;
		
		
		// generate and display summary
		System.out.println("\n"+hyphenLine.substring(0, hyphenLine.length()-interestResult.length()/2)
				+interestResult+
				hyphenLine.substring(0, hyphenLine.length()-interestResult.length()/2)+"\n");
		
		System.out.println("Total Interest: $"+totalInterest);
		System.out.println("Lowest Interest: $"+lowestInterest);
		System.out.println("Highest Interest: $"+highestInterest);
		System.out.println("Average Interest: $"+averageInterest);
		System.out.println("Number of accounts with no interest: "+zeroInterestAccounts+"\n");
		
		System.out.println(hyphenLine+hyphenLine+"\n\n");
		
		// display exit message
		System.out.println(starline+starline+"\n");
		System.out.println(spacer.substring(0, starline.length()-exit.length()/2)
				+exit+spacer.substring(0, starline.length()-exit.length()/2)+"\n");
		System.out.println(starline+starline+"\n");
		
	}

}
