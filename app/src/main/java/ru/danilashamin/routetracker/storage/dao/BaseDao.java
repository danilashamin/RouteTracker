package ru.danilashamin.routetracker.storage.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

interface BaseDao<T> {

    @Insert
    Single<Long> add(T t);

    @Insert
    Single<List<Long>> add(List<T> items);

    @Update
    Single<Integer> update(T t);

    @Delete
    Single<Integer> delete(T t);

    @Delete
    Single<Integer> delete(List<T> items);
}
