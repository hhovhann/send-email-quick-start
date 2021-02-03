package org.hhovhann.notification.resource;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class MailingResourceTest {

    @ConfigProperty(name = "quarkus.mail.receiver")
    String mailReceiver;

    @Inject
    MockMailbox mailbox;

    @BeforeEach
    void init() {
        mailbox.clear();
    }

    @Test

    @DisplayName("Send async email notification")
    void testSendSyncNotificationTextMail() throws IOException {
        // call a REST endpoint that sends email
        given()
                .when()
                .get("/mail/api/send-sync-email")
                .then()
                .statusCode(202)
                .body(is("Accepted"));

        // verify that it was sent
        List<Mail> sent = mailbox.getMessagesSentTo(mailReceiver);
        assertThat(sent, hasSize(1));
        Mail actual = sent.get(0);
        // then actual mail information should have
        Assertions.assertAll("Should have email correct information",
                () -> assertThat(actual.getSubject(), equalTo("A simple email from quarkus")),
                () -> assertThat(actual.getText(), equalTo("The Barrier A related to Equipment B has been impaired.")),
                () -> assertThat(mailbox.getTotalMessagesSent(), equalTo(1))
        );
    }

    @Test
    @DisplayName("Send async email notification")
    void testSendASyncNotificationTextMail() throws IOException {
        // call a REST endpoint that sends email
        given()
                .when()
                .get("/mail/api/send-async-email")
                .then()
                .statusCode(202)
                .body(is("Accepted"));

        // verify that it was sent
        List<Mail> sent = mailbox.getMessagesSentTo(mailReceiver);
        assertThat(sent, hasSize(1));
        Mail actual = sent.get(0);
        // then actual mail information should have
        Assertions.assertAll("Should have email correct information",
                () -> assertThat(actual.getSubject(), equalTo("A reactive email from quarkus")),
                () -> assertThat(actual.getText(), equalTo("The Barrier A related to Equipment B has been impaired.")),
                () -> assertThat(mailbox.getTotalMessagesSent(), equalTo(1))
        );
    }
}