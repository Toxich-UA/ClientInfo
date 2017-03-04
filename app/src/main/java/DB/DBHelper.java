package DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;

public class DBHelper extends SQLiteOpenHelper {


    private static String DB_PATH = null;
    private static String DB_NAME = "ClientInfo";
    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE_CLIENT = "Clients";
    public static final String TABLE_WAYBILL = "Waybill";

    //Clients table column
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_NAME = "First_Name";
    public static final String COLUMN_LAST_NAME = "Last_Name";
    public static final String COLUMN_MIDDLE_NAME = "Middle_Name";
    public static final String COLUMN_Phone = "Phone";

    //Waybill table column
    public static final String COLUMN_CLIENT_ID = "Client_id";
    public static final String COLUMN_NUMBER = "Number";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_COMMENT = "Comment";
    public static final String COLUMN_SUM = "Sum";

    public static final String[] CLIENT_COLUMN = new String[]{COLUMN_ID ,COLUMN_FIRST_NAME,COLUMN_LAST_NAME, COLUMN_MIDDLE_NAME, COLUMN_Phone};
    public static final String[] WAYBILL_COLUMN = new String[]{COLUMN_CLIENT_ID, COLUMN_NUMBER ,COLUMN_DATE,COLUMN_COMMENT, COLUMN_SUM};

    public SQLiteDatabase database;
    private final Context myContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext = context;
        DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";
    }


    public void create(){

        if (!(check())) {
            this.getReadableDatabase();
            try {
                copy();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Проверяет, существует ли уже эта база, чтобы не копировать каждый раз при запуске приложения
     * @return true если существует, false если не существует
     */
    private boolean check(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //база еще не существует
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    /**
     * Копирует базу из папки assets заместо созданной локальной БД
     * Выполняется путем копирования потока байтов.
     * */
    private void copy() throws IOException{
        //Открываем локальную БД как входящий поток
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        //Путь ко вновь созданной БД
        String outFileName = DB_PATH + DB_NAME;

        //Открываем пустую базу данных как исходящий поток
        OutputStream myOutput = new FileOutputStream(outFileName);

        //перемещаем байты из входящего файла в исходящий
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //закрываем потоки
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void open() throws SQLException{
        //открываем БД
        String myPath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if(database != null)
            database.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getData(String table, String[] columns, String selection, String[] selectionArgs){
        return database.query(table, columns, selection, selectionArgs, null, null, null);
    }
    // Здесь можно добавить вспомогательные методы для доступа и получения данных из БД
    // вы можете возвращать курсоры через "return database.query(....)", это облегчит их использование
    // в создании адаптеров для ваших view
}