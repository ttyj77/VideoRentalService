package viewer;

import controller.FilmController;
import controller.RentalController;
import model.CustomerDTO;
import model.RentalDTO;

import java.sql.Connection;
import java.util.ArrayList;
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


//    public void printRentalList() {
//        ArrayList<RentalDTO> list = rentalController.printRentalList();
//        if (list.isEmpty()) {
//            System.out.println("아직 렌탈 요청이 존재하지 않습니다.");
//        } else {
//            for (RentalDTO r : list) {
//                System.out.println("고객번호 : " + r.getCustomer_id() + "요청 영화아이디 : " + r.getFilm_id());
//            }
//        }
//
//
//    }


}
