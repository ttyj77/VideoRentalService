package controller;

import model.ActorDTO;
import model.CustomerDTO;
import model.FilmDTO;
import model.RentalDTO;

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

    public ArrayList<FilmDTO> filmAllList() {
        String query = "select * from film";
        ArrayList<FilmDTO> temp = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
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
            e.printStackTrace();
        }
        return temp;

    }

    public ArrayList<FilmDTO> ratingList(String rating) {
        String query = "select * from film where rating = ? limit 100";
        ArrayList<FilmDTO> temp = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, rating);
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
            e.printStackTrace();
        }
        return temp;
    }

    public ArrayList<FilmDTO> categoryList(int category_id) {
        String query = "select * from film f join film_category fc on f.film_id = fc.film_id join category ca on ca.category_id = fc.category_id where fc.category_id = ?";
        ArrayList<FilmDTO> temp = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, category_id);
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
                f.setRental_rate(resultSet.getBigDecimal("rental_rate"));
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


}
