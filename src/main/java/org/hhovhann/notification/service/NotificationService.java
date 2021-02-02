package org.hhovhann.notification.service;

import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

public interface NotificationService {
    void sendNotification();

    default CompletionStage<Response> sendReactiveNotification() {
        System.out.println("sendReactiveNotification");
        return  null;
    }

    default CompletionStage<Response> sendReactiveNotificationWithQuTeTemplate() {
        System.out.println("sendWithTemplateSimpleNotification");
        return null;
    }

    default void sendEmailWithAttachment() {

    }

    default void sendEmailWithHtml() {

    }
}
