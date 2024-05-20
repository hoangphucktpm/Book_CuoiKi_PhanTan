package dao.Impl;

import dao.BookDao;
import dao.ReviewsDao;
import entity.Book;
import entity.Person;
import entity.Reviews;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ReviewsImpl extends UnicastRemoteObject implements ReviewsDao {

    private static final long serialVersionUID = 1L;
    private EntityManager em;

    public ReviewsImpl() throws RemoteException {
        em = Persistence.createEntityManagerFactory("SQLdb").createEntityManager();
    }


    //c. Thêm một đánh giá mới cho một cuốn sách, biết thông tin cuốn sách và người đọc
    public boolean updateReviews(String isbn, String readerID, int rating, String comment) throws RemoteException {
        // Kiểm tra xem rating và comment có hợp lệ không
        if (rating < 1 || rating > 5 || comment == null || comment.trim().isEmpty()) {
            return false;
        }

        try {
            em.getTransaction().begin();

            // Kiểm tra xem đánh giá từ độc giả cho cuốn sách đã tồn tại chưa
            String checkExistingQuery = "SELECT COUNT(r) FROM Reviews r " +
                    "WHERE r.book.ISBN = :isbn AND r.person.id = :readerID";
            Long existingCount = em.createQuery(checkExistingQuery, Long.class)
                    .setParameter("isbn", isbn)
                    .setParameter("readerID", readerID)
                    .getSingleResult();

            if (existingCount == 0) {
                // Đánh giá chưa tồn tại, thêm mới một đánh giá
                Reviews review = new Reviews();
                review.setBook(em.find(Book.class, isbn));
                review.setPerson(em.find(Person.class, readerID));
                review.setRating(rating);
                review.setComment(comment);
                em.persist(review);
            }

            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

}
