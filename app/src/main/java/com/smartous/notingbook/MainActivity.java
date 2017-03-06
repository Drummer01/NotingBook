package com.smartous.notingbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.labs.notingbook.R;
import com.labs.notingbook.SettingsActivity;
import com.labs.notingbook.noting.NoteModel;
import com.labs.notingbook.theming.ThemeManager;

import java.util.ArrayList;
import java.util.Collections;
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
        setTheme(ThemeManager.getInstance(this).getCurrentThemeOrDefault());
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

        //startActivity(new Intent(this, SettingsActivity.class));
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
            case R.id.context_menu_edit: {
                Intent editIntent = new Intent(this, NoteCreateActivity.class);
                editIntent.putExtra(NoteCreateActivity.KEY_NOTE_MODEL, mNotes.get(notesAdapter.getPosition()));
                editIntent.putExtra(NoteCreateActivity.KEY_NOTE_INDEX, notesAdapter.getPosition());
                editIntent.setAction(Intent.ACTION_EDIT);
                startActivityForResult(editIntent, ACTION_EDIT_NOTE);
                break;
            }

            case R.id.context_menu_remove: {
                mNotes.remove(notesAdapter.getPosition());
                notesAdapter.notifyDataSetChanged();
                break;
            }
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_item_create_note : {
                Intent createNewNoteIntent = new Intent(this, NoteCreateActivity.class);
                startActivityForResult(createNewNoteIntent, ACTION_CREATE_NOTE);
                break;
            }

            case R.id.filter_high_importancy : {
//                ArrayList<NoteModel> filtred = new ArrayList<>();
//                for(NoteModel model : mNotes) {
//                    if(model.getImportance().equals(NoteModel.ImportanceLevel.Three)) {
//                        filtred.add(model);
//                    }
//                }
//                notesAdapter.setSearch(filtred);
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            }

            case R.id.filter_medium_importancy : {
                ArrayList<NoteModel> filtred = new ArrayList<>();
                for(NoteModel model : mNotes) {
                    if(model.getImportance().equals(NoteModel.ImportanceLevel.Two)) {
                        filtred.add(model);
                    }
                }
                notesAdapter.setSearch(filtred);
                break;
            }

            case R.id.filter_low_importancy : {
                ArrayList<NoteModel> filtred = new ArrayList<>();
                for(NoteModel model : mNotes) {
                    if(model.getImportance().equals(NoteModel.ImportanceLevel.One)) {
                        filtred.add(model);
                    }
                }
                notesAdapter.setSearch(filtred);
                break;
            }

            case R.id.filter_clear_importancy : {
                notesAdapter.setSearch(mNotes);
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        switch (requestCode) {
            case ACTION_CREATE_NOTE: {
                NoteModel note = data.getParcelableExtra(NoteCreateActivity.NEW_NOTE);
                mNotes.add(note);
                notesAdapter.notifyDataSetChanged();
                break;
            }

            case ACTION_EDIT_NOTE: {
                int index = data.getIntExtra(NoteCreateActivity.KEY_NOTE_INDEX, -1);
                NoteModel note = data.getParcelableExtra(NoteCreateActivity.KEY_NOTE_MODEL);
                mNotes.set(index, note);
                notesAdapter.notifyDataSetChanged();
                break;

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList newListNote = new ArrayList();
                for(NoteModel note : mNotes){
                    if(note.getName().contains(newText))
                        newListNote.add(note);
                }
                notesAdapter.setSearch(newListNote);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}
