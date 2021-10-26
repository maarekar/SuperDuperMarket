package api.users;

public class Buyer extends User {
    private String name;
    private int id;

    public Buyer(String name) {
        super(name,"Customer");
        this.name = name;
        id = User.serialNumber;
        User.serialNumber ++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
