package com.example.emailservice.controller;

import com.example.emailservice.service.MailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailControllerTest {

    @InjectMocks
    private MailController mailController;

    @Mock
    private MailService mailService;

    @Test
    void sendEmail() {
        String to = "to@example.com";
        String subject = "Subject";
        String text = "Text";

        mailController.sendEmail(to, subject, text);

        verify(mailService).sendEmail(to, subject, text);
    }
}
