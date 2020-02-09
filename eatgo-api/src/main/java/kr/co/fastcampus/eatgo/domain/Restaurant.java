package kr.co.fastcampus.eatgo.domain;

public class Restaurant {
    private final String name;
    private final String address;
    private final long Id;

    public Restaurant(long Id, String name, String address) {
        this.Id = Id;
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return name + " in " + address;
    }

    public long getId() {
        return Id;
    }

    public String getAddress() {
        return address;
    }
}
