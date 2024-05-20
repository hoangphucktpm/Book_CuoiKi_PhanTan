package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "reviews")
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    private int rating;
    private String comment;

    @Id
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Id
    @ManyToOne
    @JoinColumn(name = "ISBN")
    private Book book;


    public Reviews() {
    }

    public Reviews(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }



}
