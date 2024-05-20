package dao.Impl;

import dao.BookDao;
import entity.Book;
import org.junit.jupiter.api.*;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookImplTest {

    private BookDao bookDao;

    @BeforeAll
    void setUp() throws RemoteException {
        bookDao = new BookImpl();
    }

    @Test
    void testListRatedBooks() throws Exception {
        String author = "Richard Helm";
        int rating = 0;
        List<Book> books = bookDao.listRatedBooks(author, rating);
        assertEquals(2, books.size());

    }

    // Cau b
    @Test
     void testCountBooksByAuthor() throws Exception {
//        bookDao.countBooksByAuthor().forEach((k, v) -> System.out.println(k + " : " + v));
        assertEquals(11, bookDao.countBooksByAuthor().size());
    }


    @AfterAll
    void tearDown() throws RemoteException {
        bookDao = null;
    }
}