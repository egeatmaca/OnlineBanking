public class Transfer {
    private int senderID;
    private int receiverID;
    private int money;
    private int tryNumber = 1;

    public Transfer(int senderID, int receiverID, int money){
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.money = money;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getTryNumber() {
        return tryNumber;
    }

    public void setTryNumber(int tryNumber) {
        this.tryNumber = tryNumber;
    }
}
