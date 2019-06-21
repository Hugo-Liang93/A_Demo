package cn.hugo.domain;


import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class Customer {
    private long customId;
    private String name;
    private String address;
    private String phoneNumber;

    @Override
    public String toString() {
        return "Customer{" +
                "customId=" + customId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
