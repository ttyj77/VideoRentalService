package controller;

import model.CustomerDTO;
import model.FilmDTO;
import model.RentalDTO;
import model.RentalRequestList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RentalController {


    private Connection connection;


    public RentalController(Connection connection) {
        this.connection = connection;

    }

    //렌탈요청 리스트 프린트하는 부분
    public ArrayList<RentalRequestList> rental() {
        ArrayList<RentalRequestList> temp = new ArrayList<>();
        String query = "select * from rental_request rr join customer cu on cu.customer_id = rr.customer_id join film f on f.film_id = rr.film_id";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                RentalRequestList r = new RentalRequestList();
                r.setRental_id(resultSet.getInt("rental_id"));
                r.setCustomer_id(resultSet.getInt("customer_id"));
                r.setFilm_id(resultSet.getInt("film_id"));
                r.setFirst_name(resultSet.getString("first_name"));
                r.setLast_name(resultSet.getString("last_name"));
                r.setTitle(resultSet.getString("title"));
                r.setRequest_date(resultSet.getTimestamp("request_date"));
                r.setEmail(resultSet.getString("email"));
                temp.add(r);
            }
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    //고객이 렌탈을 요청하는 부분
    public boolean insert(RentalDTO rentalDTO) {
        String query = "insert into rental_request (customer_id, film_id,last_update) values (?,?,Now())";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, rentalDTO.getCustomer_id());
            pstmt.setInt(2, rentalDTO.getFilm_id());

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("rental Request insert 문 오류.");
            return false;
        }

        return true;
    }

    //직원이 직접 입력받았을 경우
    public boolean staffInsert(RentalDTO rentalDTO, int staff_id) {
        String query = "select * from inventory where film_id = ? and inventory_id not in (select inventory_id from rental where return_date is null) order by inventory_id limit 1";
        int inventory_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, rentalDTO.getFilm_id());

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                inventory_id = resultSet.getInt("inventory_id");
            }
            resultSet.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("inventory_id 문 오류.");
            return false;
        }

        query = "insert into rental (rental_date,inventory_id, customer_id,return_date,staff_id,last_update) values(now(),?,?,null,?,now())";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, inventory_id);
            pstmt.setInt(2, rentalDTO.getCustomer_id());
            pstmt.setInt(3, staff_id);

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("rental Request insert 문 오류.");
            return false;
        }
        return true;
    }

    //관리자가 렌탈 승인하고 insert 하는부분
    public boolean rentalInsert(int rental_id, int staff_id) {
        String query = "select * from rental_request where rental_id = ?";
        int film_id = 0;
        int customer_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, rental_id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                film_id = resultSet.getInt("film_id");
                customer_id = resultSet.getInt("customer_id");

            }
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            return false;
        }

        query = "select * from inventory where film_id = ? and inventory_id not in (select inventory_id from rental where return_date is null) order by inventory_id limit 1";
        int inventory_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, film_id);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                inventory_id = resultSet.getInt("inventory_id");
            }
            resultSet.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("inventory_id 문 오류.");
            return false;
        }

        query = "insert into rental (rental_date,inventory_id, customer_id,return_date,staff_id,last_update) values(now(),?,?,null,?,now())";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, inventory_id);
            pstmt.setInt(2, customer_id);
            pstmt.setInt(3, staff_id);

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("rental Request insert 문 오류.");
            return false;
        }

        query = "delete from rental_request where rental_id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, rental_id);

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("delete 문 오류.");
            return false;
        }


        return true;
    }

    //자신의 렌탈 현황을 보는 부분
    public ArrayList<RentalDTO> rentalList(int id) {
        ArrayList<RentalDTO> temp = new ArrayList<>();
        String query = "select * from rental re join inventory iv on re.inventory_id = iv.inventory_id join film f on f.film_id = iv.film_id where customer_id = ? and re.return_date is null";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                RentalDTO r = new RentalDTO();
                r.setRental_id(resultSet.getInt("rental_id"));
                r.setRental_date(resultSet.getTimestamp("rental_date"));
                r.setInventory_id(resultSet.getInt("inventory_id"));
                r.setReturn_date(resultSet.getTimestamp("return_date"));
                r.setStaff_id(resultSet.getInt("staff_id"));
                r.setLast_update(resultSet.getTimestamp("last_update"));
                r.setCustomer_id(resultSet.getInt("customer_id"));
                r.setFilm_id(resultSet.getInt("film_id"));
                r.setRental_duration(resultSet.getInt("rental_duration"));
                r.setTitle(resultSet.getString("title"));
                temp.add(r);
            }
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    //이전 대여기록
    public ArrayList<RentalDTO> oldRentalList(int id) {
        ArrayList<RentalDTO> temp = new ArrayList<>();
        String query = "select * from rental re join inventory iv on iv.inventory_id = re.inventory_id join film f on f.film_id = iv.film_id where customer_id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                RentalDTO r = new RentalDTO();
                r.setRental_id(resultSet.getInt("rental_id"));
                r.setRental_date(resultSet.getTimestamp("rental_date"));
                r.setInventory_id(resultSet.getInt("inventory_id"));
                r.setReturn_date(resultSet.getTimestamp("return_date"));
                r.setStaff_id(resultSet.getInt("staff_id"));
                r.setLast_update(resultSet.getTimestamp("last_update"));
                r.setCustomer_id(resultSet.getInt("customer_id"));
                r.setFilm_id(resultSet.getInt("film_id"));
                r.setRental_duration(resultSet.getInt("rental_duration"));
                r.setTitle(resultSet.getString("title"));
                temp.add(r);
            }
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;

    }

}
