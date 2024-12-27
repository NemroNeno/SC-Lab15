import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class WriterThread implements Runnable {
    private List<Integer> sharedList;
    private int start;
    private int end;

    public WriterThread(List<Integer> sharedList, int start, int end) {
        this.sharedList = sharedList;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for(int i=start; i<=end; i++) {
            sharedList.add(i);
            // Optional: Uncomment to see write actions
            // System.out.println(Thread.currentThread().getName() + " added: " + i);
            try {
                Thread.sleep(50); // Simulate some delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ReaderThread implements Runnable {
    private List<Integer> sharedList;

    public ReaderThread(List<Integer> sharedList) {
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        for(int i=0; i<10; i++) {
            System.out.println(Thread.currentThread().getName() + " reads: " + sharedList);
            try {
                Thread.sleep(100); // Simulate some delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ConcurrentDataStructuresDemo {
    public static void main(String[] args) {
        // Using CopyOnWriteArrayList for thread-safe operations
        List<Integer> sharedList = new CopyOnWriteArrayList<>();

        // Creating writer threads
        Thread writer1 = new Thread(new WriterThread(sharedList, 1, 50), "Writer-1");
        Thread writer2 = new Thread(new WriterThread(sharedList, 51, 100), "Writer-2");

        // Creating reader threads
        Thread reader1 = new Thread(new ReaderThread(sharedList), "Reader-1");
        Thread reader2 = new Thread(new ReaderThread(sharedList), "Reader-2");

        // Starting writer and reader threads
        writer1.start();
        writer2.start();
        reader1.start();
        reader2.start();

        // Waiting for all threads to finish
        try {
            writer1.join();
            writer2.join();
            reader1.join();
            reader2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state of the shared list
        System.out.println("Final Shared List Size: " + sharedList.size());
    }
}
