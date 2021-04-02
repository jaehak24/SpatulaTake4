package com.common.spatulatake4.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.common.bean.EventSet;
import com.common.listener.OnTaskFinishedListener;
import com.common.spatulatake4.Dialog.ConfirmDialog;
import com.common.spatulatake4.R;
import com.common.spatulatake4.activity.MainActivity;
import com.common.spatulatake4.task.schedule.RemoveScheduleTask;
import com.common.spatulatake4.util.SpatulaUtils;
import com.common.spatulatake4.widget.SlideDeleteView;

import java.util.List;

public class EventSetAdapter extends RecyclerView.Adapter<EventSetAdapter.EventSetViewHolder> {

    private Context mContext;
    private List<EventSet> mEventSets;

    public EventSetAdapter(Context context, List<EventSet> eventSets) {
        mContext = context;
        mEventSets = eventSets;
    }

    @Override
    public EventSetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventSetViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_event_set, parent, false));
    }

    @Override
    public int getItemCount() {
        return mEventSets.size();
    }

    @Override
    public void onBindViewHolder(EventSetViewHolder holder, final int position) {
        final EventSet eventSet = mEventSets.get(position);
        holder.sdvEventSet.close(false);
        holder.tvEventSetName.setText(eventSet.getName());
        holder.vEventSetColor.setBackgroundResource(SpatulaUtils.getEventSetColor(eventSet.getColor()));
        holder.ibEventSetDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteEventSetDialog(eventSet, position);
            }
        });
        holder.sdvEventSet.setOnContentClickListener(new SlideDeleteView.OnContentClickListener() {
            @Override
            public void onContentClick() {
                gotoEventSetFragment(eventSet);
            }
        });
    }

    private void showDeleteEventSetDialog(final EventSet eventSet, final int position) {
        new ConfirmDialog(mContext, R.string.event_set_delete_this_event_set, new ConfirmDialog.OnClickListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm() {
                new RemoveScheduleTask(mContext, new OnTaskFinishedListener<Boolean>() {
                    @Override
                    public void onTaskFinished(Boolean data) {
                        if (data) {
                            removeItem(position);
                        }
                    }
                }, eventSet.getId()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }).show();
    }

    private void gotoEventSetFragment(EventSet eventSet) {
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).gotoEventSetFragment(eventSet);
        }
    }

    protected class EventSetViewHolder extends RecyclerView.ViewHolder {

        private SlideDeleteView sdvEventSet;
        private View vEventSetColor;
        private TextView tvEventSetName;
        private ImageButton ibEventSetDelete;

        public EventSetViewHolder(View itemView) {
            super(itemView);
            sdvEventSet = (SlideDeleteView) itemView.findViewById(R.id.sdvEventSet);
            vEventSetColor = itemView.findViewById(R.id.vEventSetColor);
            tvEventSetName = (TextView) itemView.findViewById(R.id.tvEventSetName);
            ibEventSetDelete = (ImageButton) itemView.findViewById(R.id.ibEventSetDelete);
        }
    }

    public void changeAllData(List<EventSet> eventSets) {
        mEventSets.clear();
        mEventSets.addAll(eventSets);
        notifyDataSetChanged();
    }

    public void insertItem(EventSet eventSet) {
        mEventSets.add(eventSet);
        notifyItemInserted(mEventSets.size() - 1);
    }

    public void removeItem(int position) {
        mEventSets.remove(position);
        notifyDataSetChanged();
    }
}
