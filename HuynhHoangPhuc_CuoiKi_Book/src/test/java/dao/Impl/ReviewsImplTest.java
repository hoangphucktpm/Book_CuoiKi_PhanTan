package dao.Impl;

import dao.BookDao;
import dao.ReviewsDao;
import entity.Book;
import entity.Reviews;
import org.junit.jupiter.api.*;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewsImplTest {

    private ReviewsDao reviewsDao;

    @BeforeAll
    void setUp() throws RemoteException {
        reviewsDao = new ReviewsImpl();
    }

    // cau c
    // Câu c. Cập nhật đánh giá của một cuốn sách từ một độc giả
    // Test case: rating < 1
    @Test
    void testUpdateReviewInvalidRating() throws Exception {
        String isbn = "888-0321115217";
        String readerID = "1";
        int rating = 0;
        String comment = "GoodGoodGoodGoodGoodGoodGoodGoodGoodGoodGoodGoodGoodGoodGoodGood";
        assertFalse(reviewsDao.updateReviews(isbn, readerID, rating, comment));
    }

    // Test case: rating > 5

    @Test
    void testUpdateReviewInvalidRating2() throws Exception {
        String isbn = "888-0321115217";
        String readerID = "1";
        int rating = 6;
        String comment = "Good";
        assertFalse(reviewsDao.updateReviews(isbn, readerID, rating, comment));
    }


    // Test case: Valid rating and comment
    @Test
    void testAddReviewValid() throws Exception {
        String isbn = "888-0321660783";
        String readerID = "14";
        int rating = 5;
        String comment = "Good 77777    77777777777";
        assertTrue(reviewsDao.updateReviews(isbn, readerID, rating, comment));
    }


    @AfterAll
    void tearDown() throws RemoteException {
        reviewsDao = null;
    }
}