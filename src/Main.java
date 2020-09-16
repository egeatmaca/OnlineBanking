import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static Map<Integer, Account> accounts = new HashMap<>();
    public static Queue<Transfer> transferQueue = new LinkedList<>();
    public static Lock lock = new ReentrantLock();
    public static Condition notEmpty = lock.newCondition();
    public static Condition notFull = lock.newCondition();
    public static Integer terminatedB = 0;
    public static boolean lastB = false;
    public static final int maxTransfers = 100;
    public static final int totalTransfers = 1000000;
    public static final int totalAccounts = 10000;
    public static final int totalBranches = 20;
    public static final int totalCentrals = 40;

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < totalAccounts; i++){
            Account a = new Account();
            accounts.put(a.getId(), a);
        }

        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < totalBranches; i++){
            threads.add(new Thread(new Branch()));
        }
        for(int i = 0; i < totalCentrals; i++){
            threads.add(new Thread(new Central()));
        }

        for(Thread t : threads){
            t.start();
        }

        for(Thread t : threads){
            try {
                t.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        int total = 0;
        for(Account a : accounts.values()){
            total += a.getMoney();
        }
        if(total == totalAccounts * 100000){
            System.out.println("It works!");
        }else{
            System.out.println("It doesn't look like working good...");
        }
        System.out.println(transferQueue.size() + " transfers remains in the queue");
        System.out.println("Duration: " + (System.currentTimeMillis() - startTime));
    }
}
