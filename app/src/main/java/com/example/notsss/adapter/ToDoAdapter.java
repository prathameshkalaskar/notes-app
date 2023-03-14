package com.example.notsss.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notsss.AddNewTask;
import com.example.notsss.DatabaseHandler;
import com.example.notsss.MainActivity;
import com.example.notsss.R;
import com.example.notsss.model.ToDoModel;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder>{

    private List<ToDoModel> toDoList;
    private MainActivity activity;
    DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db , MainActivity activity){
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout_design, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position){
        db.OpenDatabase();

        final ToDoModel item = toDoList.get(position);
        holder.task.setText(item.getTask());
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<ToDoModel>toDoList) {
        this.toDoList = toDoList;
        notifyDataSetChanged();
    }

    public void editItem(int position){
        ToDoModel item= toDoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public  void deleteItem(int position){
        ToDoModel item= toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.textview);
        }
    }


}
