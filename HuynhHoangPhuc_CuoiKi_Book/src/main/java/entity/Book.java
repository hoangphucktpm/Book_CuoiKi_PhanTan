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
//@ToString
@Table(name = "books")
@Inheritance(strategy = InheritanceType.JOINED)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    protected  String ISBN;
    protected  String name;
    @Column(name = "publish_year")
    protected  int publishYear;
    @Column(name = "num_of_pages")
    protected  int numOfPages;
    protected  double price;


    @ElementCollection
    @CollectionTable(name = "books_authors", joinColumns = @JoinColumn(name = "ISBN"))
    @Column(name = "author")
    protected Set<String> authors;


    @ManyToOne
    @JoinColumn(name = "publisher_id")
    protected Publisher publisher;

    @OneToMany
    @JoinColumn(name = "ISBN")
    protected Set<Reviews> reviews;



    public Book() {
    }

    public Book(String ISBN, String name, int publishYear, int numOfPages, double price, Set<String> authors, Publisher publisher) {
        this.ISBN = ISBN;
        this.name = name;
        this.publishYear = publishYear;
        this.numOfPages = numOfPages;
        this.price = price;
        this.authors = authors;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", name='" + name + '\'' +
                ", publishYear=" + publishYear +
                ", numOfPages=" + numOfPages +
                ", price=" + price +
                ", authors=" + authors +
                ", publisher=" + publisher +
                '}';
    }




}
