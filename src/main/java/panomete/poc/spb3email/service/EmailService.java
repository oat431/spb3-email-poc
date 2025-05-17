package panomete.poc.spb3email.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
