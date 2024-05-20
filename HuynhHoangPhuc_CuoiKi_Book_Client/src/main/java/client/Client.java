package client;


import dao.BookDao;
import dao.Impl.BookImpl;
import dao.Impl.ReviewsImpl;
import dao.ReviewsDao;
import entity.Book;


import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 6541);
            Scanner scanner = new Scanner(System.in);
        ){
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");
            int choice =0;
            while (true)
            {
                System.out.println("1. Liệt kê danh sách các cuốn sách được viết bởi tác giả nào đó khi biết tên tác giả và có điểm đánh giá từ mấy sao trở lên");
                System.out.println("2. Thống kê số cuốn sách được dịch sang ngôn ngữ khác của từng tác giả, kết quả sắp xếp theo tên tác giả");
                System.out.println("3. Cập nhật thêm một đánh giá mới cho một cuốn sách, biết thông tin cuốn sách và người đọc");
                System.out.println("4. Thoát");
                System.out.println("Nhập lựa chọn của bạn: ");
                choice = scanner.nextInt();
                dos.writeInt(choice);
                dos.flush();
                switch (choice)
                {
                    case 1:
                        scanner.nextLine();
                        System.out.println("Nhập tên tác giả: ");
                        String author = scanner.nextLine();
                        System.out.println("Nhập điểm đánh giá: ");
                        int rating = scanner.nextInt();
                        System.out.println("Kết quả liệt kê danh sách các cuốn sách được viết bởi tác giả nào đó khi biết tên tác giả và có điểm đánh giá từ mấy sao trở lên");
                        System.out.println("Danh sách các cuốn sách: ");
                        List<Book> books = (List<Book>) ois.readObject();
                        books.forEach(System.out::println);
                        dos.writeUTF(author);
                        dos.writeInt(rating);
                        dos.flush();
                        break;
                    case 2:

                        scanner.nextLine();
                        System.out.println("Kết quả thống kê số cuốn sách được dịch sang ngôn ngữ khác của từng tác giả, kết quả sắp xếp theo tên tác giả");
                        Map<String, Long> countBooksByAuthor = (Map<String, Long>) ois.readObject();
                        countBooksByAuthor.forEach((k, v) -> System.out.println(k + " : " + v));
                        break;

                    case 3:
                        scanner.nextLine();
                        System.out.println("Nhập ISBN của cuốn sách: ");
                        String isbn = scanner.nextLine();
                        System.out.println("Nhập ID của độc giả: ");
                        String readerID = scanner.nextLine();
                        System.out.println("Nhập điểm đánh giá: ");
                        int rating1 = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Nhập bình luận: ");
                        String comment = scanner.nextLine();
                        break;

                    case 4:
                        System.out.println("Thoát");
                        break;
                    default:
                        System.out.println("Nhập sai. Vui lòng nhập lại");
                        break;
                }


            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
