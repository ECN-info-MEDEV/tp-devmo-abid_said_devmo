package com.ecn.birthday.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.room.Room;
import com.ecn.birthday.R;
import com.ecn.birthday.Utils;
import com.ecn.birthday.dao.PersonDao;
import com.ecn.birthday.db.AppDatabase;
import com.ecn.birthday.entity.Person;
import com.ecn.birthday.ui.list.placeholder.PlaceholderContent;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class PersonListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PersonListFragment newInstance(int columnCount) {
        PersonListFragment fragment = new PersonListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("PersonListFragment", "onResume");
        fetchPersonData();
    }

    private MyPersonRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyPersonRecyclerViewAdapter(PlaceholderContent.ITEMS);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void fetchPersonData() {
        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
                AppDatabase.class, Utils.DATABASE_NAME).build();

        PersonDao personDao = db.personDao();
        Utils.THREAD_POOL_EXECUTOR.execute(() -> {
            List<Person> persons = personDao.getAll();
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(PersonListFragment.this.requireContext(), persons.size() + "Persons loaded from database!", Toast.LENGTH_SHORT).show();
                PlaceholderContent.updateItems(persons);
                adapter.notifyDataSetChanged();
            });
            Log.d("PersonListFragment", "persons: " + persons);
        });
    }
}