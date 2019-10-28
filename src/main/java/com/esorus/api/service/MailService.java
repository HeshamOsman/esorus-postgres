package com.esorus.api.service;

import com.esorus.api.domain.Authority;
import com.esorus.api.domain.Configs;
import com.esorus.api.domain.RequestForSupplier;
import com.esorus.api.domain.User;
import com.esorus.api.repository.ConfigsRepository;
import com.esorus.api.repository.UserRepository;
import com.esorus.api.security.AuthoritiesConstants;

import io.github.jhipster.config.JHipsterProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;
    private final UserRepository userRepository;
    
    private final ConfigsRepository configsRepository;
    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine,UserRepository userRepository,
            ConfigsRepository configsRepository) {
    	this.userRepository = userRepository;
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.configsRepository = configsRepository;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        }  catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }
    
    @Async
    public void sendBuyerRequestSubmittedEmail(RequestForSupplier requestForSupplier) {
        log.debug("Sending creation email to '{}'", requestForSupplier.getUser().getEmail());
        byte[] fileBytes = null;
		try {
			Path path = Paths.get("src/main/resources/templates/images/esorus-black-logo.png");
			fileBytes = Files.readAllBytes(path);
		} catch (IOException e) {
		}
        sendEmailFromTemplate(requestForSupplier.getUser(), "mail/requestForSupplierConfirmation", "email.buyer.request.title","esorus-black",new ByteArrayResource(fileBytes));
        sendEmailFromTemplate(requestForSupplier,configsRepository.findOneByKey("admin_email").get().getValue(), "mail/requestForSuppliarSubmitted", "email.admin.buyer.request.title","esorus-black",new ByteArrayResource(fileBytes));
    }
    
    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey,String name,InputStreamSource imageSource) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable("imageResourceName",name);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            message.setTo(user.getEmail());
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, true);
            message.addInline(name, imageSource, "image/png");
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", user.getEmail());
        }  catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", user.getEmail(), e);
        }
    }
    
    @Async
    public void sendEmailFromTemplate(RequestForSupplier requestForSupplier, String to, String templateName, String titleKey,String name,InputStreamSource imageSource) {
        Locale locale = Locale.forLanguageTag(requestForSupplier.getUser().getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, requestForSupplier.getUser());
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable("imageResourceName",name);
        context.setVariable("rfs",requestForSupplier);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, true);
            message.addInline(name, imageSource, "image/png");
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", requestForSupplier.getUser().getEmail());
        }  catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", requestForSupplier.getUser().getEmail(), e);
        }
    }
    
    @Async
    public void sendEmailFromTemplate(User user, String to, String templateName, String titleKey,String name,InputStreamSource imageSource) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable("imageResourceName",name);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, true);
            message.addInline(name, imageSource, "image/png");
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", user.getEmail());
        }  catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", user.getEmail(), e);
        }
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        
        byte[] fileBytes = null;
		try {
			Path path = Paths.get("src/main/resources/templates/images/esorus-black-logo.png");
			fileBytes = Files.readAllBytes(path);
		} catch (IOException e) {
		}
		
        for(Authority auth:user.getAuthorities()) {
        	if(auth.getName().equals(AuthoritiesConstants.PROFESSIONAL_BUYER)) {
        		sendEmailFromTemplate(user, "mail/activationEmailForProffesionalBuyer", "email.buy.activation.title","esorus-black",new ByteArrayResource(fileBytes));
        		return;
        	}
        	if(auth.getName().equals(AuthoritiesConstants.SUPPLIER)) {
        		sendEmailFromTemplate(user, "mail/activationEmailForSupplaier", "email.sup.activation.title","esorus-black",new ByteArrayResource(fileBytes));
        		sendEmailFromTemplate(user,configsRepository.findOneByKey("admin_email").get().getValue(), "mail/supplierRegistered", "email.admin.sup.activation.title","esorus-black",new ByteArrayResource(fileBytes));
        		return;
        	}
        }
        
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }
    
    @Async
    public void sendAfterActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        user = userRepository.findOneWithAuthoritiesByEmailIgnoreCase(user.getEmail()).get();
        byte[] fileBytes = null;
		try {
			Path path = Paths.get("src/main/resources/templates/images/esorus-black-logo.png");
			fileBytes = Files.readAllBytes(path);
		} catch (IOException e) {
		}
		
        for(Authority auth:user.getAuthorities()) {
        	if(auth.getName().equals(AuthoritiesConstants.PROFESSIONAL_BUYER)) {
        		sendEmailFromTemplate(user, "mail/accountActivatedSuccessfullyBuyer", "email.buy.after.active.title","esorus-black",new ByteArrayResource(fileBytes));
        		return;
        	}
        	if(auth.getName().equals(AuthoritiesConstants.SUPPLIER)) {
        		sendEmailFromTemplate(user, "mail/accountActivatedSuccessfullySupplaier", "email.sup.after,active.title","esorus-black",new ByteArrayResource(fileBytes));
        		return;
        	}
        }
        
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }
}
