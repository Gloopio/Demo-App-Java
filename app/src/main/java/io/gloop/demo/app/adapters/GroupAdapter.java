package io.gloop.demo.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import io.gloop.GloopList;
import io.gloop.GloopOnChangeListener;
import io.gloop.demo.app.R;
import io.gloop.permissions.GloopGroup;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ItemViewHolder> {

    class ItemViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView itemNameTextView;
        ImageView imageView;
        ImageView privateIcon;
        ImageView checkIcon;

        ItemViewHolder(View view) {
            super(view);
            this.relativeLayout = view.findViewById(R.id.rl_item_list);
            this.itemNameTextView = view.findViewById(R.id.task_item_name);
            this.imageView = view.findViewById(R.id.item_circular_view);
            this.checkIcon = view.findViewById(R.id.check_icon);
            this.privateIcon = view.findViewById(R.id.private_icon);
            view.setClickable(true);
        }
    }

    private GloopList<GloopGroup> groupList;

    public GroupAdapter(GloopList<GloopGroup> groupList) {
        this.groupList = groupList;
        this.groupList.addOnChangeListener(new GloopOnChangeListener() {

            @Override
            public void onChange() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public GroupAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        final GloopGroup item = this.groupList.get(position);

        holder.itemNameTextView.setText(item.getName());

        final String firstLetter = item.getName().substring(0, 1).toUpperCase();
        holder.imageView.setImageDrawable(TextDrawable.builder().buildRound(firstLetter, ColorGenerator.MATERIAL.getColor(firstLetter)));


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.groupList.size();
    }

}