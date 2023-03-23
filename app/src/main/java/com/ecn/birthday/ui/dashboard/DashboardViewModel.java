package com.ecn.birthday.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ecn.birthday.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<Person>> mPersons;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
        mPersons = new MutableLiveData<>();
        mPersons.setValue(new ArrayList<>());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Person>> getPersons() {
        return mPersons;
    }

    public void setPersons(List<Person> persons) {
        mPersons.setValue(persons);
    }

    public void addPerson(Person person) {
        List<Person> persons = mPersons.getValue();
        persons.add(person);
        mPersons.setValue(persons);
    }
}