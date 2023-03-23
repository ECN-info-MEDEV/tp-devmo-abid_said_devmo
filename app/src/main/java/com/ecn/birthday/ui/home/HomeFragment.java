package com.ecn.birthday.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import com.ecn.birthday.Utils;
import com.ecn.birthday.dao.PersonDao;
import com.ecn.birthday.databinding.FragmentHomeBinding;
import com.ecn.birthday.db.AppDatabase;
import com.ecn.birthday.entity.Person;
import com.ecn.birthday.ui.input.DatePickerFragment;

import java.util.Date;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        final EditText editFirstName = binding.firstName;
        final EditText editLastName = binding.lastName;

        final Button birthdayButton = binding.birthday;
        birthdayButton.setOnClickListener(this::showDatePickerDialog);

        // We set the listener on the child fragmentManager
        getChildFragmentManager().setFragmentResultListener(DatePickerFragment.REQUEST_DATE_KEY, this, (requestKey, result) -> {
            Date date = (Date) result.getSerializable("date");
            homeViewModel.setBirthday(date);
            textView.setText(Utils.formatDisplayDate(date));
            birthdayButton.setText(Utils.formatDisplayDate(date));
            birthdayButton.setEnabled(true);
        });

        final Button buttonSave = binding.save;
        buttonSave.setOnClickListener(v -> {
            String firstName = editFirstName.getText().toString();
            String lastName = editLastName.getText().toString();
            Date birthdate = homeViewModel.getBirthday().getValue();
            if (firstName.isEmpty() || lastName.isEmpty() || birthdate == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                String missingFields = "";
                if (firstName.isEmpty()) {
                    missingFields += "- First name -";
                }
                if (lastName.isEmpty()) {
                    missingFields += "- Last name -";
                }
                if (birthdate == null) {
                    missingFields += "- Birthdate -";
                }
                builder.setMessage("Please fill the following fields: " + missingFields)
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            } else {
                Utils.THREAD_POOL_EXECUTOR.execute(() -> {
                    Person person = new Person();
                    person.setFirstName(firstName);
                    person.setLastName(lastName);
                    // fill birthdate in format yyyy-MM-dd
                    person.setBirthDate(Utils.formatDatabaseDate(birthdate));
                    AppDatabase db = Room.databaseBuilder(requireActivity().getApplicationContext(),
                            AppDatabase.class, Utils.DATABASE_NAME).build();

                    PersonDao personDao = db.personDao();
                    personDao.insertAll(person);
//                        show toast message
                    // run back in the main thread
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "Person saved", Toast.LENGTH_SHORT).show();
                        // clear fields
                        editFirstName.setText("");
                        editLastName.setText("");
                        birthdayButton.setText("Select birthday");
                        homeViewModel.setBirthday(null);
                    });
                });
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showDatePickerDialog(View v) {
        // prevent multiple clicks
        v.setEnabled(false);
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getChildFragmentManager(), "datePicker");
    }
}