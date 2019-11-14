package jk.techplus.quiz.gkQuiz;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class QuestionDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public QuestionDataSource(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }

    public void close() {
        this.dbHelper.close();
    }

    public MultipleChoice cursortoMCQ(Cursor cursor) {
        MultipleChoice multipleChoice = new MultipleChoice();
        multipleChoice.setId(cursor.getInt(0));
        multipleChoice.setQuestion(cursor.getString(1).trim().replace("۔", ""));
        multipleChoice.setChoice1(cursor.getString(2).trim().replace("۔", ""));
        multipleChoice.setChoice2(cursor.getString(3).trim().replace("۔", ""));
        multipleChoice.setChoice3(cursor.getString(4).trim().replace("۔", ""));
        multipleChoice.setChoice4(cursor.getString(5).trim().replace("۔", ""));
        multipleChoice.setAnswer(cursor.getString(6).trim().replace("۔", ""));
        return multipleChoice;
    }

    public int getNumberOfQuestions(String str) {
        Cursor rawQuery = this.database.rawQuery("select count(*) from questions " + str, null);
        rawQuery.moveToFirst();
        return rawQuery.getInt(0);
    }

    public MultipleChoice getRandomQuestion(String str) {
        Cursor rawQuery = this.database.rawQuery("select id,Question,Choice1,Choice2,Choice3,Choice4,Answer from questions " + str + " order by RANDOM() Limit 1", null);
        rawQuery.moveToFirst();
        return cursortoMCQ(rawQuery);
    }

    public Cursor getData(String str){
        final String TABLE_NAME = "questions";

        this.database.rawQuery("SELECT * FROM questions Where Categ =  '"+str+"' "  , null);
        Cursor rawQuery =  this.database.rawQuery("SELECT * FROM questions WHERE Categ = '"+str+"'", null);
//        String query ="select * from questions Where Categ =" +  str + " ";
//        Cursor data = this.database.rawQuery(query, null);
        return rawQuery;
    }

    public MultipleChoice getQuestion(String str) {
        Cursor rawQuery = this.database.rawQuery("select * from questions " + str + " ", null);
        rawQuery.moveToFirst();
        return cursortoMCQ(rawQuery);
    }

    public String[] getAppCategoryDetail(String str) {

        final String TABLE_NAME = "questions";
        String selectQuery = "SELECT  Question, Answer FROM " + TABLE_NAME + str+ " ";
        Cursor cursor      = this.database.rawQuery(selectQuery, null);
        String[] data      = null;

        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable

            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }


    public void open() throws SQLException {
        this.database = this.dbHelper.getWritableDatabase();
    }
}
