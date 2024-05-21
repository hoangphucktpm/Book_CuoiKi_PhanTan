package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;


@Entity
@Getter
@Setter
//@ToString
@Table(name = "book_translations")
public class BookTranslation extends Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private String language;
    @Column(name = "translate_name")
    private String translateName;


    public BookTranslation() {
    }

    public BookTranslation(String ISBN, String name, int publishYear, int numOfPages, double price, Set<String> authors, Publisher publisher, String language, String translateName) {
        super(ISBN, name, publishYear, numOfPages, price, authors, publisher);
        this.language = language;
        this.translateName = translateName;
    }

    @Override
    public String toString() {
        return "BookTranslation{" +
                "ISBN='" + ISBN + '\'' +
                ", name='" + name + '\'' +
                ", publishYear=" + publishYear +
                ", numOfPages=" + numOfPages +
                ", price=" + price +
                ", authors=" + authors +
                ", publisher=" + publisher +
                ", language='" + language + '\'' +
                ", translateName='" + translateName + '\'' +
                '}';
    }
}
