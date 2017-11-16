package io.gloop.tasks.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import io.gloop.Gloop;
import io.gloop.tasks.R;
import io.gloop.tasks.model.Task;
import io.gloop.permissions.GloopGroup;

public class NewTaskFragment extends Fragment {

    public NewTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        final EditText taskName = view.findViewById(R.id.new_task_et_name);
        final Button next = view.findViewById(R.id.new_task_bt_next);
        final CheckBox shareCheckBox = view.findViewById(R.id.new_task_share_checkBox);
        final Spinner groupsSpinner = view.findViewById(R.id.new_task_group_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, getGroupNames());
        groupsSpinner.setAdapter(adapter);

        // enable/disable spinner on checkbox select
        groupsSpinner.setEnabled(false);
        shareCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                groupsSpinner.setEnabled(true);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create and save new task object
                Task newTask = new Task();

                String description = taskName.getText().toString();
                if (!description.isEmpty())
                    newTask.setDescription(description);

                // set group if selected
                if (shareCheckBox.isChecked()) {
                    if (groupsSpinner.getSelectedItem() != null) {
                        final String selectedGroup = groupsSpinner.getSelectedItem().toString();

                        final GloopGroup group = Gloop.all(GloopGroup.class).where().equalsTo("name", selectedGroup).first();
                        if (group != null)
                            newTask.setUser(group.getObjectId());
                    }
                }

                newTask.save();

                // show tasks list view
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new TasksFragment();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });

        return view;
    }

    private List<String> getGroupNames() {
        final List<GloopGroup> groups = Gloop.all(GloopGroup.class);
        List<String> groupNames = new ArrayList<>();
        for (GloopGroup group : groups) {
            groupNames.add(group.getName());
        }
        return groupNames;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}