package org.hhovhann.notification.openapi;

import io.quarkus.runtime.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@OpenAPIDefinition(
        tags = {@Tag(name = "email", description = "Email Notification Operations."), @Tag(name = "sms", description = "Sms Notification Operations.")},
        info = @Info(title = "DRP Example API", version = "1.0.0",
                contact = @Contact(name = "Notification API Example", url = "https://notification.com/", email = "notification@gmail.com"),
                license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")),
        servers = @Server(url = "/", description = "Localhost")
)
public class NotificationApiApplication extends Application {
    @Override
    protected void doStart(String[] args) {
        System.out.println("System Start with Arguments ..." + args);
    }

    @Override
    protected void doStop() {
        System.out.println("System Stop ...");

    }

    @Override
    public String getName() {
        return "Notification Api Example Application";
    }
}
