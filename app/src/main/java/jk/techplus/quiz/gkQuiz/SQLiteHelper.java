package jk.techplus.quiz.gkQuiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "gkquizdb.db";
    private Context context;

    public SQLiteHelper(Context context2) {
        super(context2, DB_NAME, null, 1);
        this.context = context2;
        File databasePath = context2.getDatabasePath(DB_NAME);
        if (!databasePath.exists()) {
            try {
                SQLiteDatabase openOrCreateDatabase = context2.openOrCreateDatabase(DB_NAME, 0, null);
                if (openOrCreateDatabase != null) {
                    openOrCreateDatabase.close();
                }
                copyDataBase(databasePath);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("SQLiteHelper", "Error");
            }
        }
    }

    private void copyDataBase(File file) throws IOException {
        InputStream open = this.context.getAssets().open(DB_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = open.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
                return;
            }
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
