package edu.hw7.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstractPersonDatabase implements PersonDatabase {
    private final Map<Integer, Person> idMap;
    private final Map<String, List<Person>> cachedNames;
    private final Map<String, List<Person>> cachedAddresses;
    private final Map<String, List<Person>> cachedNumbers;
    private static final Logger LOGGER = LogManager.getLogger();

    public AbstractPersonDatabase() {
        idMap = new HashMap<>();
        cachedNames = new HashMap<>();
        cachedAddresses = new HashMap<>();
        cachedNumbers = new HashMap<>();

        LOGGER.info("Database was created");
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    @Override
    public void add(Person person) {
        idMap.put(person.id(), person);
        cachedNames.computeIfAbsent(person.name(), name -> new ArrayList<>()).add(person);
        cachedAddresses.computeIfAbsent(person.address(), address -> new ArrayList<>()).add(person);
        cachedNumbers.computeIfAbsent(person.phoneNumber(), number -> new ArrayList<>()).add(person);

        LOGGER.info("Person with id " + person.id() + " was added to DB");
    }

    @Override
    public void delete(int id) {
        Person deletedPerson = idMap.get(id);
        if (deletedPerson == null) {
            LOGGER.info("Person with id " + id + " could not be deleted from DB");
            return;
        }
        idMap.remove(id);
        cachedNames.get(deletedPerson.name()).remove(deletedPerson);
        cachedAddresses.get(deletedPerson.address()).remove(deletedPerson);
        cachedNumbers.get(deletedPerson.phoneNumber()).remove(deletedPerson);

        LOGGER.info("Person with id " + id + " was deleted from DB");
    }

    @Override
    public List<Person> findByName(String name) {
        LOGGER.info("Trying to find by name  " + name);
        if (!cachedNames.containsKey(name)) {
            return null;
        }

        return cachedNames.get(name);

    }

    @Override
    public List<Person> findByAddress(String address) {
        LOGGER.info("Trying to find by address  " + address);

        if (!cachedAddresses.containsKey(address)) {
            return null;
        }

        return cachedAddresses.get(address);
    }

    @Override
    public List<Person> findByPhone(String phone) {
        LOGGER.info("Trying to find by phone  " + phone);

        if (!cachedNumbers.containsKey(phone)) {
            return null;
        }

        return cachedNumbers.get(phone);
    }
}
