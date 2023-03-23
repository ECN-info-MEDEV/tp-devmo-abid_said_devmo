package com.ecn.birthday.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.ecn.birthday.dao.PersonDao;
import com.ecn.birthday.entity.Person;

@Database(entities = {Person.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
}
