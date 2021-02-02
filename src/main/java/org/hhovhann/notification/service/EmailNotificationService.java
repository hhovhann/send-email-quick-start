package org.hhovhann.notification.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.MailTemplate;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.quarkus.qute.api.CheckedTemplate;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hhovhann.notification.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
@Named("EMAIL")
public class EmailNotificationService implements NotificationService {

    @ConfigProperty(name = "quarkus.mail.receiver")
    String mailReceiver;

    @Inject
    Mailer mailer;

    @Inject
    ReactiveMailer reactiveMailer;

    @CheckedTemplate
    static class Templates {
        public static native MailTemplate.MailTemplateInstance notification(User user);
    }

//    @ResourcePath("notificationTemplate.html") not working for some reason when using other path then templates/className
//    @Inject MailTemplate notification;

    /***
     * Send Imperative Email Example
     */
    @Override
    public void sendNotification() {
        mailer.send(Mail.withText(mailReceiver, "A simple email from quarkus", "The Barrier A related to Equipment B has been impaired."));
    }

    /***
     * Send Reactive Email Example
     */
    @Override
    public CompletionStage<Response> sendReactiveNotification() {
        try {
            return reactiveMailer
                    .send(Mail.withText(mailReceiver, "A reactive email from quarkus", "The Barrier A related to Equipment B has been impaired."))
                    .subscribeAsCompletionStage()
                    .thenApply((x -> Response.accepted().build()));
        } catch (Exception ex) {
            System.out.println("{}" + ex.getMessage());
            throw new NotAllowedException("Not allowed to send message reactively ...");
        }
    }

    /***
     * Send Reactive Email with QuTe template Example
     * @return
     */
    @Override
    public CompletionStage<Response> sendReactiveNotificationWithQuTeTemplate() {
        // TODO: Checking that this object we should have as an input
        User user = new User("Barrier", "Barrier Impairment Notification from QuTe template", "hahik2001@outlook.com");
        /* With @CheckedTemplate mechanism */
        return Templates.notification(user)
                .to(user.getEmail())
                .subject(user.getSubject())
                .send()
                .thenApply(x -> Response.accepted().build());


        /* With MailTemplate mechanism
        return notification
                .to(user.getEmail())
                .from("no-reply@gmail.com")
                .subject(user.getSubject())
                .data("User", user)
                .send()
                .thenApply(x -> Response.accepted().build());
        */
    }

    /***
     * Send Imperative Email with inline attachment example
     * @return
     */
    @Override
    public void sendEmailWithAttachment() {
        mailer.send(Mail.withText(mailReceiver, "An email from quarkus with attachment",
                "The Barrier A related to Equipment B has been impaired.")
                .addAttachment("my-file.txt",
                        "content of my file".getBytes(), "text/plain"));
    }
    /***
     * Send Imperative Email with inline html example
     * @return
     */
    @Override
    public void sendEmailWithHtml() {
        String body = "<strong>Hello!</strong>" + "\n" +
                "<p>Here is an image for you: <img src=\"cid:my-image@quarkus.io\"/></p>" +
                "<p>Regards</p>";
        mailer.send(Mail.withHtml(mailReceiver, "An email in HTML", body)
                .addInlineAttachment("quarkus-logo.png",
                        new File("resources/quarkus-logo.png"),
                        "image/png", "<my-image@quarkus.io>"));
    }
}
