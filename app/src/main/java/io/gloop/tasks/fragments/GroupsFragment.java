package io.gloop.tasks.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.gloop.Gloop;
import io.gloop.GloopList;
import io.gloop.tasks.R;
import io.gloop.tasks.adapters.GroupAdapter;
import io.gloop.tasks.fragments.groups.NewGroupFragment;
import io.gloop.permissions.GloopGroup;

public class GroupsFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private GloopList<GloopGroup> groups;
    private GroupAdapter mAdapter;

    public GroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.activity_group_swipe_refresh_layout);
        mRecyclerView = view.findViewById(R.id.group_list);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, new NewGroupFragment()).commit();
            }
        });

        groups = Gloop.all(GloopGroup.class);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GroupAdapter(groups);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Gloop.sync();

                groups = Gloop.all(GloopGroup.class);
                mAdapter = new GroupAdapter(groups);
                mRecyclerView.setAdapter(mAdapter);

                mSwipeRefreshLayout.setRefreshing(false);
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
