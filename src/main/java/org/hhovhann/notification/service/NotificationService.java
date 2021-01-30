package org.hhovhann.notification.service;

import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

public interface NotificationService {
    void sendNotification();

    default void sendSimpleNotification() {
        System.out.println("sendSimpleNotification");
    }

    default CompletionStage<Response> sendASimpleEmailAsync() {
        System.out.println("sendSimpleNotification");
        return null;
    }

    default void sendEmailWithAttachment() {

    }

    default void sendEmailWithHtml(){

    }
}
