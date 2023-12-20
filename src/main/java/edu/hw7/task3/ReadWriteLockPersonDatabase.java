package edu.hw7.task3;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockPersonDatabase extends AbstractPersonDatabase {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public void add(Person person) {
        try {
            readWriteLock.writeLock().lock();
            super.add(person);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        try {
            readWriteLock.writeLock().lock();
            super.delete(id);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        try {
            readWriteLock.readLock().lock();
            return super.findByName(name);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        try {
            readWriteLock.readLock().lock();
            return super.findByAddress(address);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        try {
            readWriteLock.readLock().lock();
            return super.findByPhone(phone);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
