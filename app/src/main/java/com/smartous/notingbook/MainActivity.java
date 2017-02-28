package com.smartous.notingbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.labs.notingbook.R;
import com.labs.notingbook.noting.NoteModel;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mNotesRecycler;
    private ArrayList<NoteModel> mNotes = new ArrayList<>();


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
        mNotes.add(nm);
        mNotes.add(nm);
        mNotes.add(nm);


        mNotesRecycler = (RecyclerView) findViewById(R.id.recycler_notes);
        NotesAdapter notesAdapter = new NotesAdapter(mNotes);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mNotesRecycler.setLayoutManager(llm);
        mNotesRecycler.setAdapter(notesAdapter);

    }
}
