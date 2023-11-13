package edu.hw3.task5;

import org.jetbrains.annotations.NotNull;

public record Contact(String name, String surname) implements Comparable<Contact> {
    public static Contact getContactFromString(String contact) {
        if (contact == null) {
            throw new NullPointerException("Contact could not be null!");
        }
        String[] data = contact.split(" ");
        if (data.length < 1 || data[0].isEmpty()) {
            throw new IllegalArgumentException("your contact must not be empty!");
        } else if (data.length > 1) {
            return new Contact(data[0], data[1]);
        } else {
            return new Contact(data[0], "");
        }
    }

    @Override
    public int compareTo(@NotNull Contact o) {
        String arg1 = this.surname.isEmpty() ? this.name : this.surname;
        String arg2 = o.surname.isEmpty() ? o.name : o.surname;

        return arg1.compareTo(arg2);
    }
}
