package org.hhovhann.notification.resource;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hhovhann.notification.service.NotificationService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

@Path("/mail/api")
@Tag(name="email")
public class MailingResource {

    @Inject
    @Named("EMAIL")
    NotificationService notificationService;

    @GET
    @Path("/send-simple-email-with-qute-template")
    public CompletionStage<Response> send() {
        return notificationService.sendReactiveNotificationWithQuTeTemplate();
    }

    @GET
    @Path("/send-sync-email")
    public Response sendImperativeNotification() {
        notificationService.sendNotification();
        return Response.accepted("Accepted").build();
    }

    @GET
    @Path("/send-async-email")
    public CompletionStage<Response> sendASimpleEmailAsync() {
        return notificationService.sendReactiveNotification();
    }

    @GET
    @Path("/send-email-with-attachment")
    public Response sendEmailWithAttachment() {
        notificationService.sendEmailWithAttachment();
        return Response.accepted().build();
    }

    @GET
    @Path("/send-email-with-html")
    public Response sendingHTML() {
        notificationService.sendEmailWithHtml();
        return Response.accepted().build();
    }

    @GET
    @Path("/send-email-from-notification-service")
    public Response sendEmailNotification() {
        notificationService.sendNotification();
        return Response.accepted().build();
    }
}
