package model;

import java.math.BigDecimal;
import java.util.Date;

public class FilmDTO {

    private int film_id;
    private String title;
    private String description;
    private String release_year;
    private int language_id;
    private int original_language_id;
    private int rental_duration;
    private BigDecimal rental_rate;
    private int length;
    private BigDecimal replacement_cost;
    private String rating; //enum 타입인데 '-' 때문에 지정이 안됨
    private BigDecimal special_features;
    private Date last_update;

    private String last_name;
    private String first_name;

    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelease_year() {
        return release_year;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public int getOriginal_language_id() {
        return original_language_id;
    }

    public void setOriginal_language_id(int original_language_id) {
        this.original_language_id = original_language_id;
    }

    public int getRental_duration() {
        return rental_duration;
    }

    public void setRental_duration(int rental_duration) {
        this.rental_duration = rental_duration;
    }

    public BigDecimal getRental_rate() {
        return rental_rate;
    }

    public void setRental_rate(BigDecimal rental_rate) {
        this.rental_rate = rental_rate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public BigDecimal getReplacement_cost() {
        return replacement_cost;
    }

    public void setReplacement_cost(BigDecimal replacement_cost) {
        this.replacement_cost = replacement_cost;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public BigDecimal getSpecial_features() {
        return special_features;
    }

    public void setSpecial_features(BigDecimal special_features) {
        this.special_features = special_features;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public FilmDTO(int film_id) {
        this.film_id = film_id;
    }

    public boolean equals(Object o) {
        if (o instanceof FilmDTO) {
            FilmDTO f = (FilmDTO) o;
            return film_id == f.film_id;
        }
        return false;
    }


    public FilmDTO() {

    }
}
