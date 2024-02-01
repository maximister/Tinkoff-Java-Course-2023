package edu.hw7.task3;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DatabasesTest {
    @Test @DisplayName("testing sync database") public void syncDatabase_shouldPassDatabaseTest() {
        PersonDatabase database = new SynchronizedDatabase();
        testDatabase(database);
    }

    @Test @DisplayName("testing lock database") public void lockDatabase_shouldPassDatabaseTest() {
        PersonDatabase database = new ReadWriteLockPersonDatabase();
        testDatabase(database);
    }

    private static void testDatabase(PersonDatabase personDatabase) {
        Future<List<Person>> futureByPhone;
        Future<List<Person>> futureByName;
        Future<List<Person>> futureByAddress;
        try (ExecutorService mockedExecutorService = Mockito.spy(Executors.newFixedThreadPool(4))) {
            mockedExecutorService.execute(() -> getPersonList().forEach(personDatabase::add));

            Mockito.verify(mockedExecutorService, Mockito.timeout(500).times(1))
                .execute(Mockito.any());

            mockedExecutorService.execute(() -> personDatabase.delete(3));

            Thread.sleep(100);

            futureByPhone = mockedExecutorService.submit(() -> personDatabase.findByPhone("1234567"));
            futureByName = mockedExecutorService.submit(() -> personDatabase.findByName("Vasya"));
            futureByAddress = mockedExecutorService.submit(() -> personDatabase.findByAddress("Moscow"));

            mockedExecutorService.shutdown();

            assertAll(
                () -> assertThat(futureByPhone.get()).isEqualTo(getPersonList().stream()
                    .filter(person -> person != null && person.phoneNumber().equals("1234567") && person.id() != 3)
                    .toList()),

                () -> assertThat(futureByName.get()).isEqualTo(getPersonList().stream()
                    .filter(person -> person != null && person.name().equals("Vasya") && person.id() != 3).toList()),

                () -> assertThat(futureByAddress.get()).isEqualTo(getPersonList().stream()
                    .filter(person -> person != null && person.address().equals("Moscow") && person.id() != 3).toList())
            );

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Person> getPersonList() {
        return List.of(
            Person.builder().id(1).name("Vasya").address("Moscow").phoneNumber("1234567").build(),
            Person.builder().id(2).name("Sergay").address("Rostov-papa").phoneNumber("1234567").build(),
            Person.builder().id(3).name("Vasya").address("Rostov").phoneNumber("7654321").build(),
            Person.builder().id(4).name("Gena").address("Saratov").phoneNumber("12345").build()
        );
    }
}
