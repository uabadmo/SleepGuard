package com.mm90849491.sleepguard;

/**
 */
public abstract class Person {
    private String firstName;
    private String lastName;
    private String middleName;
    private String prefix;
    private String phoneNumber;
    private String emailAddress;

    protected Person() {
        this.setFirstName("John");
        this.setLastName("Smith");
        this.setPrefix("Mr.");
    }

    protected Person(String firstName, String lastName, String middleName, String prefix, String phoneNumber, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.prefix = prefix;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
