package com.smartous.notingbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.labs.notingbook.R;
import com.labs.notingbook.noting.NoteModel;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int CREATE_NEW_NOTE_ACTION = 100;

    private RecyclerView mNotesRecycler;
    private ArrayList<NoteModel> mNotes = new ArrayList<>();
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteModel nm = new NoteModel.Builder()
                .withName("Test")
                .withDescription("test desc")
                .withDate(new Date())
                .withImportanceLevel(NoteModel.ImportanceLevel.One)
                .build();
       // mNotes.add(nm);


        mNotesRecycler = (RecyclerView) findViewById(R.id.recycler_notes);
        notesAdapter = new NotesAdapter(mNotes);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mNotesRecycler.setLayoutManager(llm);
        mNotesRecycler.setAdapter(notesAdapter);

        Intent createNewNoteIntent = new Intent(this, NoteCreateActivity.class);
        startActivityForResult(createNewNoteIntent, CREATE_NEW_NOTE_ACTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case CREATE_NEW_NOTE_ACTION : {
                    NoteModel note = data.getParcelableExtra(NoteCreateActivity.NEW_NOTE);
                    mNotes.add(note);
                    notesAdapter.notifyDataSetChanged();
                    break;
                }
            }
    }
}
