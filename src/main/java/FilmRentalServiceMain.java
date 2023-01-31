import dbConn.ConnectionMaker;
import dbConn.MysqlConnectionMaker;
import model.CustomerDTO;
import viewer.CustomerViewer;
import viewer.FilmViewer;

import java.sql.Connection;

public class FilmRentalServiceMain {
    public static void main(String[] args) {
        ConnectionMaker connectionMaker = new MysqlConnectionMaker();
        CustomerDTO logIn = new CustomerDTO();
        CustomerViewer customerViewer = new CustomerViewer(connectionMaker,logIn);


        customerViewer.showIndex();
    }



}
