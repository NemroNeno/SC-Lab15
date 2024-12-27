// Method 1: Extending Thread class
class NumberPrinter extends Thread {
    @Override
    public void run() {
        for(int i=1; i<=10; i++) {
            System.out.println("Number: " + i);
            try {
                Thread.sleep(100); // Sleep for better readability
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Method 2: Implementing Runnable interface
class SquarePrinter implements Runnable {
    @Override
    public void run() {
        for(int i=1; i<=10; i++) {
            System.out.println("Square: " + (i * i));
            try {
                Thread.sleep(100); // Sleep for better readability
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MultithreadingDemo {
    public static void main(String[] args) {
        // Creating thread using Thread subclass
        NumberPrinter numberThread = new NumberPrinter();
        
        // Creating thread using Runnable implementation
        Thread squareThread = new Thread(new SquarePrinter());
        
        // Starting both threads
        numberThread.start();
        squareThread.start();
        
        // Joining threads to ensure main thread waits for them to finish
        try {
            numberThread.join();
            squareThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Both threads have finished execution.");
    }
}
