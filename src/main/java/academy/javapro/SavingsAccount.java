package academy.javapro;

/**
 * SavingsAccount class extending the abstract Account class. Features interest
 * rate and minimum balance requirement.
 */
public class SavingsAccount extends Account {
	private final double interestRate;
	private static final double MIN_BALANCE = 100.0; // Minimum balance requirement

	/**
	 * Constructor for creating a new savings account.
	 *
	 * @param accountNumber  The account number
	 * @param customerName   The name of the account holder
	 * @param initialBalance The initial balance
	 * @param interestRate   The annual interest rate (%)
	 */
	public SavingsAccount(String accountNumber, String customerName, double initialBalance, double interestRate) {
		super(accountNumber, customerName, initialBalance); // Call to the parent constructor
		this.interestRate = interestRate;
	}


	/**
	 * Calculates the interest amount based on the current balance.
	 *
	 * @return The calculated interest amount
	 */
	public double calculateInterest() {
		return getBalance() * (interestRate / 100);
	}

	/**
     * Applies the calculated interest to the account balance and logs the transaction.
     */
	public void applyInterest() {
		double interest = calculateInterest();
		setBalance(getBalance() + interest);
		logTransaction("INTEREST", interest);
		System.out.println("Interest applied: $" + String.format("%.2f", interest));
	}

	/**
     * Override the deposit method. Ensures that the deposit amount is positive.
     */
	@Override
	public void deposit(double amount) {
		if (amount <= 0) {
			System.out.println("Deposit amount must be greater than zero.");
			return;
		}

		setBalance(getBalance() + amount);
		logTransaction("DEPOSIT", amount);
		System.out.println("Deposit successful: $" + String.format("%.2f", amount));
	}
	
	/**
	 * Overrides the withdraw method with savings account-specific rules. Ensures
	 * minimum balance is maintained.
	 */
	@Override
	public void withdraw(double amount) {
		if (getBalance() - amount < MIN_BALANCE) {
			System.out.println("Cannot withdraw $" + String.format("%.2f", amount) + ". Minimum balance of $"
					+ MIN_BALANCE + " must be maintained.");
			return;
		}
		
		// Check if account balance is less than zero (overdraft protection)
		if (getBalance() < 0) {
			System.out.println("Account is in overdraft. Current balance: $" + String.format("%.2f", getBalance()));
		}

		setBalance(getBalance() - amount);
		logTransaction("WITHDRAWAL", amount);
		System.out.println("Withdrawal successful: $" + String.format("%.2f", amount) + " from savings account");
	}
	
	/**
     * Displays detailed information about the savings account, including the
     * account type, interest rate, and minimum balance requirement.
     */
	@Override
	public void displayInfo() {
		super.displayInfo(); // Call to the parent method
		System.out.println("Account Type: Savings Account");
		System.out.println("Interest Rate: " + interestRate + "%");
		System.out.println("Minimum Balance Requirement: $" + MIN_BALANCE);
	}
}
