public class Account {
    private int id = nextID++;
    private int money = 100000;
    private static int nextID = 1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public boolean setMoney(int money) {
        if(money >= 0) {
            this.money = money;
            return true;
        }else{
            return false;
        }
    }
}
