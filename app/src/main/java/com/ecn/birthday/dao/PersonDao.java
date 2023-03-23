package com.ecn.birthday.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.ecn.birthday.entity.Person;

import java.util.List;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM person")
    List<Person> getAll();

    @Query("SELECT * FROM person WHERE id IN (:ids)")
    List<Person> loadAllByIds(int[] ids);

    @Query("SELECT * FROM person WHERE first_name LIKE :first AND " +
           "last_name LIKE :last LIMIT 1")
    Person findByName(String first, String last);

    @Insert
    void insertAll(Person... persons);

    @Delete
    void delete(Person person);
}
