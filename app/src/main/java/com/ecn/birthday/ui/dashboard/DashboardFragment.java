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


        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        final Button button = binding.button;
        // log message via logcat
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DashboardFragment", "onClick");

//                String url = "https://api.chucknorris.io/jokes/random";
//                RequestQueue queue = Volley.newRequestQueue(DashboardFragment.this.requireContext());
//
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                        response -> {
//                            // Extract the joke from the response
//                            String joke = response.optString("value");
//
//                            // Update the text field with the new joke
//                            textView.setText(joke);
//                        },
//                        error -> {
//                            // Handle error
//                            Toast.makeText(DashboardFragment.this.requireContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        });
//
//                // Add the request to the RequestQueue.
//                queue.add(jsonObjectRequest);
//            }
            }
        });


        final PersonListAdapter adapter = new PersonListAdapter(dashboardViewModel.getPersons());
        binding.recyclerView.setAdapter(adapter);

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