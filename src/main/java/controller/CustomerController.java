package controller;

import model.CustomerDTO;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController {

    private Connection connection;

    public CustomerController(Connection connection) {
        this.connection = connection;
    }

    //회원 조회 수정 삭제(탈퇴)
    public boolean insert(CustomerDTO customerDTO) {
        String query = "insert into customer (email, password, first_name, last_name,nickname,create_date,last_update,active,store_id,address_id) values ( ?,?,?,?,?,NOW(),NOW(),1,1,1)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, customerDTO.getEmail());
            pstmt.setString(2, customerDTO.getPassword());
            pstmt.setString(3, customerDTO.getFirst_name());
            pstmt.setString(4, customerDTO.getLast_name());
            pstmt.setString(5, customerDTO.getNickname());

            pstmt.executeUpdate();

            pstmt.close();

        } catch (SQLException e) {
            System.out.println("insert문 오류.");
            return false;
        }

        return true;
    }

    //login
    public CustomerDTO auth(String email, String password) {

        String query = "select * from customer where email = ? and password = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setCustomer_id(resultSet.getInt("customer_id"));
                customerDTO.setEmail(resultSet.getString("email"));
                customerDTO.setNickname(resultSet.getString("nickname"));
                customerDTO.setFirst_name(resultSet.getString("first_name"));
                customerDTO.setLast_name(resultSet.getString("last_name"));
                customerDTO.setActive(resultSet.getInt("active"));
                customerDTO.setRole(resultSet.getInt("role"));

//                userDTO.setPassword(resultSet.getString("password")); // 보안상으로 매우 취약하다
                return customerDTO;
            }

            resultSet.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(CustomerDTO customerDTO) {
        String query = "update customer set password = ?, nickname = ? where customer_id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, customerDTO.getPassword());
            pstmt.setString(2, customerDTO.getNickname());
            pstmt.setInt(3, customerDTO.getCustomer_id());

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int customer_id) {
        String query = "delete from customer where customer_id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, customer_id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public CustomerDTO selectOne(int customer_id) {
        CustomerDTO u = null;
        String query = "select * from customer where customer_id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, customer_id);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                u = new CustomerDTO();
                u.setCustomer_id(resultSet.getInt("customer_id"));
                u.setNickname(resultSet.getString("nickname"));
                return u;
            }
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

}
