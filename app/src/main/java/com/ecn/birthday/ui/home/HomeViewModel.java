package com.ecn.birthday.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Date> mDate;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        mDate = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setBirthday(Date date) {
        mDate.setValue(date);
    }

    public LiveData<Date> getBirthday() {
        return mDate;
    }
}