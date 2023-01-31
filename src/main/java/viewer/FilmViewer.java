package viewer;

import controller.CustomerController;
import controller.FilmController;
import controller.RentalController;
import dbConn.ConnectionMaker;
import dbConn.MysqlConnectionMaker;
import model.ActorDTO;
import model.CustomerDTO;
import model.FilmDTO;
import util.ScannerUtil;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class FilmViewer {

    private final Scanner SCANNER;
    private Connection connection;
    private CustomerDTO logIn;
    private final String DATE_FORMAT = "yy/MM/dd HH:mm:ss";
    private FilmController filmController;
    private RentalController rentalController;
    private CustomerViewer customerViewer;
    private CustomerController customerController;
    ConnectionMaker connectionMaker = new MysqlConnectionMaker();

    public FilmViewer(Connection connection, Scanner scanner, CustomerDTO logIn) {
        this.SCANNER = scanner;
        this.connection = connection;
        this.logIn = logIn;
        filmController = new FilmController(this.connection);
        rentalController = new RentalController(this.connection);
        customerController = new CustomerController(connection);
        customerViewer = new CustomerViewer(connectionMaker, logIn);
    }


    //customer ShowMenu()에서 영화대여하기 눌렀을 때
    public void showFilmRentalMenu() {
        String message = "1. 모든영화목록보기  2. 조건별 검색하기  3. 뒤로가기 ";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);
        if (userChoice == 1) {
            printFilmList();
        } else if (userChoice == 2) {
            searchMenu();
        } else if (userChoice == 3) {
            customerViewer.showMenu();
        }

    }

    private void searchMenu() {
        String message = "1. 관람 등급별 보기  2. 장르별로 보기  3. 가장최근 등록된 순으로 보기  4.인기순으로 보기(대여횟수) 5.뒤로가기";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);
        if (userChoice == 1) {
            ratingMenu();
        } else if (userChoice == 2) {
            categoryMenu();
        } else if (userChoice == 3) {
            //바로 쿼리문으로 연결
        } else if (userChoice == 4) {
            //가장 최근 등록순으로 보여주기
        } else if (userChoice == 5) {
            showFilmRentalMenu();
        }
    }


    private void printFilmList() {
        FilmController filmController = new FilmController(connection);
        ArrayList<FilmDTO> list = filmController.filmAllList();
        if (list.isEmpty()) {
            System.out.println("등록된 영화가 없습니다.");
        } else {
            for (FilmDTO f : list) {
                System.out.printf("%d. %s 개봉연도 %s\n", f.getFilm_id(), f.getTitle(), f.getRelease_year());
            }
            String message = "상세보기할 영화DVD의 번호를 입력해주세요 or 뒤로가기 원하시면 0을 입력해주세요";
            int userChoice = ScannerUtil.nextInt(SCANNER, message);
            while (userChoice != 0 && !list.contains(new FilmDTO(userChoice))) {
                System.out.println("잘못입력하셨습니다.");
                userChoice = ScannerUtil.nextInt(SCANNER, message);
            }
            if (userChoice != 0) {
                printOne(userChoice);
            }
        }
    }

    private void printOne(int id) {
//        UserController userController = new UserController(connection);
//        ReplyViewer replyViewer = new ReplyViewer(SCANNER, connection, logIn);
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        FilmDTO filmDTO = filmController.selectOne(id);

        System.out.println("=================================================");
        System.out.println(filmDTO.getTitle());
        System.out.println("-------------------------------------------------");
        System.out.println("영화 번호: " + filmDTO.getFilm_id());
        System.out.println("대표 출연배우: " + filmDTO.getFirst_name() + " " + filmDTO.getLast_name());
        System.out.println("개봉년도 : " + filmDTO.getRelease_year() + "  상영시간 : " + filmDTO.getLength() + "  상영등급 : " + filmDTO.getRating());
        System.out.println("-------------------------------------------------");
        System.out.println("대여비용 : " + filmDTO.getRental_rate() + "  대여기간 : " + filmDTO.getRental_duration());
        System.out.println("-------------------------------------------------");
        System.out.println("줄거리 : " + filmDTO.getDescription());
        System.out.println("-------------------------------------------------");
        System.out.println("대여 평점");
        System.out.println("-------------------------------------------------");
//        replyViewer.printAll(id);
        System.out.println("=================================================");
        String message;
        int userChoice;

//        if (boardDTO.getWriterId() == logIn.getId()) {
        message = "1. 대여하기 2. 뒤로가기";
        userChoice = ScannerUtil.nextInt(SCANNER, message, 2, 1);
//        } else {
//            message = "3. 댓글 메뉴 4. 뒤로 가기";
//            userChoice = ScannerUtil.nextInt(SCANNER, message, 4, 3);
//        }

        if (userChoice == 1) {
            rental(logIn, filmDTO);
        } else if (userChoice == 2) {
            showFilmRentalMenu();
        } else if (userChoice == 3) {
//            replyViewer.showMenu(id);
//            printOne(id);
        } else if (userChoice == 4) {
//            printList();
        }
    }

    public void rental(CustomerDTO logIn, FilmDTO filmDTO) {
        String message = filmDTO.getTitle() + " 를" + "대여하시겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(SCANNER, message);
        if (yesNo.equalsIgnoreCase("Y")) {
            System.out.println("대여비용은 " + filmDTO.getRental_rate() + "$ 입니다.");
            rentalController.rental(logIn, filmDTO);
//            showFilmRentalMenu();
        } else {

            printOne(filmDTO.getFilm_id());
        }
    }

    private void ratingMenu() {
        String message = "1. G  2. PG  3. PG-13  4.R  5. NC-17  6. 뒤로가기 ";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);
        if (userChoice == 1) {
            PrintRatingList("G");
        } else if (userChoice == 2) {
            PrintRatingList("PG");
        } else if (userChoice == 3) {
            PrintRatingList("PG-13");
        } else if (userChoice == 4) {
            PrintRatingList("R");
        } else if (userChoice == 5) {
            PrintRatingList("NC-17");
        } else if (userChoice == 6) {
            searchMenu();
        }
    }

    private void categoryMenu() {
        String message = "1. Action  2. Animation  3. Children  4.Classics  " +
                "5. Comedy 6. Documentary  7. Drama  8. Family  9.Foreign \n 10. Games " +
                "11. Horror  12. Music  13. New  14.Sci-Fi  15. Sports  16. Travel  뒤로가시려면 0을 입력하세요";
        int userChoice = ScannerUtil.nextInt(SCANNER, message, 16, 0);

        if (userChoice != 0) {
            PrintCategoryList(userChoice);
        } else if (userChoice == 0) {
            searchMenu();
        }
    }

    public void PrintRatingList(String rating) {
        ArrayList<FilmDTO> list = filmController.ratingList(rating);
        if (list.isEmpty()) {
            System.out.println("해당하는 영화가 없습니다.");
        } else {
            for (FilmDTO f : list) {
                System.out.printf("%d. %s 개봉연도 %s\n", f.getFilm_id(), f.getTitle(), f.getRelease_year());
            }
            String message = "상세보기할 영화DVD의 번호를 입력해주세요 or 뒤로가기 원하시면 0을 입력해주세요";
            int userChoice = ScannerUtil.nextInt(SCANNER, message);
            while (userChoice != 0 && !list.contains(new FilmDTO(userChoice))) {
                System.out.println("잘못입력하셨습니다.");
                userChoice = ScannerUtil.nextInt(SCANNER, message);
            }
            if (userChoice != 0) {
                printOne(userChoice);
            }
        }
    }

    public void PrintCategoryList(int category_id) {
        ArrayList<FilmDTO> list = filmController.categoryList(category_id);
        for (FilmDTO f : list) {
            System.out.printf("%d. %s  - 장르 : %s\n", f.getFilm_id(), f.getTitle(), f.getCategoryName());
        }
        String message = "상세보기할 영화DVD의 번호를 입력해주세요 or 뒤로가기 원하시면 0을 입력해주세요";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);
        while (userChoice != 0 && !list.contains(new FilmDTO(userChoice))) {
            System.out.println("잘못입력하셨습니다.");
            userChoice = ScannerUtil.nextInt(SCANNER, message);
        }
        if (userChoice != 0) {
            printOne(userChoice);
        }
    }
}


