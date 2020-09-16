import java.util.Queue;
import java.util.Random;

public class Branch implements Runnable{

    public static void produce(Queue<Transfer> transferQueue){
        Main.lock.lock();
        Random random = new Random();
        try {
            while(transferQueue.size() >= Main.maxTransfers){
                Main.notFull.await();
            }
            transferQueue.add(new Transfer(random.nextInt(Main.totalAccounts-1)+1, random.nextInt(Main.totalAccounts-1)+1, random.nextInt(10000)));
            Main.notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Main.lock.unlock();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < Main.totalTransfers; i++) {
            Branch.produce(Main.transferQueue);
        }
        synchronized(Main.terminatedB){
            Main.terminatedB++;
        }
        if(Main.terminatedB == Main.totalBranches){
            Main.lastB = true;
        }
    }
}
