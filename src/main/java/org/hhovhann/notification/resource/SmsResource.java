package org.hhovhann.notification.resource;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hhovhann.notification.service.NotificationService;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/sms/api")
@Tag(name="sms")
public class SmsResource {

    @Named("SMS")
    NotificationService notificationService;

    @GET
    @Path("/send-sms")
    public Response sendSms() {
        notificationService.sendNotification();
        return Response.accepted().build();
    }
}
