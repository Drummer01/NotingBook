package com.smartous.notingbook;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.labs.notingbook.R;
import com.labs.notingbook.noting.NoteModel;

import org.w3c.dom.Text;

import java.util.Date;

public class NoteCreateActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String NEW_NOTE  = "new_note";

    private TextView mTitle;
    private TextView mDescription;
    private RadioGroup mImportance;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);

        mTitle = (TextView) findViewById(R.id.create_title);
        mDescription = (TextView) findViewById(R.id.create_desc);
        mImportance = (RadioGroup) findViewById(R.id.create_radio_group);
        mImage = (ImageView) findViewById(R.id.note_create_img);

        findViewById(R.id.upload).setOnClickListener(this);
        findViewById(R.id.create_note_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_note_btn : {
                int checkedIndex = mImportance.indexOfChild(findViewById(mImportance.getCheckedRadioButtonId()));
                NoteModel note =  new NoteModel.Builder()
                        .withDate(new Date())
                        .withName(mTitle.getText().toString().trim())
                        .withDescription(mDescription.getText().toString().trim())
                        .withImportanceLevel(NoteModel.ImportanceLevel.createFromInt(checkedIndex))
                        .withImagePath((String)mImage.getTag())
                        .build();
                Intent data = new Intent();
                data.putExtra(NEW_NOTE, note);

                setResult(MainActivity.CREATE_NEW_NOTE_ACTION, data);
                finish();
                break;
            }

            case R.id.upload : {
                Intent selectPicIntent = new Intent(Intent.ACTION_PICK);
                selectPicIntent.setType("image/*");
                startActivityForResult(selectPicIntent, 12345);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 12345 && data != null)
        {
            Uri pickedImage = data.getData();
            mImage.setImageURI(pickedImage);
            mImage.setTag(pickedImage.toString());
        }
    }
}
