package com.comert.mhl.mail.mailsender;

import com.comert.mhl.mail.mail.Mail;

public interface MessageProducer {

    void sendMail(Mail mail);

}
