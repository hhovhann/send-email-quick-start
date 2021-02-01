package org.hhovhann.notification.entity;

public class User {
    private String name;
    private String subject;
    private String email;

    public User(String name, String subject, String email) {
        this.name = name;
        this.subject = subject;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
