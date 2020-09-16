import java.util.Queue;
import java.util.concurrent.TimeUnit;


public class Central implements Runnable{

    public static void consume(Queue<Transfer> transferQueue){
        Main.lock.lock();
        try {
            while (transferQueue.size() == 0) {
                Main.notEmpty.await(5,TimeUnit.SECONDS);
                if(Main.lastB == true){
                    return;
                }
            }
            Transfer t = transferQueue.remove();
            if (Main.accounts.get(t.getSenderID()).getMoney() - t.getMoney() >= 0){
                Main.accounts.get(t.getSenderID()).setMoney(Main.accounts.get(t.getSenderID()).getMoney() - t.getMoney());
                Main.accounts.get(t.getReceiverID()).setMoney(Main.accounts.get(t.getReceiverID()).getMoney() + t.getMoney());
            }else{
                if(t.getTryNumber() < 3){
                    t.setTryNumber(t.getTryNumber() + 1);
                    transferQueue.add(t);
                }
            }
            if(transferQueue.size() < Main.maxTransfers){
                Main.notFull.signal();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            Main.lock.unlock();
        }
    }

    @Override
    public void run() {
        while(Main.terminatedB < Main.totalBranches){
                Central.consume(Main.transferQueue);
        }
        while(Main.transferQueue.size() > 0){
            Central.consume(Main.transferQueue);
        }
        if(Main.transferQueue.size() == 0) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
