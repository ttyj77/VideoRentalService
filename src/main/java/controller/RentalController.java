package controller;

import model.CustomerDTO;
import model.FilmDTO;
import model.RentalDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class RentalController {


    private Connection connection;
    private ArrayList<RentalDTO> rentalList;


    public RentalController(Connection connection) {
        this.connection = connection;
        rentalList = new ArrayList<>();

    }

    public void rental(CustomerDTO logIn, FilmDTO filmDTO) {
        RentalDTO r = new RentalDTO();
        r.setCustomer_id(logIn.getCustomer_id());
        r.setFilm_id(filmDTO.getFilm_id());
        rentalList.add(r);
        for (RentalDTO rentalDTO : rentalList){
            System.out.println(rentalDTO.getCustomer_id());
        }
    }

    public ArrayList<RentalDTO> printRentalList(){
        return rentalList;
    }
}
