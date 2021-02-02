package org.hhovhann.notification.resource;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hhovhann.notification.service.NotificationService;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

@Path("/sms/api")
@Tag(name = "sms")
public class SmsResource {

    @Named("SMS")
    NotificationService notificationService;

    @GET
    @Path("/send-sms")
    public CompletionStage<Response> sendSms() {
        notificationService.sendNotification();
        return null;
    }
}
