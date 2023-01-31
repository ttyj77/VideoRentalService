package viewer;

import controller.CustomerController;
import controller.FilmController;
import dbConn.ConnectionMaker;
import model.CustomerDTO;
import model.FilmDTO;
import util.ScannerUtil;

import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class CustomerViewer {

    private final Scanner SCANNER;
    private Connection connection;
    private CustomerDTO logIn;
    private CustomerViewer customerViewer;

    public CustomerViewer(ConnectionMaker connectionMaker,CustomerDTO logIn) {
        SCANNER = new Scanner(System.in);
        connection = connectionMaker.makeConnection();
        this.logIn = logIn;
    }



    //가장 처음 보이는 index
    public void showIndex() {
        String message = "1. 로그인 2. 회원가입 3. 종료";
        while (true) {
            int userChoice = ScannerUtil.nextInt(SCANNER, message);
            if (userChoice == 1) {
                auth();

                if (logIn != null) {

                    showMenu();
                }
            } else if (userChoice == 2) {
                register();
            } else if (userChoice == 3) {
                System.out.println("사용해주셔서 감사합니다.");
                break;
            }
        }
    }

    private void register() {
        String message;
        message = "아이디로 사용하실 이메일을 입려해주세요";

        CustomerDTO u = new CustomerDTO();
        u.setEmail(ScannerUtil.nextLine(SCANNER, message));

        message = "사용하실 비밀번호를 입력해주세요";
        u.setPassword(ScannerUtil.nextLine(SCANNER, message));

        message = "사용하실 이름(first_name) 입력해주세요";
        u.setFirst_name(ScannerUtil.nextLine(SCANNER, message));

        message = "사용하실 이름(last_name) 입력해주세요";
        u.setLast_name(ScannerUtil.nextLine(SCANNER, message));

        message = "사용하실 닉네임을 입력해주세요";
        u.setNickname(ScannerUtil.nextLine(SCANNER, message));

        CustomerController userController = new CustomerController(connection);

        if (!userController.insert(u)) {
            System.out.println("중복된 이메일 입니다.");
            message = "새로 가입을 시도하시겠습니까? Y/N";
            String yesNo = ScannerUtil.nextLine(SCANNER, message);
            if (yesNo.equalsIgnoreCase("Y")) {
                register();
            }
        }
    }

    private void auth() {
        //객체지향프로그랭은 자식이 필요한 결과만 알면된다.
        String message;
        message = "이메일을 입력해주세요";
        String email = ScannerUtil.nextLine(SCANNER, message);

        message = "비밀번호를 입력해주세요";
        String password = ScannerUtil.nextLine(SCANNER, message);

        CustomerController userController = new CustomerController(connection);
        logIn = userController.auth(email, password);

        if (logIn == null) {
            System.out.println("로그인정보가 정확하지 않습니다.");
        }
    }

    //로그인 하면 보이는 메뉴
    public void showMenu() {
        String message;
        int userChoice = 0;
        if (logIn.getRole() == 2) { //2.staff 영화대여점
            message = "1. 대여요청보기  2. 회원관리  3. 개인정보관리  4.로그아웃 ";
            userChoice = ScannerUtil.nextInt(SCANNER, message, 4, 1);
        } else if (logIn.getRole() == 1) { //1.일반회원
                message = "5. 영화대여하기  6. 대여중인 영화보기 3. 회원정보관리  4.로그아웃";
            userChoice = ScannerUtil.nextInt(SCANNER, message, 6, 3);
        }
        while (!(logIn == null)) {
            if (userChoice == 1) {
                RentalViewer rentalViewer = new RentalViewer(connection,SCANNER,logIn);
                rentalViewer.printRentalList();
            } else if (userChoice == 2) {
//                showRentalFilm();
            } else if (userChoice == 3) {
                printOne();
            } else if (userChoice == 4) {
                logIn = null;
                System.out.println("정상적으로 로그아웃 되었습니다.");
                break;
            } else if (userChoice == 5) {
                FilmViewer filmViewer = new FilmViewer(connection, SCANNER, logIn);
                filmViewer.showFilmRentalMenu();
            } else if (userChoice == 6) {

            }
        }
    }

    //회원정보 관리
    private void printOne() {
        String activate = "활성";
        System.out.println("회원번호 :  " + logIn.getCustomer_id());
        System.out.println("회원 닉네임 : " + logIn.getNickname());
        System.out.println("회원 이름 : " + logIn.getFirst_name() + " " + logIn.getLast_name());
        System.out.println("회원 이메일 : " + logIn.getEmail());
        if (logIn.getActive() == 0) {
            activate = "비활성";
        }
        System.out.println("현재 회원 상태 : " + activate);
        System.out.println("=====================================");
        String message = "1. 수정 2. 삭제 3. 뒤로가기";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);
        if (userChoice == 1) {
            update();
        } else if (userChoice == 2) {
            delete();
        }
    }

    private void update() {
        System.out.println("회원번호 :  " + logIn.getCustomer_id());
        System.out.println("회원 닉네임 ; " + logIn.getNickname());

        String message;
        message = "새로운 닉네임을 입력해주세요";
        String newNickname = ScannerUtil.nextLine(SCANNER, message);

        message = "기본 비밀번호 입력해주세요";
        String oldPassword = ScannerUtil.nextLine(SCANNER, message);

        message = "새로운 비밀번호 입력해주세요";
        String newPassword = ScannerUtil.nextLine(SCANNER, message);

        CustomerController customerController = new CustomerController(connection);

        if (customerController.auth(logIn.getEmail(), oldPassword) != null) {
            logIn.setNickname(newNickname);
            logIn.setPassword(newPassword);

            customerController.update(logIn);

        } else {
            System.out.println("비밀번호를 다시 확인해주세요");

        }
    }

    private void delete() {
        String message = "정말로 삭제하시겠습니까?  Y/N";
        String yesNo = ScannerUtil.nextLine(SCANNER, message);

        if (yesNo.equalsIgnoreCase("Y")) {
            message = "비밀번호를 입력해주세요";
            String password = ScannerUtil.nextLine(SCANNER, message);
            CustomerController userController = new CustomerController(connection);

            if (userController.auth(logIn.getEmail(), password) != null) {
                userController.delete(logIn.getCustomer_id());
                logIn = null;
            }
        }
    }


}
