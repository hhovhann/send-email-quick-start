package org.hhovhann.notification.extensions;

import io.quarkus.qute.TemplateExtension;

@TemplateExtension
public class NotificationFormatExtensions {

    public String generateNotificationBody(){
        return "Barrier A related to Equipment B has been impaired.";
    }
}