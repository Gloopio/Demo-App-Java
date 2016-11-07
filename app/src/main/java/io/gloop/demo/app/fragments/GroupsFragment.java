package io.gloop.demo.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.gloop.Gloop;
import io.gloop.demo.app.R;
import io.gloop.demo.app.fragments.groups.NewGroupFragment;
import io.gloop.permissions.GloopGroup;

public class GroupsFragment extends Fragment {

    public GroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                createGroupDialog();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, new NewGroupFragment()).commit();


            }
        });
        return view;
    }

    private void createGroupDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_new_group, null);  // TODO Create fragment instead dialog.

        List<String> userIds = new ArrayList<String>(); // TODO fill with all members of the group

        final EditText groupName = (EditText) dialogView.findViewById(R.id.group_name);
        ListView userList = (ListView) dialogView.findViewById(R.id.user_list);
        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, userIds);
        userList.setAdapter(userAdapter);

        Button addUserButton = (Button) dialogView.findViewById(R.id.button_add_user_to_group);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO impl
            }
        });
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
