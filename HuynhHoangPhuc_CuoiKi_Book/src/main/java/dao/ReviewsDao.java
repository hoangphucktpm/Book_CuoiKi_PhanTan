package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReviewsDao extends Remote {
    boolean updateReviews(String isbn, String readerID, int rating, String comment) throws RemoteException;
}
