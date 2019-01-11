package com.example.sajad.reminder.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sajad.reminder.DeleteInterface;
import com.example.sajad.reminder.R;
import com.example.sajad.reminder.adapter.RecyclerAdapter;
import com.example.sajad.reminder.database.DBHandler;
import com.example.sajad.reminder.model.ReminderModel;

import java.util.List;

/**
 * Created by sajad on 5/25/2018.
 */
public class AllFragment extends Fragment implements DeleteInterface {
    // onCreatView ke view return mikone inflate be recyclere view
    //adaptererecycler view va passesh mideim be recyclerview

    private List reminderModels;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);


        recyclerView = view.findViewById(R.id.my_recycler_view);
       // ArrayList<ReminderModel> reminderModels = new ArrayList<>();
       /* reminderModels.add(new ReminderModel("salam1", 15, 10, "01/05/2018", 2));
        reminderModels.add(new ReminderModel("salam2", 15, 10, "01/05/2018", 2));
        reminderModels.add(new ReminderModel("salam3", 15, 10, "01/05/2018", 2));*/

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DBHandler dbHandler = new DBHandler(getContext());
        reminderModels = dbHandler.getAllReminders();
        adapter = new RecyclerAdapter(reminderModels, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = getActivity().findViewById(R.id.empty_view);
        showEmptyView(emptyView);
    }


    @Override
    public void ondelete(final ReminderModel reminderModel, int position) {
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.MyApp_DialogButton)
                //set message, title, and icon
                .setMessage("Do you want to Delete?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //deleting reminder
                        DBHandler dbHandler = new DBHandler(getContext());
                        dbHandler.deleteReminder(reminderModel.getId());
                        reminderModels.remove(reminderModel);
                        View emptyView = getActivity().findViewById(R.id.empty_view);
                        showEmptyView(emptyView);
                        adapter.notifyDataSetChanged();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    private void showEmptyView(View emptyView) {
        if(reminderModels.isEmpty()){
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.INVISIBLE);
        }
    }
}
