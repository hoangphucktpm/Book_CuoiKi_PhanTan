package server;


import dao.BookDao;
import dao.Impl.BookImpl;
import dao.Impl.ReviewsImpl;
import dao.ReviewsDao;
import entity.Book;
import entity.Reviews;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Server {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(6541)) {
            System.out.println("Server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                System.out.println("Client IP: " + socket.getInetAddress().getHostAddress());
                Thread t = new Thread(new ClientHandler(socket));
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) throws RemoteException {
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            BookDao bookDao = new BookImpl();
            ReviewsDao reviewsDao = new ReviewsImpl();
            int choice = 0;
            while (true)
            {
                try {
                    choice = dis.readInt();
                } catch (SocketException | EOFException e) {
                    System.out.println("Client disconnected");
                    break;
                }
                switch (choice)
                {
                    case 1:
                        System.out.println("Cau 1: Liệt kê danh sách các cuốn sách được viết bởi tác giả nào đó khi biết tên tác giả và có điểm đánh giá từ mấy sao trở lên");
                        String author = dis.readUTF();
                        int rating = dis.readInt();
                        List<Book> books = bookDao.listRatedBooks(author, rating);
                        books.forEach(System.out::println);
                        oos.writeObject(books);
                        oos.flush();
                        break;
                    case 2:
                        System.out.println("Cau 2: Thống kê số cuốn sách được dịch sang ngôn ngữ khác của từng tác giả, kết quả sắp xếp theo tên tác giả");
                        Map<String, Long> items = bookDao.countBooksByAuthor();
                        oos.writeObject(items);
                        oos.flush();
                        break;
                    case 3:
                        System.out.println("Cau 3: Cập nhật đánh giá của một cuốn sách từ một độc giả");
                        String isbn = dis.readUTF();
                        String readerID = dis.readUTF();
                        int rating1 = dis.readInt();
                        String comment = dis.readUTF();
                        boolean result = reviewsDao.updateReviews(isbn, readerID, rating1, comment);
                        oos.writeBoolean(result);
                        oos.flush();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client exception " + e.getMessage());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}