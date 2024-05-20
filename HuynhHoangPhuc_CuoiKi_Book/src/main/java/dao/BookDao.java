package dao;

import entity.Book;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface BookDao extends Remote {
    List<Book> listRatedBooks(String author, int rating) throws Exception;
    Map<String, Long> countBooksByAuthor() throws RemoteException;
}
