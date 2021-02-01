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
//
//    @ResourcePath("EmailNotificationService/notification.html")
//    MailTemplate notificationTemplate;

    @Override
    public CompletionStage<Response> sendWithTemplateSimpleNotification() {
        // prepare user to send notification
        User user = new User("Barrier", "Barrier Impairment Notification from QuTe template", "hahik2001@outlook.com");
        /* With @CheckedTemplate mechanism*/
        return Templates.notification(user)
                .to(user.getEmail())
                .subject(user.getSubject())
                .send()
                .thenApply(x -> Response.accepted().build());


        /* With MailTemplate mechanism
        return notificationTemplate

                .to(user.getEmail())
                .from("no-reply@gmail.com")
                .subject(user.getSubject())
                .data("User", user)
                .send()
                .thenApply(x -> Response.accepted().build());
        */
    }

    @Override
    public void sendNotification() {
        // Imperative API:
        mailer.send(Mail.withText(mailReceiver, "A simple email from quarkus", "The Barrier A related to Equipment B has been impaired."));
        System.out.println("Imperatively mail was sent");
        // Reactive API:
        Uni<Void> stage = reactiveMailer.send(Mail.withText(mailReceiver, "A reactive email from quarkus", "The Barrier A related to Equipment B has been impaired."));
        try {
            System.out.println("sendNotification logger: {}" + stage.subscribeAsCompletionStage().get());
            System.out.println("Reactively mail was sent");

        } catch (Exception ex) {
            System.out.println("{}" + ex.getMessage());
        }
    }

    @Override
    public void sendSimpleNotification() {
        mailer.send(Mail.withText(mailReceiver, "A simple email from quarkus", "The Barrier A related to Equipment B has been impaired."));
    }

    @Override
    public CompletionStage<Response> sendASimpleEmailAsync() {
        return reactiveMailer.send(
                Mail.withText(mailReceiver, "A reactive email from quarkus", "The Barrier A related to Equipment B has been impaired."))
                .subscribeAsCompletionStage()
                .thenApply(x -> Response.accepted().build());
    }

    @Override
    public void sendEmailWithAttachment() {
        mailer.send(Mail.withText(mailReceiver, "An email from quarkus with attachment",
                "The Barrier A related to Equipment B has been impaired.")
                .addAttachment("my-file.txt",
                        "content of my file".getBytes(), "text/plain"));
    }

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
