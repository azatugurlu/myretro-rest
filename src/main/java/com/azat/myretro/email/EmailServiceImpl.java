package com.azat.myretro.email;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import com.azat.myretro.model.Response;

@Component
@PropertySource("classpath:emailconfig.properties")
public class EmailServiceImpl implements EmailService {

	@Autowired
	SpringMailConfig config;

	@Value("${mail.from}")
	private String from;

	@Override
	public Response send(String to, String subject, String template, Map<String, Object> context) {
		final Context ctx = new Context();
		context.keySet().forEach(k -> {
			ctx.setVariable(k, context.get(k));
		});
		try {
			final String htmlContent = config.emailTemplateEngine().process(template, ctx);
			final MimeMessage mimeMessage = config.mailSender().createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
			message.setFrom(from);
			message.setTo(to);
			message.setSubject(subject);
			message.setText("text/html; charset=utf-8", htmlContent);
			config.mailSender().send(mimeMessage);
		} catch (MailException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return Response.create().message("info", "Email " + subject + " sent.");
	}

}
