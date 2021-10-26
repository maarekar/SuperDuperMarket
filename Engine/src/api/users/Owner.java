package api.users;

public class Owner  extends User {
    private String name;
    private int id;

    public Owner(String name) {
        super(name,"Owner");
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
