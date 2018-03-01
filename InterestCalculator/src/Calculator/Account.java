package Calculator;

public class Account {
	
	public Account(){
	}
	/*this method calculates the interest amount*/
	public double interest(int amount){
		Double interest=0d;
		if(amount<=1000) return 0;
		else if(amount>=1001 && amount<=5000) interest=amount*2.5/100d;
		else if(amount>=5001 && amount<=10000) interest=amount*5/100d;
		else if(amount>10000) interest=amount*8/100d;
		return interest;
	}
	/*this method determines if interest amount is zero*/
	public int interestType(double interest){
		if(interest==0d)return 0;
		else return 1;
		
	}

}
