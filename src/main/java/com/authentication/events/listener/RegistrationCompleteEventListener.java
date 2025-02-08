//package com.authentication.events.listener;
//
//import com.authentication.events.RegistrationCompleteEvent;
//import com.authentication.model.User;
//import com.authentication.service.AuthenticationService;
//import com.authentication.service.EmailService;
//
//import java.util.UUID;
//
//public class RegistrationCompleteEventListener {
//    private final AuthenticationService authenticationService;
//    private final EmailService emailService;
//    @Override
//    public void onApplicationEvent(RegistrationCompleteEvent event) {
//
//        User user = event.getUser();
//        String verificationToken = UUID.randomUUID().toString();
//        authenticationService.createVerificationToken(user, verificationToken);
//        String appUrl = event.getAppUrl()+"/api/v1/auth/verify-account?token="+verificationToken;
////        emailService.sendSimpleMailMessage(user.getName(), user.getEmail(), appUrl);
////        emailService.sendMimeMessageWithAttachment(user.getName(), user.getEmail(), appUrl);
//        emailService.sendHtmlEmail(user.getFirstName() + " "+user.getLastName(), user.getEmail(), appUrl);
//        log.info("Registration complete event for user: "+appUrl);
//    }
//}
