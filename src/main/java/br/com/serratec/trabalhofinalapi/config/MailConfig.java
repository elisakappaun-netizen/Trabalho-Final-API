package br.com.serratec.trabalhofinalapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {
    @Autowired
    private JavaMailSender javaMailSender;

    //@Value("${USUARIO_EMAIL}")
    private String email;

    public void enviarEmail(String texto, String para, String assunto){
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom(email);
        msg.setTo(para);
        msg.setSubject(assunto);
        msg.setText("Prezado Cliente, \n" + texto + "\n\nOficina Tech");
        javaMailSender.send(msg);
    }
}
