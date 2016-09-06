package deepak.tns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "tnss";
    // tasks table name
    private static final String TABLE_QUEST = "quest";
    // tasks Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer"; //correct option
    private static final String KEY_OPTA= "opta"; //option a
    private static final String KEY_OPTB= "optb"; //option b
    private static final String KEY_OPTC= "optc"; //option c
    private SQLiteDatabase dbase;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase=db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
                +KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
        db.execSQL(sql);
        addQuestions();
        //db.close();
    }
    private void addQuestions()
    {
        Question q1=new Question("What is JP?","Jalur Pesawat", "Jack sParrow", "Jasa Programmer", "Jasa Programmer");
        this.addQuestion(q1);
        Question q2=new Question("where the JP place?", "Monas, Jakarta", "Gelondong, Bangun Tapan, bantul", "Gelondong, Bangun Tapan, bandul", "Gelondong, Bangun Tapan, bantul");
        this.addQuestion(q2);
        Question q3=new Question("who is CEO of the JP?","Usman and Jack", "Jack and Rully","Rully and Usman", "Rully and Usman" );
        this.addQuestion(q3);
        Question q4=new Question("what do you know about JP?", "JP is programmer home", "JP also realigy home", "all answer is true","all answer is true");
        this.addQuestion(q4);
        Question q5=new Question("what do you learn in JP?","Realigy","Programming","all answer is true","all answer is true");
        this.addQuestion(q5);
        Question q6=new Question("What type of vehicle do you own?","Van","Suv","Alto","Swift");
        this.addQuestion(q6);
        Question q7=new Question("What is your age?","20","30","40","50");
        this.addQuestion(q7);
        Question q8=new Question("You indicated that you eat at Joe’s fast food once every 3 months. Why don’t you eat at Joe’s more often?","There isn’t a location near my house"," I don’t like the taste of the food","Never heard of it","Unkwown");
        this.addQuestion(q8);
        Question q9=new Question("What is your opinion of Crazy Justin’s auto-repair?","Pretty good"," Great","Fantastic","Incredible");
        this.addQuestion(q9);
        Question q10=new Question("How likely are you to go out for dinner and a movie this weekend?","Dinner and Movie"," Dinner Only","Movie Only","Neither");
        this.addQuestion(q10);
        Question q11=new Question("Do not own a computer"," IBM PC"," Apple","Hp","other");
        this.addQuestion(q11);
        Question q12=new Question("Where did you grow up? ","country"," farm","city","field");
        this.addQuestion(q12);
        Question q13=new Question("What do you think about this report?","It's the worst report I've read"," It's somewhere between the worst and best","It's the best report I've read","Knwown");
        this.addQuestion(q13);
        Question q14=new Question("Are you satisfied with your current auto insurance?","Yes"," No","Don't have auto insurance","Donot,Say");
        this.addQuestion(q14);
        Question q15=new Question("Are you in favor of Proposition 13 ?","Yes"," No","Undecide","Knwown");
        this.addQuestion(q15);
        Question q16=new Question("How much is your annual life insurance premium ?","yes," ,"No","Knwown","Undecide");
        this.addQuestion(q16);
        Question q17=new Question("What do you think about this report?","It's the worst report I've read"," It's somewhere between the worst and best","It's the best report I've read","Knwown");
        this.addQuestion(q17);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(db);
    }
    // Adding new question
    public void addQuestion(Question quest) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());
        values.put(KEY_OPTC, quest.getOPTC());
        // Inserting Row
        dbase.insert(TABLE_QUEST, null, values);
    }
    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));
                quest.setOPTC(cursor.getString(5));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }
    public int rowcount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }
}