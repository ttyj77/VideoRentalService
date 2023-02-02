package viewer;

import controller.*;
import dbConn.ConnectionMaker;
import model.*;
import util.ScannerUtil;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class CustomerViewer {

    private final Scanner SCANNER;
    private Connection connection;
    private final String DATE_FORMAT = "yy/MM/dd";
    private CustomerDTO logIn;
    private CustomerViewer customerViewer;

    public CustomerViewer(ConnectionMaker connectionMaker, CustomerDTO logIn) {
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
        message = "아이디로 사용하실 이메일을 입려해주세요(회원용)";

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

        message = "사용하실 휴대폰번호를 입력해주세요";
        u.setPhone(ScannerUtil.nextLine(SCANNER, message));

        message = "현재 거주하는 나라의 이름을 입력해주세요 (검색가능)";
        u.setCountry(ScannerUtil.nextLine(SCANNER, message));

        message = "현재 거주하는 도시의 이름을 입력해주세요 (검색가능)";
        u.setCity(ScannerUtil.nextLine(SCANNER, message));

        message = "현재 거주지의 상세주소를 입력해주세요";
        u.setDetailsAddress(ScannerUtil.nextLine(SCANNER, message));

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
            message = "1. 대여요청보기  2. 회원조회  3. 회원정보변경  4.현장대여 5.영화등록 6.로그아웃 ";
            userChoice = ScannerUtil.nextInt(SCANNER, message, 5, 1);
        } else if (logIn.getRole() == 1) { //1.일반회원
            message = "7. 영화대여하기  8. 대여중인 영화보기 9. 개인정보관리  6.로그아웃";
            userChoice = ScannerUtil.nextInt(SCANNER, message, 8, 3);
        }
        while (logIn != null) {
            if (userChoice == 1) {
                showRentalList();
            } else if (userChoice == 2) {
                //회원조회 이름으로 나라로 핸드폰 번호로
                customerSearchMenu();
                showMenu();
//                showRentalFilm();
            } else if (userChoice == 3) {
                //회원정보 관리 이름으로 검색 이메일로검색
                updateCustomer(); //주소변경만 가능한데 회원 번호변경도 가능하게 할 것
                showMenu();
            } else if (userChoice == 4) {
                //현장 대여
                rentalNow();
                showMenu();
            } else if (userChoice == 5) {
                //영화등록
                insertFilm();
            } else if (userChoice == 6) {
                logIn = null;
                System.out.println("정상적으로 로그아웃 되었습니다.");
            } else if (userChoice == 7) {
                FilmViewer filmViewer = new FilmViewer(connection, SCANNER, logIn);
                filmViewer.showFilmRentalMenu();
                showMenu();
            } else if (userChoice == 8) {
                printCustomerRentalList();
                showMenu();
            } else if (userChoice == 9) {
                printOne();
            }
        }
    }

    private void insertFilm() {
        FilmDTO filmDTO = new FilmDTO();
        String message = "영화제목을 입력해주세요 ";
        filmDTO.setTitle(ScannerUtil.nextLine(SCANNER, message));

        message = "영화 줄거리를 입력해주세요 ";
        filmDTO.setDescription(ScannerUtil.nextLine(SCANNER, message));


        message = "출연배우를 이름으로 검색해 추가해주세요 그만하기를 원하시면 x을 눌러주세요 ";
        String actor = ScannerUtil.nextLine(SCANNER, message);
        ArrayList<Integer> list = new ArrayList<>();
        while (!actor.equalsIgnoreCase("X")) {
//            actor = ScannerUtil.nextLine(SCANNER, message);
            ActorController actorController = new ActorController(connection);
            actor = ScannerUtil.nextLine(SCANNER, message);
            ArrayList<ActorDTO> actorList = actorController.actorList(actor);

            message = "배우번호를 선택해주세요 뒤로가려면 0을 눌러주세요";
            for (ActorDTO a : actorList) {
                System.out.println(a.getActor_id() + ". " + a.getFirst_name() + " " + a.getLast_name());
            }
            int userChoice = ScannerUtil.nextInt(SCANNER, message);
            while (userChoice != 0 &!actorList.contains(new ActorDTO(userChoice))) {
                System.out.println("잘못선택하셨습니다.");
                userChoice = ScannerUtil.nextInt(SCANNER, message);
            }
            list.add(userChoice);
            System.out.println("등록되었습니다.");
        }
        filmDTO.setActor_id(list);
        message = "영화 개봉년도를 입력해주세요 ";
        filmDTO.setRelease_year(ScannerUtil.nextLine(SCANNER, message));

        message = "영화 관람등급을 입력해주세요 ";
        filmDTO.setRating(ScannerUtil.nextLine(SCANNER, message));

        message = "상영시간을 입력해주세요 (딘위 : 분) ";
        filmDTO.setLength(ScannerUtil.nextInt(SCANNER, message));

        message = "대여기간을 입력해주세요 (딘위 : 일) ";
        filmDTO.setRental_duration(ScannerUtil.nextInt(SCANNER, message));

        message = "대여금액을 입력해주세요 (단위 : $) ";
        filmDTO.setRental_rate(ScannerUtil.nextInt(SCANNER, message));


        FilmController filmController = new FilmController(connection);
        filmController.insertFilm(filmDTO, list);

    }

    private void rentalNow() {
        CustomerController customerController = new CustomerController(connection);
        String message = "성을 입력해주세요";
        ArrayList<CustomerSearchDTO> list = name(message, customerController);

        message = "회원번호 입력 뒤로가려면 0을 눌러주세요";
        int customer_id = ScannerUtil.nextInt(SCANNER, message);

        while (customer_id != 0 && !list.contains(new CustomerSearchDTO(customer_id))) {
            System.out.println("잘못 입력하셨습니다.");
            customer_id = ScannerUtil.nextInt(SCANNER, message);
        }
        if (customer_id == 0) {
            showMenu();
        } else {
            message = "빌릴영화제목을 입력해주세요";
            ArrayList<FilmDTO> filmList = film(message);

            message = "대여할 영화 번호를 클릭해주세요 뒤로가려면 0을 눌러주세요";
            int film_id = ScannerUtil.nextInt(SCANNER, message);
            while (film_id != 0 && !filmList.contains(new FilmDTO(film_id))) {
                System.out.println("잘못 입력하셨습니다.");
                film_id = ScannerUtil.nextInt(SCANNER, message);
            }
            if (film_id == 0) {
                showMenu();
            } else {
                RentalController rentalController = new RentalController(connection);
                RentalDTO r = new RentalDTO();
                r.setCustomer_id(customer_id);
                r.setFilm_id(film_id);
                int staff_id = logIn.getStore_id();
                rentalController.staffInsert(r, staff_id); //customer_id랑 film_id필요
                System.out.println("대여가 완료되었습니다.");
            }
        }
    }

    private ArrayList<FilmDTO> film(String message) {
        String title = ScannerUtil.nextLine(SCANNER, message);
        FilmController filmController = new FilmController(connection);
        ArrayList<FilmDTO> filmList = filmController.searchFilmTitle(title);
        printFilmList(filmList);
        return filmList;
    }

    private ArrayList<CustomerSearchDTO> name(String message, CustomerController customerController) {

        String first_name = ScannerUtil.nextLine(SCANNER, message);
        message = "이름을 입력해주세요";
        String last_name = ScannerUtil.nextLine(SCANNER, message);
        ArrayList<CustomerSearchDTO> list = customerController.customerSearchName(first_name, last_name);
        printCustomerSearchName(list);
        return list;
    }

    private void printFilmList(ArrayList<FilmDTO> list) {
        if (list.isEmpty()) {
            System.out.println("일치하는 영화가 없습니다.");
            String message = "빌릴영화제목을 입력해주세요";
            film(message);
        } else {
            System.out.println("ID.       제목         대여기간        금액");
            for (FilmDTO f : list) {
                System.out.println(f.getFilm_id() + ". " + f.getTitle() + "         " + f.getRental_duration() + "일        " + f.getRental_rate());
            }
        }
    }

    private void customerSearchMenu() {
        CustomerController customerController = new CustomerController(connection);
        String message = "1.이름으로 검색 2.핸드폰 뒷자리(4개) 3.뒤로가기";
        int userChoice = ScannerUtil.nextInt(SCANNER, message, 3, 1);
        if (userChoice == 1) {
            message = "성을 입력해주세요";
            ArrayList<CustomerSearchDTO> list = name(message, customerController);
        } else if (userChoice == 2) {
            message = "핸드폰 뒷자리 4번호를 입력해주세요";
            String phone4 = ScannerUtil.nextLine(SCANNER, message);
            customerController.searchPhoneNumber(phone4);
            ArrayList<CustomerSearchDTO> list = customerController.searchPhoneNumber(phone4);
            printCustomerSearchPhone(list);
        }
        if (userChoice == 3) {

        }
    }

    private void printCustomerSearchPhone(ArrayList<CustomerSearchDTO> list) {
        if (list.isEmpty()) {
            System.out.println("일치하는 회원이 없습니다.");
        } else {
            System.out.println("ID.       이름       핸드폰 번호                     주소");
            for (CustomerSearchDTO c : list) {
                System.out.println(c.getCustomer_id() + ". " + c.getFirst_name() + " " + c.getLast_name() + "  " + c.getPhone() + "     " + c.getAddress());
            }
        }
    }

    private void printCustomerSearchName(ArrayList<CustomerSearchDTO> list) {
        if (list.isEmpty()) {
            System.out.println("일치하는 회원이 없습니다.");
            rentalNow();
        } else {
            System.out.println("ID.     이름                이메일                            주소");
            for (CustomerSearchDTO c : list) {
                System.out.println(c.getCustomer_id() + ".  " + c.getFirst_name() + " " + c.getLast_name() + "  " + c.getEmail() + "     " + c.getAddress());
            }
        }
    }


    private void printCustomerRentalList() {
        RentalController rentalController = new RentalController(connection);
        ArrayList<RentalDTO> list = rentalController.rentalList(logIn.getCustomer_id());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        String message;
        if (list.isEmpty()) {
            System.out.println("현재 대여중인 DVD가 없습니다.");
            message = "2. 이전 대여기록 열람하기 3.뒤로가기";
        } else {
            System.out.println("           [  대여중인 목록  ]   ");
            System.out.println("일련번호      영화제목          빌린날         반납예정일");
            System.out.println();
            Calendar cal = Calendar.getInstance();
            for (RentalDTO r : list) {
                cal.setTime((r.getRental_date()));
                cal.add(Calendar.DAY_OF_MONTH, r.getRental_duration());
                System.out.print(r.getRental_id() + ".  " + r.getTitle() + "    -    ");
                System.out.println(df.format(r.getRental_date()) + "       " + df.format(cal.getTime()));
            }
            message = "1.DVD 반납하기 2. 이전 대여기록 열람하기 3.뒤로가기";
        }

        int userChoice = ScannerUtil.nextInt(SCANNER, message);


        if (userChoice == 1) { //DVD 반납하기
            int rental_id = ScannerUtil.nextInt(SCANNER, message);
            while (!list.contains(new RentalDTO(rental_id))) {
                System.out.println("잘못입력하셨습니다.");
                userChoice = ScannerUtil.nextInt(SCANNER, message);
            }
            returnFilm(userChoice);

        } else if (userChoice == 2) { //이전 대여기록
            oldRentalList();
        } else if (userChoice == 3) {

        }


    }

    private void oldRentalList() {
        RentalController rentalController = new RentalController(connection);
        ArrayList<RentalDTO> list = rentalController.oldRentalList(logIn.getCustomer_id());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        String message;
        if (list.isEmpty()) {
            System.out.println("대여했던 DVD가 없습니다.");
            message = "3.뒤로가기";
        } else {
            System.out.println("           [  대여 기록  ]   ");
            System.out.println("일련번호      영화제목          빌린날         반납일");
            System.out.println();
            Calendar cal = Calendar.getInstance();
            for (RentalDTO r : list) {
                System.out.print(r.getRental_id() + ".  " + r.getTitle() + "    -    ");

                System.out.println(df.format(r.getRental_date()) + "       " + df.format(r.getReturn_date()));
            }
            message = "3.뒤로가기";
        }
        int userChoice = ScannerUtil.nextInt(SCANNER, message, 3, 3);
        if (userChoice == 3) {

        }

    }

    //고객이 반납요청한 영화
    private void returnFilm(int rental_id) {
        String message = "정말로 반납하시겠습니까?  Y/N";
        String yesNo = ScannerUtil.nextLine(SCANNER, message);

        if (yesNo.equalsIgnoreCase("Y")) {
            message = "비밀번호를 입력해주세요";
            String password = ScannerUtil.nextLine(SCANNER, message);
            CustomerController customerController = new CustomerController(connection);

            if (customerController.auth(logIn.getEmail(), password) != null) {
                customerController.returnFilm(rental_id);
            }
        }
        message = "다른분들을 위해 평점을 남겨주세요?  Y/N";
        yesNo = ScannerUtil.nextLine(SCANNER, message);

        if (yesNo.equalsIgnoreCase("Y")) {
            message = "해당 영화의 만족도는 어떠셨나요? (1~10)";
            int rating = ScannerUtil.nextInt(SCANNER, message, 10, 1);
            ReplyController replyController = new ReplyController(connection);
            replyController.insertRating(rental_id, rating);
        }
    }

    private void showRentalList() {
        RentalController rentalController = new RentalController(connection);
        ArrayList<RentalRequestList> list = rentalController.rental();
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);

        System.out.println("대여신청 목록");
        for (RentalRequestList r : list) {
            System.out.printf("%d.  - %s %s  %s \n", r.getRental_id(), r.getFirst_name(), r.getLast_name(), r.getTitle(), df.format(r.getRequest_date()));
        }

        String message = "대여를 승인할 요청번호를 입력해주세요. 0r 뒤로가려면 0을 눌러주세요";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);

        while (userChoice != 0 && !list.contains(new RentalRequestList(userChoice))) {
            System.out.println("잘못 입력하셨습니다.");
            userChoice = ScannerUtil.nextInt(SCANNER, message);
        }
        if (userChoice != 0) {
            //승인하는 코드
            rentalController.rentalInsert(userChoice, logIn.getStore_id());
            System.out.printf("승인 이메일을 회원에게 전송하였습니다. ");
            EmailEx emailEx = new EmailEx();
            RentalRequestList temp = new RentalRequestList(userChoice);
            RentalRequestList re = new RentalRequestList(list.get(list.indexOf(temp)));
            System.out.printf("---" + re.getFirst_name() + "==" + re.getLast_name() + "++" + re.getEmail() + "11" + re.getTitle());
            emailEx.mail(re.getFirst_name(), re.getLast_name(), re.getEmail(), re.getTitle());
        } else if (userChoice == 0) {
            showMenu();
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
        } else if (userChoice == 3) {
            showMenu();
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

    private void updateCustomer() {
        String message;
        message = "회원의 이메일을 알려주세요";
        CustomerController customerController = new CustomerController(connection);
        String customer_email = ScannerUtil.nextLine(SCANNER, message);
        CustomerDTO u = customerController.customerSearchEmail(customer_email); //현재 바꾸고 싶은 회원의 정보
        while (u.getRole() == 2) {
            System.out.println("직원의 정보는 수정할 수 없습니다.");
            customer_email = ScannerUtil.nextLine(SCANNER, message);
            u = customerController.customerSearchEmail(customer_email);
        }

        while (u == null) {
            System.out.println("이메일이 일치하는 회원이 없습니다");
            customer_email = ScannerUtil.nextLine(SCANNER, message);
            u = customerController.customerSearchEmail(customer_email);
        }

        message = "새로운 주소의 국가를 입력해주세요"; //서울
        String newAddressCountry = ScannerUtil.nextLine(SCANNER, message);
        int country_id = customerController.selectCountry(newAddressCountry);
        while (country_id == 0) {
            System.out.println("국가가 일치하는 정보가 없습니다.");
            newAddressCountry = ScannerUtil.nextLine(SCANNER, message);
            country_id = customerController.selectCountry(newAddressCountry);
        }

        message = "새로운 주소의 도시를 입력해주세요"; //서울
        String newAddressCity = ScannerUtil.nextLine(SCANNER, message);
        int city_id = customerController.selectCity(newAddressCity, country_id);

        while (city_id == 0) {
            System.out.println("도시가 일치하는 정보가 없습니다.");
            newAddressCity = ScannerUtil.nextLine(SCANNER, message);
            city_id = customerController.selectCity(newAddressCity, country_id);
        }

        message = "새로운 주소의 상세정보 입력해주세요"; //서울
        String detailAddress = ScannerUtil.nextLine(SCANNER, message);
        customerController.addresUpdate(detailAddress, city_id, u.getAddress_id());

//        customerController.updateCustomer(u, city_id);
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
