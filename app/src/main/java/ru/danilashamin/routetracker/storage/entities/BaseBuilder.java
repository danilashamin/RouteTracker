package ru.danilashamin.routetracker.storage.entities;

abstract class BaseBuilder<E, T extends BaseBuilder<E, T>> {

    private static final int AUTO_INCREMENT_STUB = 0;

    long id = AUTO_INCREMENT_STUB;

    public final T setId(long id) {
        this.id = id;
        return self();
    }

    abstract T self();

    public abstract E build();
}
