package edu.hw3.task5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ContactParser {
    private ContactParser() {
    }

    public static ArrayList<Contact> parseContacts(List<String> contacts, String type) {
        if (contacts == null || contacts.isEmpty()) {
            return new ArrayList<>(Collections.emptyList());
        }

        ArrayList<Contact> contactList =
            contacts.stream().map(Contact::getContactFromString).sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        if (type.equals("ASC")) {
            return contactList;
        } else if (type.equals("DESC")) {
            Collections.reverse(contactList);
            return contactList;
        } else {
            throw new IllegalArgumentException("unsupported type of sorting!");
        }
    }
}
