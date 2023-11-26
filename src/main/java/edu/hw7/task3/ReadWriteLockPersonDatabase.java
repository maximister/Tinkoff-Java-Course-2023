package edu.hw7.task3;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockPersonDatabase extends AbstractPersonDatabase {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public void add(Person person) {
        readWriteLock.writeLock().lock();
        super.add(person);
        readWriteLock.writeLock().unlock();
    }

    @Override
    public void delete(int id) {
        readWriteLock.writeLock().lock();
        super.delete(id);
        readWriteLock.writeLock().unlock();
    }

    @Override
    public List<Person> findByName(String name) {
        readWriteLock.readLock().lock();
        List<Person> res = super.findByName(name);
        readWriteLock.readLock().unlock();

        return res;
    }

    @Override
    public List<Person> findByAddress(String address) {
        readWriteLock.readLock().lock();
        List<Person> res = super.findByAddress(address);
        readWriteLock.readLock().unlock();

        return res;
    }

    @Override
    public List<Person> findByPhone(String phone) {
        readWriteLock.readLock().lock();
        List<Person> res = super.findByPhone(phone);
        readWriteLock.readLock().unlock();

        return res;
    }
}
