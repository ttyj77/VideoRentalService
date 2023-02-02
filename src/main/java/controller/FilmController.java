package controller;

import model.ActorDTO;
import model.CustomerDTO;
import model.FilmDTO;
import model.RentalDTO;
import viewer.FilmViewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilmController {

    private Connection connection;


    public FilmController(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<FilmDTO> filmAllList(int number) {
        String query = "select * from film limit ?, 30";
        ArrayList<FilmDTO> temp = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, number);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                FilmDTO f = new FilmDTO();
                f.setFilm_id(resultSet.getInt("film_id"));
                f.setTitle(resultSet.getString("title"));
                f.setDescription(resultSet.getString("description"));
                f.setLength(resultSet.getInt("length"));
                f.setRelease_year(resultSet.getString("release_year"));
                f.setRental_duration(resultSet.getInt("rental_duration"));
                f.setLanguage_id(resultSet.getInt("language_id"));
                temp.add(f);
            }
            resultSet.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("넘어갈 수 있는 페이지가 없습니다");
        }
        return temp;

    }

    public ArrayList<FilmDTO> ratingList(String rating, int pageNum) {
        String query = "select * from film where rating = ? limit ?, 30";
        ArrayList<FilmDTO> temp = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, rating);
            pstmt.setInt(2, pageNum);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                FilmDTO f = new FilmDTO();
                f.setFilm_id(resultSet.getInt("film_id"));
                f.setTitle(resultSet.getString("title"));
                f.setDescription(resultSet.getString("description"));
                f.setLength(resultSet.getInt("length"));
                f.setRelease_year(resultSet.getString("release_year"));
                f.setRental_duration(resultSet.getInt("rental_duration"));
                f.setLanguage_id(resultSet.getInt("language_id"));
                temp.add(f);
            }
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("error!!!!!!!!!!!!!!");

        }
        return temp;
    }

    public ArrayList<FilmDTO> categoryList(int category_id, int pageNum) {
        String query = "select * from film f join film_category fc on f.film_id = fc.film_id join category ca on ca.category_id = fc.category_id where fc.category_id = ? limit ?,30";
        ArrayList<FilmDTO> temp = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, category_id);
            pstmt.setInt(2, pageNum);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                FilmDTO f = new FilmDTO();
                f.setFilm_id(resultSet.getInt("film_id"));
                f.setTitle(resultSet.getString("title"));
                f.setDescription(resultSet.getString("description"));
                f.setLength(resultSet.getInt("length"));
                f.setRelease_year(resultSet.getString("release_year"));
                f.setRental_duration(resultSet.getInt("rental_duration"));
                f.setLanguage_id(resultSet.getInt("language_id"));
                f.setCategoryName(resultSet.getString("name"));
                temp.add(f);
            }
            resultSet.close();
            pstmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;

    }


    public FilmDTO selectOne(int id) {
        FilmDTO f = null;

        String query = "select f.film_id,f.title,f.description, f.release_year, f.rental_duration, f.rental_rate, f.length, f.rating, a.first_name, a.last_name from film f join film_actor fa on fa.film_id = f.film_id join actor a on a.actor_id = fa.actor_id where f.film_id = ? group by f.film_id";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                f = new FilmDTO();
                f.setTitle(resultSet.getString("title"));
                f.setFilm_id(resultSet.getInt("film_id"));
                f.setDescription(resultSet.getString("description"));
                f.setLength(resultSet.getInt("length"));
                f.setRelease_year(resultSet.getString("release_year"));
                f.setRental_duration(resultSet.getInt("rental_duration"));
                f.setRating(resultSet.getString("rating"));
                f.setRental_rate(resultSet.getInt("rental_rate"));
                f.setFirst_name(resultSet.getString("first_name"));
                f.setLast_name(resultSet.getString("last_name"));
            }
            resultSet.close();
            pstmt.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return f;
    }

    public ArrayList<FilmDTO> searchFilmTitle(String title) {
        ArrayList<FilmDTO> temp = new ArrayList<>();
        String query = "select * from film where title like ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, "%" + title + "%");
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                FilmDTO f = new FilmDTO();
                f.setTitle(resultSet.getString("title"));
                f.setFilm_id(resultSet.getInt("film_id"));
                f.setRelease_year(resultSet.getString("release_year"));
                f.setRental_duration(resultSet.getInt("rental_duration"));
                f.setRating(resultSet.getString("rating"));
                f.setRental_rate(resultSet.getInt("rental_rate"));
                temp.add(f);
            }
            resultSet.close();
            pstmt.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return temp;


    }

    public void insertFilm(FilmDTO filmDTO, ArrayList<Integer> list) {
        String query = "insert into film (title,description,release_year,rental_duration,rental_rate,rating,length,last_update) values (?,?,?,?,?,?,?,now())";
        //1. 일단 영화를 등록하고
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, filmDTO.getTitle());
            pstmt.setString(2, filmDTO.getDescription());
            pstmt.setString(3, filmDTO.getRelease_year());
            pstmt.setInt(4, filmDTO.getRental_duration());
            pstmt.setInt(5, filmDTO.getRental_rate());
            pstmt.setString(6, filmDTO.getRating());
            pstmt.setInt(7, filmDTO.getLength());

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("777");
        }


    }
}
