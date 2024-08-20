package com.chargebee.creditmanagement.datastores;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class LockableConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {

    private final ConcurrentHashMap<K, ReentrantReadWriteLock> locks = new ConcurrentHashMap<>();

    public V lockedPut(K key, V value) {
        Lock lock = this.getWriteLock(key);
        lock.lock();
        try {
            return super.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    public V lockedGet(K key) {
        Lock lock = this.getReadLock(key);
        lock.lock();
        try {
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    public V getForUpdate(K key) {
        Lock lock = this.getWriteLock(key);
        lock.lock();
        try {
            return super.get(key);
        } catch (Exception e) {
            lock.unlock();
            throw e;
        }
    }

    public void insertLockedValue(K key, V newValue) {
        super.put(key, newValue);
        releaseLockedKey(key);
    }

    public void updateLockedValue(K key, V newValue) {
        super.put(key, newValue);
        releaseLockedKey(key);
    }

    public void releaseLockedKey(K key) {
        ReentrantReadWriteLock lock = getLock(key);
        if (lock.isWriteLockedByCurrentThread()) {
            lock.writeLock().unlock();
        } else {
            throw new IllegalStateException("Key " + key + " is not locked for update");
        }
    }

    public Lock getReadLock(K key) {
        return getLock(key).readLock();
    }

    public Lock getWriteLock(K key) {
        return getLock(key).writeLock();
    }

    private ReentrantReadWriteLock getLock(K key) {
        return locks.computeIfAbsent(key, k -> new ReentrantReadWriteLock());
    }
}
