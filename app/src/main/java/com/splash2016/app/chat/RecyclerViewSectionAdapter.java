package com.splash2016.app.chat;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.splash2016.app.R;
import com.splash2016.app.objects.Message;
import com.splash2016.app.objects.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAOJUN on 4/6/16.
 */
public class RecyclerViewSectionAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private static final String TAG = RecyclerViewSectionAdapter.class.getSimpleName();
    private List<MessageModel> messageModelList;

    public RecyclerViewSectionAdapter(List<MessageModel> messageModelList) {
        this.messageModelList = messageModelList;
    }

    @Override
    public int getSectionCount() {
        Log.d(TAG, "RecyclerView section size: " + messageModelList.size());
        return messageModelList.size();
    }

    @Override
    public int getItemCount(int section) {
        Log.d(TAG, "RecyclerView item size: " + messageModelList.get(section).getAllMessagesInSection().size());
        return messageModelList.get(section).getAllMessagesInSection().size();
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        String sectionName = messageModelList.get(section).getDateHeader();
        SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;
        sectionViewHolder.sectionTitle.setText(sectionName);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int section, int relativePosition, int absolutePosition) {
        ArrayList<Message> itemsInSection = messageModelList.get(section).getAllMessagesInSection();
        Message message = itemsInSection.get(relativePosition);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        if(message.isSelf()) {
            itemViewHolder.frameYou.setVisibility(View.VISIBLE);
            itemViewHolder.frameFrom.setVisibility(View.GONE);
            itemViewHolder.txtMsgYou.setText(Html.fromHtml(message.getMessage() + " &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"));
            itemViewHolder.txtTimeYou.setText(message.getTime().replaceAll("\\.", "").toUpperCase());
        } else {
            itemViewHolder.frameYou.setVisibility(View.GONE);
            itemViewHolder.frameFrom.setVisibility(View.VISIBLE);
            itemViewHolder.txtMsgFrom.setText(Html.fromHtml(message.getMessage() + " &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"));
            itemViewHolder.txtTimeFrom.setText(message.getTime().replaceAll("\\.", "").toUpperCase());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView;

        if (viewType == VIEW_TYPE_HEADER) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_header, parent, false);
            return new SectionViewHolder(convertView);
        } else {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_message, parent, false);
            return new ItemViewHolder(convertView);
        }
    }

    // SectionViewHolder Class for Sections
    public static class SectionViewHolder extends RecyclerView.ViewHolder {

        final TextView sectionTitle;

        public SectionViewHolder(View itemView) {
            super(itemView);
            sectionTitle = (TextView) itemView.findViewById(R.id.sectionTitle);
        }
    }

    // ItemViewHolder Class for Items in each Section
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        final FrameLayout frameFrom;
        final FrameLayout frameYou;
        final TextView txtMsgFrom;
        final TextView txtTimeFrom;
        final TextView txtMsgYou;
        final TextView txtTimeYou;

        public ItemViewHolder(View itemView) {
            super(itemView);
            frameFrom = (FrameLayout) itemView.findViewById(R.id.frameFrom);
            frameYou = (FrameLayout) itemView.findViewById(R.id.frameYou);
            txtMsgFrom = (TextView) itemView.findViewById(R.id.txtMsgFrom);
            txtTimeFrom = (TextView) itemView.findViewById(R.id.txtTimeFrom);
            txtMsgYou = (TextView) itemView.findViewById(R.id.txtMsgYou);
            txtTimeYou = (TextView) itemView.findViewById(R.id.txtTimeYou);
        }
    }
}