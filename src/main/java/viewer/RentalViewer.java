package viewer;

import controller.FilmController;
import controller.RentalController;
import model.CustomerDTO;

import java.sql.Connection;
import java.util.Scanner;

public class RentalViewer {

    private final Scanner SCANNER;
    private Connection connection;
    private CustomerDTO logIn;
    private final String DATE_FORMAT = "yy/MM/dd HH:mm:ss";
    private RentalController rentalController;
    private CustomerViewer customerViewer;

    public RentalViewer(Connection connection, Scanner scanner, CustomerDTO logIn) {
        this.SCANNER = scanner;
        this.connection = connection;
        this.logIn = logIn;
        rentalController = new RentalController(this.connection);
    }



    public void printRentalList(){
        rentalController.printRentalList();


    }


}
