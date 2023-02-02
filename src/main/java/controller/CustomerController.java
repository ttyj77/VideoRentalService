package controller;

import model.CustomerDTO;
import model.CustomerSearchDTO;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerController {

    private Connection connection;

    public CustomerController(Connection connection) {
        this.connection = connection;
    }

    //회원 조회 수정 삭제(탈퇴)
    public boolean insert(CustomerDTO customerDTO) {
        //1.받은 도시로 도시아이디 뽑기
        addAddress(customerDTO);
        String query;
        int lastNumber = 0;
        query = "select address_id from address order by address_id desc limit 1";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                lastNumber = resultSet.getInt("address_id");
            }
        } catch (SQLException e) {
            System.out.println("1111");
            return false;
        }

        //3.추가한 address아이디 customer추가
        query = "insert into customer (email, password, first_name, last_name,nickname,create_date,last_update,active,store_id,address_id) values (?,?,?,?,?,NOW(),NOW(),1,1,?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, customerDTO.getEmail());
            pstmt.setString(2, customerDTO.getPassword());
            pstmt.setString(3, customerDTO.getFirst_name());
            pstmt.setString(4, customerDTO.getLast_name());
            pstmt.setString(5, customerDTO.getNickname());
            pstmt.setInt(6, lastNumber);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("222");
            return false;
        }
        return true;
    }

    private boolean addAddress(CustomerDTO customerDTO) {
        int country_id = selectCountry(customerDTO.getCountry());
        int city_id = selectCity(customerDTO.getCity(), country_id);
        System.out.println("----" + country_id + "---" + city_id);
        //2.도시아디이 뽑고 address추가
        String query = "insert into `address`(`address`, `district`, `city_id`, `postal_code`, `phone`, `location`) values(?, ?, ?, ?, ?, ST_GeomFromText('POINT(0.0 0.0)'))";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, customerDTO.getDetailsAddress());
            pstmt.setString(2, "seoul");
            pstmt.setInt(3, city_id);
            pstmt.setInt(4, 1111);
            pstmt.setString(5, customerDTO.getPhone());
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("777");
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
                customerDTO.setStore_id(resultSet.getInt("store_id"));

//                userDTO.setPassword(resultSet.getString("password")); // 보안상으로 매우 취약하다
                return customerDTO;
            }

            resultSet.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("333");
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
            System.out.println("444");
        }

    }

    public void updateCustomer(CustomerDTO u, int city_id) {
        String query = "select * from address where city_id = ?"; //address_id
        int address_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, city_id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                address_id = resultSet.getInt("address_id");
            }
        } catch (SQLException e) {
            System.out.println("잘못된 입력 입니다.");
        }
        query = "update customer set address_id = ? where customer_id = ?"; //address_id
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, address_id);
            pstmt.setInt(2, u.getCustomer_id());
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("잘못된 회원정보 입니다.");
        }

    }

    public int selectCountry(String country) {
        String query = "select * from country where country = ?"; //나라
        int county_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, country);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                county_id = resultSet.getInt("country_id");
            }
        } catch (SQLException e) {
            System.out.println("나라를 잘못입력하셨습니다.");
        }
        return county_id;
    }


    public int selectCity(String city, int county_id) {
        String query = "select * from city where country_id = ? and city= ?"; //도시
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, county_id);
            pstmt.setString(2, city);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                city_id = resultSet.getInt("city_id");
            }
        } catch (SQLException e) {
            System.out.println("도시를 잘못입력하셨습니다.");
        }
        return city_id;
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

    //반납하는 쿼리
    public void returnFilm(int rental_id) {
        String query = "update rental set return_date = now() where rental_id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, rental_id);

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

    public ArrayList<CustomerSearchDTO> customerSearchName(String first_name, String last_name) {
        ArrayList<CustomerSearchDTO> temp = new ArrayList<>();
        String query = "select * from customer cu join address ad on ad.address_id = cu.address_id where first_name = ? and last_name = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                CustomerSearchDTO u = new CustomerSearchDTO();
                u.setCustomer_id(resultSet.getInt("customer_id"));
                u.setFirst_name(resultSet.getString("first_name"));
                u.setLast_name(resultSet.getString("last_name"));
                u.setEmail(resultSet.getString("email"));
                u.setAddress(resultSet.getString("address"));

                temp.add(u);
            }
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public ArrayList<CustomerSearchDTO> searchPhoneNumber(String num) {
        ArrayList<CustomerSearchDTO> temp = new ArrayList<>();
        String query = "select * from address ad join customer cu on cu.address_id = ad.address_id where right(phone,4) = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, num);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                CustomerSearchDTO u = new CustomerSearchDTO();
                u.setCustomer_id(resultSet.getInt("customer_id"));
                u.setFirst_name(resultSet.getString("first_name"));
                u.setLast_name(resultSet.getString("last_name"));
                u.setAddress(resultSet.getString("address"));
                u.setEmail(resultSet.getString("email"));
                u.setPhone(resultSet.getString("phone"));
                temp.add(u);
            }
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public CustomerDTO customerSearchEmail(String email) {
        CustomerDTO u = null;
        String query = "select * from customer where email = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                u = new CustomerDTO();
                u.setCustomer_id(resultSet.getInt("customer_id"));
                u.setNickname(resultSet.getString("nickname"));
                u.setAddress_id(resultSet.getInt("address_id"));
                u.setRole(resultSet.getInt("role"));
                return u;
            }
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public void addresUpdate(String address,int city_id, int address_id){
        String query = "update address set `address` = ?, city_id = ? where address_id =?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, address);
            pstmt.setInt(2,city_id);
            pstmt.setInt(3, address_id);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("444");
        }

    }

}
