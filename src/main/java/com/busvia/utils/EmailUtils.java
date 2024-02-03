package com.busvia.utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class EmailUtils {


//    @Autowired
//    private JavaMailSender mailSender;
//    public  void sendMail(String toEmail,
//                          String subject,
//                          String body){
//
//        SimpleMailMessage message=new SimpleMailMessage();
//
//        message.setFrom("anairsreejith1998@gmail.com");
//        message.setTo(toEmail);
//        message.setSubject(body);
//        message.setSubject(subject);
//        mailSender.send(message);
//
//        System.out.println("Mail send successfully.......");
//    }

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("anairsreejith1998@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);  // Set the body as HTML

            mailSender.send(message);

            System.out.println("Mail sent successfully.......");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to send email");
        }
    }

//    public boolean validateEmail(String email){
//
//        System.out.println(email);
//        String emailRegex =  "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
//
//        System.out.println(emailRegex);
//
//        if (!Pattern.matches(emailRegex, email)) {
//
//            return false;
//        }
//        return true;
//    }


//    public void sendSimpleMessage(String to , String subject ,String text ,List<String>list){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("anairsreejith1998@gmail.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        if(list!=null && list.size()>0)
//        message.setCc(getCcArray(list));
//        mailSender.send(message);
//
//    }
//
//    private  String[] getCcArray(List<String> ccList){
//        String []cc= new String[ccList.size()];
//        for (int i = 0; i < ccList.size() ; i++) {
//            cc[i]=  ccList.get(i);
//        }
//    return cc;
//    }

//    public void forgotMail(String to, String subject, String password) throws MessagingException {
//
//        MimeMessage message = emailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        helper.setFrom("anairsreejith1998@gmail.com");
//        helper.setTo(to);
//        helper.setSubject(subject);
//
//        String htmlMsg = "<p><b>Your login details for busVia.com</b><br>"
//                + "<b>Email:</b> " + to + "<br>"
//                + "<b>Password:</b> " + password + "<br>"
//                + "<a href=\"http://localhost:4200/\">Click here to login</a></p>";
//
//        helper.setText(htmlMsg, true);
//        emailSender.send(message);
//    }

//    public void forgotMail(String to , String subject, String password) throws Exception{
//
//        MimeMailMessage message = emailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message.getMimeMessage());
//        helper.setFrom("anairsreejith1998@gmail.com");
//        helper.setTo(to);
//        helper.setSubject(subject);
//        String htmlMsg ="<p><b> Your login details for busVia.com </b> <br><b>Email: </br>"+ to +
//                "<br><b> Password : </b>"+ password +"<br><a href=\"http://localhost:4200/\"> Click here to login </a></p>";
//        message.setContent(htmlMsg,"text/html");
//        emailSender.send(message);
//
//
//    }
}
