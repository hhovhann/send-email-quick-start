package org.hhovhann.notification.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("SMS")
public class SmsNotificationService implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("SMS NOTIFICATION SENT AN SMS TO THE NUMBER 897654231");
    }

}
