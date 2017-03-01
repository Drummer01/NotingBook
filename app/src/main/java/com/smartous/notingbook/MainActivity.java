package com.smartous.notingbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.labs.notingbook.R;
import com.labs.notingbook.noting.NoteModel;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int ACTION_CREATE_NOTE = 100;
    public static final int ACTION_EDIT_NOTE = 101;

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
        //mNotes.add(nm);
        //mNotes.add(nm);


        mNotesRecycler = (RecyclerView) findViewById(R.id.recycler_notes);
        registerForContextMenu(mNotesRecycler);

        notesAdapter = new NotesAdapter(mNotes);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mNotesRecycler.setLayoutManager(llm);
        mNotesRecycler.setAdapter(notesAdapter);


        Intent createNewNoteIntent = new Intent(this, NoteCreateActivity.class);
        startActivityForResult(createNewNoteIntent, ACTION_CREATE_NOTE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle(R.string.option_menu_header);
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.cardview_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu_edit : {
                Intent editIntent = new Intent(this, NoteCreateActivity.class);
                editIntent.putExtra(NoteCreateActivity.KEY_NOTE_MODEL, mNotes.get(notesAdapter.getPosition()));
                editIntent.putExtra(NoteCreateActivity.KEY_NOTE_INDEX, notesAdapter.getPosition());
                editIntent.setAction(Intent.ACTION_EDIT);
                startActivityForResult(editIntent, ACTION_EDIT_NOTE);
                break;
            }
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case ACTION_CREATE_NOTE : {
                    NoteModel note = data.getParcelableExtra(NoteCreateActivity.NEW_NOTE);
                    mNotes.add(note);
                    notesAdapter.notifyDataSetChanged();
                    break;
                }

                case ACTION_EDIT_NOTE : {
                    int index = data.getIntExtra(NoteCreateActivity.KEY_NOTE_INDEX, -1);
                    NoteModel note = data.getParcelableExtra(NoteCreateActivity.KEY_NOTE_MODEL);
                    mNotes.set(index, note);
                    notesAdapter.notifyDataSetChanged();
                    break;
                }
            }
    }
}
