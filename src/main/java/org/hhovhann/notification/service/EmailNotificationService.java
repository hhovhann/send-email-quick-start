package org.hhovhann.notification.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.mail.MailClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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
    MailClient client; // vertx solution ...

    @Inject
    Mailer mailer;

    @Inject
    ReactiveMailer reactiveMailer;

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
