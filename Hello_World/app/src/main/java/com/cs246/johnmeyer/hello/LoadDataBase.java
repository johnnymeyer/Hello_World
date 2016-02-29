package com.cs246.johnmeyer.hello;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by edoyle on 2/26/16.
 */
public class LoadDataBase {
    public String path;
    public final String Name = "DBPages.db";
    private Context c;

    LoadDataBase(Context program) {
        c = program;
        path = c.getFilesDir().getPath();
    }

    public void _copydatabase() throws IOException {

        OutputStream myOutput = new FileOutputStream(path + Name);
        byte[] buffer = new byte[1024];
        int length;
        InputStream myInput = c.getAssets().open("DBPages");
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();

    }
}
