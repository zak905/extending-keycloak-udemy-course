package com.gwidgets;

import org.keycloak.email.EmailException;
import org.keycloak.email.EmailSenderProvider;

import java.util.Map;
import java.util.logging.Logger;

public class VoidEmailSenderProvider implements EmailSenderProvider {

    private static Logger logger = Logger.getLogger("com.gwidgets.EmailSenderProvider");

    @Override
    public void send(Map<String, String> config, String address, String subject, String textBody, String htmlBody) throws EmailException {
        logger.info("send email invoked");
    }

    @Override
    public void close() {

    }
}
