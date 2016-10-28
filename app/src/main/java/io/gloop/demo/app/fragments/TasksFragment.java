package io.gloop.demo.app.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.List;

import io.gloop.Gloop;
import io.gloop.GloopList;
import io.gloop.demo.app.R;
import io.gloop.demo.app.adapters.TasksAdapter;
import io.gloop.demo.app.model.Task;
import io.gloop.permissions.GloopGroup;

public class TasksFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GloopList<Task> tasks;
    private TasksAdapter mAdapter;

    private ArrayAdapter<String> userAdapter;

    private List<String> userIds = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.task_list);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });

        mRecyclerView.addOnItemTouchListener(
                new SwipeableRecyclerViewTouchListener(mRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions)
                                    tasks.remove(position);
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions)
                                    tasks.remove(position);
                            }
                        }));



        return view;
    }

    private List<String> getGroupNames() {
        final List<GloopGroup> groups = Gloop.all(GloopGroup.class);
        List<String> groupNames = new ArrayList<>();
        groupNames.add("Private");
        for (GloopGroup group : groups) {
            groupNames.add(group.getName());
        }
        groupNames.add("New Group");
        return groupNames;
    }

    public void showAddTaskDialog() {
        final Activity activity = getActivity();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_new_task, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
//
//        final Spinner spinner = (Spinner) dialogView.findViewById(R.id.group_spinner);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, getGroupNames());
//        spinner.setAdapter(adapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                final String selectedGroup = spinner.getSelectedItem().toString();
//                if (selectedGroup.equals("New Group")) {
//
//                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
//                    LayoutInflater inflater = activity.getLayoutInflater();
//                    final View dialogView = inflater.inflate(R.layout.new_group_dialog, null);
//
//                    final EditText groupName = (EditText) dialogView.findViewById(R.id.group_name);
//                    ListView userList = (ListView) dialogView.findViewById(R.id.user_list);
//                    userAdapter = new ArrayAdapter<String>(activity,
//                            android.R.layout.simple_list_item_1, userIds);
//                    userList.setAdapter(userAdapter);
//
//                    Button addUserButton = (Button) dialogView.findViewById(R.id.button_add_user_to_group);
//                    addUserButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            try {
//                                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
//                                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
//
//                                startActivityForResult(intent, 0);
//
//                            } catch (Exception e) {
//                                Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
//                                Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
//                                startActivity(marketIntent);
//
//                            }
//                        }
//                    });
//
//                    dialogBuilder.setView(dialogView);
//                    dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int whichButton) {
//                                    final String name = groupName.getText().toString();
//                                    if (!name.isEmpty()) {
//                                        GloopGroup group = new GloopGroup();
//                                        group.setName(name);
//
//                                        // TODO impl
//                                        // this will produce a problem
//                                        // create new user will replace the one on the server.
//                                        // solution: query for the user or use string array with objectIds
//                                        List<GloopUser> tmp = new ArrayList<>();
//                                        for (String userId : userIds) {
//                                            tmp.add(new GloopUser(userId));
//                                        }
//                                        group.addMembers(tmp);
//                                        group.save();
//                                        tmp.clear();
//                                        adapter.add(name);
//                                        int spinnerPosition = adapter.getPosition(name);
//                                        spinner.setSelection(spinnerPosition);
//                                    }
//                                }
//                            }
//                    );
//                    AlertDialog b = dialogBuilder.create();
//                    b.show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        dialogBuilder.setTitle("Add new task");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                final String description = edt.getText().toString();
                if (!description.isEmpty()) {

                    final Task task = new Task();
                    task.setDescription(description);

//                    final String selectedGroup = spinner.getSelectedItem().toString();
//
//
//                    if (!selectedGroup.equals("New Group")) {
//                        if (!selectedGroup.equals("Private")) {
//                            final GloopGroup group = Gloop.all(GloopGroup.class).where().equalsTo("name", selectedGroup).first();
//                            if (group != null)
//                                task.setUser(group);
//                            tasks.add(task);
//                        } else if (selectedGroup.equals("Private")) {
                            tasks.add(task);
//                        }
//                    }
                }
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.task_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        tasks = Gloop.all(Task.class);

        mAdapter = new TasksAdapter(tasks);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Gloop.sync();

                tasks = Gloop.all(Task.class);
                mAdapter = new TasksAdapter(tasks);
                mRecyclerView.setAdapter(mAdapter);

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0) {
//            if (resultCode == RESULT_OK) {
//                String contents = data.getStringExtra("SCAN_RESULT");
//                userIds.add(contents);
//                userAdapter.notifyDataSetChanged();
//            }
//        }
//    }
}