package com.splash2016.app.chat;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.splash2016.app.R;
import com.splash2016.app.database.ChatDatabase;
import com.splash2016.app.objects.Message;
import com.splash2016.app.objects.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class RecyclerViewSectionAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private static final String TITLE_DELETE = "Delete message";
    private static final String MESSAGE_DELETE = "Are you sure you want to delete message?";
    private static final String MESSAGE_OK = "Ok";
    private static final String MESSAGE_CANCEL = "Cancel";

    private ChatDatabase chatDatabase;
    private List<MessageModel> messageModelList;
    private Context context;

    public RecyclerViewSectionAdapter(List<MessageModel> messageModelList, Context context) {
        this.messageModelList = messageModelList;
        this.context = context;
    }

    @Override
    public int getSectionCount() {
        return messageModelList.size();
    }

    @Override
    public int getItemCount(int section) {
        return messageModelList.get(section).getAllMessagesInSection().size();
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        String sectionName = messageModelList.get(section).getDateHeader();
        SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;
        sectionViewHolder.sectionTitle.setText(sectionName);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int section, final int relativePosition, int absolutePosition) {
        ArrayList<Message> itemsInSection = messageModelList.get(section).getAllMessagesInSection();
        final Message message = itemsInSection.get(relativePosition);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        if(message.isSelf()) {
            itemViewHolder.frameFrom.setVisibility(View.GONE);
            itemViewHolder.frameYou.setVisibility(View.VISIBLE);
            itemViewHolder.txtMsgYou.setText(Html.fromHtml(message.getMessage() + " &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"));
            itemViewHolder.txtTimeYou.setText(message.getTime().replaceAll("\\.", "").toUpperCase());
            setLongClick(itemViewHolder.frameYou, message, relativePosition, section);
        } else {
            itemViewHolder.frameYou.setVisibility(View.GONE);
            itemViewHolder.frameFrom.setVisibility(View.VISIBLE);
            itemViewHolder.txtMsgFrom.setText(Html.fromHtml(message.getMessage() + " &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"));
            itemViewHolder.txtTimeFrom.setText(message.getTime().replaceAll("\\.", "").toUpperCase());
            setLongClick(itemViewHolder.frameFrom, message, relativePosition, section);
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

    private void setLongClick(FrameLayout frame, final Message message, final int relativePosition, final int section) {
        frame.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alertDialog(message.getId(), relativePosition, section);
                return true;
            }
        });
    }

    private void alertDialog(final long id, final int position, final int section) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning_black_24dp);
        builder.setTitle(TITLE_DELETE);
        builder.setMessage(MESSAGE_DELETE);

        builder.setPositiveButton(MESSAGE_OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chatDatabase = new ChatDatabase(context);
                messageModelList.get(section).getAllMessagesInSection().remove(position);
                notifyDataSetChanged();
                chatDatabase.deleteMessage(chatDatabase, id);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(MESSAGE_CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}