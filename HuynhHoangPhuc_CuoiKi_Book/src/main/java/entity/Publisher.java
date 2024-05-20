package entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Table(name = "publishers")
public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "publisher_id")
    private String id;
    private String name;
    private String address;
    private String email;
    private String phone;

    public Publisher() {
    }

    public Publisher(String id, String name, String address, String email, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }


}
