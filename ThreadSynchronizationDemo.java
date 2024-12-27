class Counter {
    private int count = 0;

    // Synchronized method to ensure thread safety
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

class CounterThread implements Runnable {
    private Counter counter;
    private int increments;

    public CounterThread(Counter counter, int increments) {
        this.counter = counter;
        this.increments = increments;
    }

    @Override
    public void run() {
        for(int i=0; i<increments; i++) {
            counter.increment();
            // Optional: Uncomment to see the thread actions
            // System.out.println(Thread.currentThread().getName() + " incremented to " + counter.getCount());
        }
    }
}

public class ThreadSynchronizationDemo {
    public static void main(String[] args) {
        Counter sharedCounter = new Counter();
        int incrementsPerThread = 100;

        // Creating three threads
        Thread t1 = new Thread(new CounterThread(sharedCounter, incrementsPerThread), "Thread-1");
        Thread t2 = new Thread(new CounterThread(sharedCounter, incrementsPerThread), "Thread-2");
        Thread t3 = new Thread(new CounterThread(sharedCounter, incrementsPerThread), "Thread-3");

        // Starting threads
        t1.start();
        t2.start();
        t3.start();

        // Waiting for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Displaying final counter value
        System.out.println("Final Counter Value: " + sharedCounter.getCount());
    }
}
