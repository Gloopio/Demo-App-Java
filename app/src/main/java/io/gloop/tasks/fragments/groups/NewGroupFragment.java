package io.gloop.tasks.fragments.groups;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import io.gloop.tasks.R;
import io.gloop.tasks.constants.Constants;
import io.gloop.permissions.GloopGroup;

public class NewGroupFragment extends Fragment {

    public NewGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21)
            getActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.flatui_alizarin));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_group, container, false);

        final EditText newGroupName = view.findViewById(R.id.new_group_et_name);
        Button next = view.findViewById(R.id.new_group_bt_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = newGroupName.getText().toString();
                GloopGroup group = new GloopGroup();
                group.setName(groupName);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                InviteMembersFragment fragment = new InviteMembersFragment();
                Bundle args = new Bundle();
                args.putSerializable(Constants.BUNDLE_KEY_NEW, group);
                fragment.setArguments(args);
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
