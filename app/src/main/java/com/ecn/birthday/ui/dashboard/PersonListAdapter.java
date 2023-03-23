package com.ecn.birthday.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ecn.birthday.R;
import com.ecn.birthday.entity.Person;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.JokeViewHolder> {

    private LiveData<List<Person>> persons;

    public PersonListAdapter(LiveData<List<Person>> persons) {
        this.persons = persons;
    }

    @NonNull
    @NotNull
    @Override
    public JokeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview, parent, false);
        return new JokeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull JokeViewHolder holder, int position) {
        holder.bind(persons.getValue().get(position).toString());
    }

    @Override
    public int getItemCount() {
        return persons.getValue().size();
    }

    static class JokeViewHolder extends RecyclerView.ViewHolder {

        private final TextView jokeTextView;

        public JokeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            jokeTextView = itemView.findViewById(R.id.joke_text);
        }

        public void bind(String joke) {
            jokeTextView.setText(joke);
        }
    }
}

