package com.ecn.birthday.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ecn.birthday.Utils;
import com.ecn.birthday.dao.PersonDao;
import com.ecn.birthday.databinding.FragmentDashboardBinding;
import com.ecn.birthday.db.AppDatabase;
import com.ecn.birthday.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        this.dashboardViewModel = dashboardViewModel;

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final PersonListAdapter adapter = new PersonListAdapter(dashboardViewModel.getPersons());
        binding.recyclerView.setAdapter(adapter);
        dashboardViewModel.getPersons().observe(getViewLifecycleOwner(), persons -> {
            adapter.notifyDataSetChanged();
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("DashboardFragment", "onResume");
        fetchPersonData();
    }

    private void fetchPersonData() {
        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
                AppDatabase.class, Utils.DATABASE_NAME).build();

        PersonDao personDao = db.personDao();
        Utils.THREAD_POOL_EXECUTOR.execute(() -> {
            List<Person> persons = personDao.getAll();
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(DashboardFragment.this.requireContext(), persons.size() + "Persons loaded from database!", Toast.LENGTH_SHORT).show();
                dashboardViewModel.setPersons(persons);
            });
            Log.d("DashboardFragment", "persons: " + persons);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        Log.d("DashboardFragment", "onDestroyView");
    }
}