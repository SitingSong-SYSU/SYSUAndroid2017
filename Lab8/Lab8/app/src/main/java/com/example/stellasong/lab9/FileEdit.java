package com.example.stellasong.lab9;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.stellasong.lab9.MainActivity.mainActivity;

/**
 * Created by StellaSong on 2017/11/28.
 */

public class FileEdit extends AppCompatActivity {
    public Button save;
    public Button load;
    public Button clear;
    public Button delete;
    private EditText editTitle;
    private EditText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity.finish();
        setContentView(R.layout.activity_edit);

        save = (Button) findViewById(R.id.BtnSave);
        load = (Button) findViewById(R.id.BtnLoad);
        clear = (Button) findViewById(R.id.BtnClear2);
        delete = (Button) findViewById(R.id.BtnDelete);
        editTitle = (EditText) findViewById(R.id.editText3);
        editContent = (EditText) findViewById(R.id.editText4);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                try (FileOutputStream fileOutputStream = openFileOutput(title + ".txt", MODE_PRIVATE)) {
                    String str = "";
                    str = editContent.getText().toString();
                    fileOutputStream.write(str.getBytes());
                    Toast.makeText(FileEdit.this, "Save successfully", Toast.LENGTH_LONG).show();
                    return ;
                } catch (IOException ex) {
                    Log.e("TAG", ex.toString());
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                try (FileInputStream fileInputStream = openFileInput(title + ".txt")) {
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    editContent.setText(new String(contents));
                    Toast.makeText(FileEdit.this, "Load successfully", Toast.LENGTH_LONG).show();
                    return ;
                } catch (IOException ex) {
                    Log.e("TAG", ex.toString());
                    Toast.makeText(FileEdit.this, "Fail to load file.", Toast.LENGTH_LONG).show();
                    return ;
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTitle.setText("");
                editContent.setText("");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();

                //获取绝对路径
                File file = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + title + ".txt");
                //删除文件
                if (file.exists()) {
                    file.delete();
                    Toast.makeText(FileEdit.this, "Delete successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(FileEdit.this, "Fail to delete file", Toast.LENGTH_LONG).show();
                }

                //重新载入文件
                try (FileInputStream fileInputStream = openFileInput(title + ".txt")) {
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    editContent.setText(new String(contents));
                    Toast.makeText(FileEdit.this, "Load successfully", Toast.LENGTH_LONG).show();
                    return ;
                } catch (IOException ex) {
                    Log.e("TAG", ex.toString());
                    Toast.makeText(FileEdit.this, "Fail to load file.", Toast.LENGTH_LONG).show();
                    return ;
                }
            }
        });
    }
}
