package model;

import java.util.Date;

public class RentalDTO {
    private int rental_id;
    private Date rental_date;
    private int inventory_id;
    private int customer_id;
    private Date return_date;
    private int staff_id;
    private Date last_update;
    private int film_id;
    private int rental_duration;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRental_duration() {
        return rental_duration;
    }

    public void setRental_duration(int rental_duration) {
        this.rental_duration = rental_duration;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public int getRental_id() {
        return rental_id;
    }

    public void setRental_id(int rental_id) {
        this.rental_id = rental_id;
    }

    public Date getRental_date() {
        return rental_date;
    }

    public void setRental_date(Date rental_date) {
        this.rental_date = rental_date;
    }

    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public RentalDTO(RentalDTO origin) {
        rental_id = origin.getRental_id();
        film_id = origin.getFilm_id();
        customer_id = origin.getCustomer_id();
    }

    public RentalDTO() {

    }
    public RentalDTO(int rental_id){
        this.rental_id = rental_id;
    }

    public boolean equals(Object o) {
        if (o instanceof RentalDTO) {
            RentalDTO f = (RentalDTO) o;
            return rental_id == f.rental_id;
        }
        return false;
    }

}
