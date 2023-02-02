package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReplyController {


    private Connection connection;

    public ReplyController(Connection connection) {
        this.connection = connection;
    }

    //리뷰 받아서 insert 하는 쿼리
    public void insertRating(int rental_id, int rating) {
        int customer_id = 0;
        int film_id = 0;
        String query = "select * from rental re join inventory iv on iv.inventory_id = re.inventory_id join film f on f.film_id = iv.film_id join customer cu on cu.customer_id = re.customer_id where re.rental_id =?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, rental_id);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                film_id = resultSet.getInt("film_id");
                customer_id = resultSet.getInt("customer_id");

            }
        } catch (SQLException e) {
            System.out.println("잘못된 렌탈 아이디 입니다.");
        }

        query = "insert into reply (film_id,customer_id, rating,rental_id) values (?,?,?,?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, film_id);
            pstmt.setInt(2,customer_id);
            pstmt.setInt(3,rating);
            pstmt.setInt(4,rental_id);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("평점을 다시 입력해주세요");
        }


    }

}
