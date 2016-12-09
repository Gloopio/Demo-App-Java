package io.gloop.demo.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.gloop.demo.app.R;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.ItemViewHolder> {

    class ItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView textView;

        ItemViewHolder(View view) {
            super(view);
            this.layout = (LinearLayout) view.findViewById(R.id.invite_layout);
            this.textView = (TextView) view.findViewById(R.id.task_item_name);
            view.setClickable(true);
        }
    }

    private List<String> emails;

    public InviteAdapter(List<String> emails) {
        this.emails = emails;
    }

    public void updateList(List<String> emails) {
        this.emails = emails;
        notifyDataSetChanged();
    }

    @Override
    public InviteAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invite, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        final String item = this.emails.get(position);

        holder.textView.setText(item);
    }

    @Override
    public int getItemCount() {
        return this.emails.size();
    }

}