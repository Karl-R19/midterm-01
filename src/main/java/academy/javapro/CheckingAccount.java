package academy.javapro;

/**
 * CheckingAccount class extending the abstract Account class. Features
 * overdraft protection and transaction fees.
 */
public class CheckingAccount extends Account {
	private double overdraftLimit;
	private static final double TRANSACTION_FEE = 1.5; // Fee per withdrawal

	/**
	 * Constructor for creating a new checking account.
	 *
	 * @param accountNumber  The account number
	 * @param customerName   The name of the account holder
	 * @param initialBalance The initial balance
	 * @param overdraftLimit The maximum allowed overdraft
	 */
	public CheckingAccount(String accountNumber, String customerName, double initialBalance, double overdraftLimit) {
		super(accountNumber, customerName, initialBalance); // Call to the parent constructor
		this.overdraftLimit = overdraftLimit;
	}

	/**
	 * Getter for overdraft limit.
	 *
	 * @return The overdraft limit
	 */
	public double getOverdraftLimit() {
		return overdraftLimit;
	}

	public void setOverdraftLimit(double newLimit) {
		if (newLimit < 0) {
			System.out.println("Overdraft limit cannot be negative.");
			return;
		}
		this.overdraftLimit = newLimit;
		System.out.println("Overdraft limit updated to: $" + String.format("%.2f", newLimit));
	}

	 /**
     * Override the deposit method to handle deposits into the checking account.
     * Ensures that the deposit amount is greater than zero.
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
	 * Overrides the withdraw method with checking account-specific rules.
	 * Implements overdraft protection and applies transaction fees.
	 */
	@Override
	public void withdraw(double amount) {
		if (amount <= 0) {
			System.out.println("Withdrawal amount must be greater than zero.");
			return;
		}

		// Total deduction includes the withdrawal amount and transaction fee
		double totalDeduction = amount + TRANSACTION_FEE;
		
		// Available funds include the balance and the overdraft limit
		double availableFunds = getBalance() + overdraftLimit;

		// Check if the total deduction exceeds available funds (balance + overdraft limit)
		if (totalDeduction > availableFunds) {
			System.out.println("Insufficient funds. Cannot exceed overdraft limit.");
			return;
		}

		 // Deduct the withdrawal amount and transaction fee from the account 
		setBalance(getBalance() - totalDeduction);
		logTransaction("WITHDRAWAL", amount);
		logTransaction("FEE", TRANSACTION_FEE);
		
		// Print messages for the withdrawal and transaction fee
		System.out.println("Withdrew $" + String.format("%.2f", amount) + " from checking account");
		System.out.println("Transaction fee: $" + String.format("%.2f", TRANSACTION_FEE));

		// Check if the account balance is negative (overdraft)
		if (getBalance() < 0) {
			System.out.println("Account is in overdraft. Current balance: $" + String.format("%.2f", getBalance()));
		}
	}

	/**
     * Displays detailed information about the checking account, including the
     * account type, overdraft limit, and transaction fee.
     */
	@Override
	public void displayInfo() {
		super.displayInfo(); // Call to the parent method
		System.out.println("Account Type: Checking Account");
		System.out.println("Overdraft Limit: $" + overdraftLimit);
		System.out.println("Transaction Fee: $" + TRANSACTION_FEE);
	}
}
