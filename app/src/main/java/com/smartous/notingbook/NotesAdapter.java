package com.smartous.notingbook;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.labs.notingbook.R;
import com.labs.notingbook.noting.NoteModel;

import java.util.List;

/**
 * Created by Focus on 28.02.2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    private final int[] noteStatus = {R.mipmap.ic_low_importance, R.mipmap.ic_medium_importance, R.mipmap.ic_high_importance};
    private List<NoteModel> notesList;

    public NotesAdapter(List<NoteModel> notesList){
        this.notesList = notesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView noteTopImage, noteImportance;
        public TextView noteDateCreate, noteTitle, noteDescription;

        public MyViewHolder(View view) {
            super(view);

            noteTopImage = (ImageView) view.findViewById(R.id.note_top_image);
            noteImportance = (ImageView) view.findViewById(R.id.note_importance);

            noteDateCreate = (TextView) view.findViewById(R.id.note_date_create);
            noteTitle = (TextView) view.findViewById(R.id.note_title);
            noteDescription = (TextView) view.findViewById(R.id.note_description);
        }
    }

    @Override
    public NotesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.MyViewHolder holder, int position) {
        NoteModel note = notesList.get(position);

        holder.noteTopImage.setImageURI(Uri.parse(note.getImagePath()));

        holder.noteImportance.setImageResource(noteStatus[note.getImportance().getLevel()-1]);
        holder.noteTitle.setText(note.getName());
        holder.noteDescription.setText(note.getDescription());
        holder.noteDateCreate.setText(note.getCreationTime().toString());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
