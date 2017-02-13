package io.gloop.demo.app.fragments.groups;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.gloop.Gloop;
import io.gloop.demo.app.R;
import io.gloop.demo.app.adapters.InviteAdapter;
import io.gloop.demo.app.constants.Constants;
import io.gloop.demo.app.fragments.GroupsFragment;
import io.gloop.permissions.GloopGroup;
import io.gloop.permissions.GloopUser;

public class InviteMembersFragment extends Fragment {

    private GloopGroup newGroup;
    private List<String> emails;

    public InviteMembersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            newGroup = (GloopGroup) bundle.getSerializable(Constants.BUNDLE_KEY_NEW);
        }

        emails = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_group_invite, container, false);

        final InviteAdapter mAdapter = new InviteAdapter(emails);

        Button nextButton = (Button) view.findViewById(R.id.new_group_invite_bt_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO fin solution without creating list of GloopUsers.
                List<GloopUser> members = new ArrayList<>();
                for (String email : emails) {
                    members.add(new GloopUser(email));
                }
                members.add(Gloop.getOwner());
                newGroup.setMembers(members);
                newGroup.save();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new GroupsFragment();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });

        final EditText emailEditText = (EditText) view.findViewById(R.id.email_text);

        Button addEmail = (Button) view.findViewById(R.id.add_email_to_list_button);
        addEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();

                emails.add(email);
                mAdapter.updateList(emails);

                emailEditText.setText("");
            }
        });

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.invite_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView.setAdapter(mAdapter);

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
