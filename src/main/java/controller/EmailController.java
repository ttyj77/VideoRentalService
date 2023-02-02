package controller;

import model.CustomerDTO;
import org.apache.commons.mail.HtmlEmail;

public class EmailController {
    public static void main(String[] args) {

            // Mail Server 설정
            String charSet = "utf-8";
            String hostSMTP = "smtp.naver.com";

            String hostSMTPid = "gongahze";
            String hostSMTPpwd = "zbxldidtjd1!";

            // 보내는 사람 EMail, 제목, 내용
            String fromEmail = "gongahze@naver.com";
            String fromName = "gongahze@naver.com";
            String subject = "";
            String msg = "";

                subject = "Spring Homepage 임시 비밀번호 입니다.";
                msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
                msg += "<h3 style='color: blue;'>";
                msg += "주희" + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.</h3>";
                msg += "<p>임시 비밀번호 : ";
                msg += "1234" + "</p></div>";
            // 받는 사람 E-Mail 주소
            String mail = "ttyj77@naver.com";
            try {
                HtmlEmail email = new HtmlEmail();
                email.setDebug(true);
                email.setCharset(charSet);
                email.setSSL(true);
                email.setHostName(hostSMTP);
                email.setSmtpPort(587);

                email.setAuthentication(hostSMTPid, hostSMTPpwd);
                email.setTLS(true);
                email.addTo(mail, charSet);
                email.setFrom(fromEmail, fromName, charSet);
                email.setSubject(subject);
                email.setHtmlMsg(msg);
                email.send();
            } catch (Exception e) {
                System.out.println("메일발송 실패 : " + e);
            }
        }

}
