package io.gloop.demo.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.gloop.demo.app.R;
import io.gloop.demo.app.constants.Constants;
import io.gloop.demo.app.model.Task;

public class TaskDetailFragment extends Fragment {

    private Task task;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = (Task) getArguments().getSerializable(Constants.BUNDLE_DETAIL_GROUP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        TextView nameTextView = (TextView) view.findViewById(R.id.task_detail_name);
        nameTextView.setText(task.getDescription());

        return view;
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