package api;

public class Discount {
    private String name;
    private ItemToBuy  ifYouBuy;
    private ItemtoGet thenYouGet;

    public Discount(String name, ItemToBuy ifYouBuy, ItemtoGet thenYouGet) {
        this.name = name;
        this.ifYouBuy = ifYouBuy;
        this.thenYouGet = thenYouGet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemToBuy getIfYouBuy() {
        return ifYouBuy;
    }

    public void setIfYouBuy(ItemToBuy ifYouBuy) {
        this.ifYouBuy = ifYouBuy;
    }

    public ItemtoGet getThenYouGet() {
        return thenYouGet;
    }

    public void setThenYouGet(ItemtoGet thenYouGet) {
        this.thenYouGet = thenYouGet;
    }
}
