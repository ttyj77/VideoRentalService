package model;

import java.util.Date;

public class RentalRequestList {

    private int rental_id;

    public int getRental_id() {
        return rental_id;
    }

    public void setRental_id(int rental_id) {
        this.rental_id = rental_id;
    }

    private int customer_id;
    private int film_id;
    private Date request_date;
    private Date last_update;

    private String first_name;
    private String last_name;
    private String title;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public Date getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public RentalRequestList() {

    }

    public RentalRequestList(int rental_id) {
        this.rental_id = rental_id;
    }


    public boolean equals(Object o) {
        if (o instanceof RentalRequestList) {
            RentalRequestList f = (RentalRequestList) o;
            return rental_id == f.rental_id;
        }
        return false;
    }

    public RentalRequestList(RentalRequestList origin) {
        first_name = origin.first_name;
        last_name = origin.last_name;
        title = origin.title;
        email = origin.email;
    }

}
