import java.util.Random;

class BankAccount {
    private int balance;

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized deposit method
    public synchronized void deposit(int amount) {
        if(amount > 0) {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " deposited: " + amount + " | New Balance: " + balance);
        }
    }

    // Synchronized withdrawal method
    public synchronized void withdraw(int amount) {
        if(amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew: " + amount + " | New Balance: " + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " failed to withdraw: " + amount + " | Current Balance: " + balance);
        }
    }

    public int getBalance() {
        return balance;
    }
}

class ClientThread implements Runnable {
    private BankAccount account;
    private int transactions;
    private Random random;

    public ClientThread(BankAccount account, int transactions) {
        this.account = account;
        this.transactions = transactions;
        this.random = new Random();
    }

    @Override
    public void run() {
        for(int i=0; i<transactions; i++) {
            boolean deposit = random.nextBoolean();
            int amount = random.nextInt(100) + 1; // Amount between 1 and 100

            if(deposit) {
                account.deposit(amount);
            } else {
                account.withdraw(amount);
            }

            try {
                Thread.sleep(random.nextInt(50)); // Random delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class BankTransactionSimulation {
    public static void main(String[] args) {
        BankAccount sharedAccount = new BankAccount(1000); // Initial balance

        int numberOfClients = 5;
        int transactionsPerClient = 20;

        Thread[] clients = new Thread[numberOfClients];

        // Creating client threads
        for(int i=0; i<numberOfClients; i++) {
            clients[i] = new Thread(new ClientThread(sharedAccount, transactionsPerClient), "Client-" + (i+1));
            clients[i].start();
        }

        // Waiting for all clients to finish
        for(int i=0; i<numberOfClients; i++) {
            try {
                clients[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Final account balance
        System.out.println("Final Account Balance: " + sharedAccount.getBalance());
    }
}
