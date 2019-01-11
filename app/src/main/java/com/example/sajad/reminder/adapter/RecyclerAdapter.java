package com.example.sajad.reminder.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sajad.reminder.DeleteInterface;
import com.example.sajad.reminder.R;
import com.example.sajad.reminder.model.ReminderModel;
import com.example.sajad.reminder.ui.EditReminder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajad on 5/25/2018.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ListViewHolder> {

    private List<ReminderModel> mReminderModels;
    private boolean multiSelect = false;
    private ArrayList<Integer> selectedItems = new ArrayList<Integer>();
    private DeleteInterface mDeleteInterface;

    public RecyclerAdapter(List<ReminderModel> reminderModels, DeleteInterface deleteInterface) {
        mReminderModels = reminderModels;
        mDeleteInterface = deleteInterface;
    }

    @Override
    public RecyclerAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ListViewHolder holder, final int position) {
        // model ra gerefte va on meghadr haye morede nazaro behesh set kardim
        final ReminderModel reminderModel = mReminderModels.get(position);
        holder.title.setText(reminderModel.getTitle());
        holder.time.setText(String.format("%02d:%02d", reminderModel.getHour(), reminderModel.getMinute()));
        holder.date.setText(reminderModel.getDate());
        holder.repeater.setText(reminderModel.getRepeate() + "");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditReminder.class);
                intent.putExtra("id", reminderModel.getId());
                intent.putExtra("title", reminderModel.getTitle());
                intent.putExtra("time", String.format("%02d:%02d", reminderModel.getHour(), reminderModel.getMinute()));
                intent.putExtra("date", reminderModel.getDate());
                view.getContext().startActivity(intent);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mDeleteInterface.ondelete(reminderModel, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReminderModels.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView time;
        public TextView date;
        public TextView repeater;
        public CardView cardView;

        public ListViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.text_title);
            time = itemView.findViewById(R.id.text_view_time);
            date = itemView.findViewById(R.id.text_view_date);
            repeater = itemView.findViewById(R.id.text_view_repeat);
            cardView = itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
