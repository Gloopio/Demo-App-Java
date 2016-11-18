package io.gloop.demo.app.fragments.groups;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import io.gloop.demo.app.R;
import io.gloop.demo.app.constants.Constants;
import io.gloop.demo.app.fragments.GroupsFragment;
import io.gloop.permissions.GloopGroup;

public class InviteMembersFragment extends Fragment {

    private GloopGroup newGroup;

    public InviteMembersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21)
            getActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.flatui_emerald));

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            newGroup = (GloopGroup) bundle.getSerializable(Constants.BUNDLE_KEY_NEW);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_group_invite, container, false);

        final EditText newGroupName = (EditText) view.findViewById(R.id.new_group_et_search);

        newGroupName.setText(newGroup.getName());

        // TODO add members to newGroup.

        Button nextButton = (Button) view.findViewById(R.id.new_group_invite_bt_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGroup.save();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new GroupsFragment();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });

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
