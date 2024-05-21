package dao.Impl;

import dao.BookDao;
import entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookImpl extends UnicastRemoteObject implements BookDao {

    private static final long serialVersionUID = 1L;
    private EntityManager em;

    public BookImpl() throws RemoteException {
        em = Persistence.createEntityManagerFactory("SQLdb").createEntityManager();
    }

    //a. Liệt kê danh sách các cuốn sách được viết bởi tác giả nào đó khi biết tên tác giả và
    //có điểm đánh giá từ mấy sao trở lên.
    //+ listRatedBooks(author: String, rating: int): List<Book>

    public List<Book> listRatedBooks(String author, int rating) throws RemoteException {
        List<Book> books = em.createQuery("SELECT b FROM Book b INNER JOIN b.reviews r INNER JOIN b.authors a WHERE r.rating > :rating and a = :author")
                .setParameter("author", author)
                .setParameter("rating", rating)
                .getResultList();
        return books;
    }

    //b.  Thống kê số cuốn sách được dịch sang ngôn ngữ khác của từng tác giả, kết quả sắp
    //xếp theo tên tác giả.
    //+ countBooksByAuthor(): Map<String, Long>

    public Map<String, Long> countBooksByAuthor() throws RemoteException {
        String jpql = "SELECT a, COUNT(b) " +
                "FROM Book b " +
                "JOIN b.authors a " +
                "GROUP BY a";

        List<Object[]> results = em.createQuery(jpql, Object[].class).getResultList();

        return results.stream()
                .collect(Collectors.toMap(
                        e -> (String) e[0],
                        e -> (Long) e[1]
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
