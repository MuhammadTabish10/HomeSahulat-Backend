package com.HomeSahulat.Util;

import com.HomeSahulat.model.Booking;
import com.HomeSahulat.model.ServiceProvider;
import com.HomeSahulat.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    private static final String USER = "user";
    private static final String SERVICE_PROVIDER = "serviceProvider";
    private final JavaMailSender javaMailSender;

    public EmailUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String sender;

    @Async
    public void sendEmailForBooking(User user, ServiceProvider serviceProvider, Booking booking, String userType) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setSubject("Welcome to Your HomeSahulat!");

            if(USER.equalsIgnoreCase(userType)){
                helper.setTo(user.getEmail());
                String greeting = "Dear " + user.getName() + ",\n\n";
                String emailContent = greeting
                        + "Thank you for choosing HomeSahulat! Your service booking details are as follows:\n\n"
                        + "Service Provider: " + serviceProvider.getUser().getName() + "\n"
                        + "Service: " + serviceProvider.getServices().getName() + "\n"
                        + "Appointment Date: " + booking.getAppointmentDate() + "\n"
                        + "Appointment Time: " + booking.getAppointmentTime() + "\n\n"
                        + "We look forward to providing you with our services.\n\n"
                        + "Best regards,\n"
                        + "HomeSahulat Team";
                helper.setText(emailContent);

            }
            else if (SERVICE_PROVIDER.equalsIgnoreCase(userType)) {
                helper.setTo(serviceProvider.getUser().getEmail());
                String greeting = "Dear " + serviceProvider.getUser().getName() + ",\n\n";
                String emailContent = greeting
                        + "You have a new booking request from a customer. The details are as follows:\n\n"
                        + "Customer Name: " + user.getName() + "\n"
                        + "Service: " + serviceProvider.getServices().getName() + "\n"
                        + "Appointment Date: " + booking.getAppointmentDate() + "\n"
                        + "Appointment Time: " + booking.getAppointmentTime() + "\n\n"
                        + "Please confirm the booking at your earliest convenience.\n\n"
                        + "Best regards,\n"
                        + "HomeSahulat Team";
                helper.setText(emailContent);
            }
            javaMailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    @Async
    public void sendPasswordResetEmail(User user, String resetCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(user.getEmail());
            helper.setSubject("Password Reset Request");

            String emailContent = "Dear " + user.getName() + ",\n\n"
                    + "You have requested to reset your password. Please use the following code in the app to proceed with the password reset:\n\n"
                    + "ResetCode: " + resetCode + "\n\n"
                    + "If you did not request a password reset, please ignore this email.\n\n"
                    + "Best regards,\n"
                    + "HomeSahulat Team";

            helper.setText(emailContent);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
