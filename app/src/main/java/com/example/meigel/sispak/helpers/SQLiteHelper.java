package com.example.meigel.sispak.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.meigel.sispak.models.Gejala;
import com.example.meigel.sispak.models.Keputusan;
import com.example.meigel.sispak.models.Penyakit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meigel on 1/16/19.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    static SQLiteHelper sqh;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "databaseKu";

    private static final String TABLE_PENYAKIT = "penyakits";
    private static final String TABLE_GEJALA = "gejala";
    private static final String TABLE_KEPUTUSAN = "keputusan";

    //KOLOM
    private static final String KEY_PENYAKIT_ID = "penyakid_id";
    private static final String KEY_GEJALA_ID = "gejala_id";
    private static final String KEY_KEPUTUSAN_ID = "keputusan_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PENANGANAN = "penanganan";
    private static final String KEY_DESC = "desc";
    private static final String KEY_DESC1 = "desc1";
    private static final String KEY_DESC2 = "desc2";
    private static final String KEY_DESC3 = "desc3";
    private static final String KEY_KODE = "code";
    private static final String KEY_IMG = "img";
    private static final String KEY_PARAM = "param";
    private static final String KEY_GEJALA = "gejala";
    private static final String KEY_PENYAKIT = "penyakit";
    private static final String KEY_PROBALITAS = "probalitas";

    public static SQLiteHelper getInstance(Context context){
        if(sqh == null){
            sqh = new SQLiteHelper(context);
        }

        return sqh;
    }

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PENYAKIT_TBL = "CREATE TABLE " + TABLE_PENYAKIT + "("
                + KEY_PENYAKIT_ID + " INTEGER PRIMARY KEY,"
                + KEY_KODE + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_PENANGANAN + " TEXT,"
                + KEY_DESC + " TEXT,"
                + KEY_DESC1 + " TEXT,"
                + KEY_DESC2 + " TEXT,"
               + KEY_DESC3 + " TEXT,"
                + KEY_IMG + " TEXT" + ")";

        String CREATE_GEJALA_TBL = "CREATE TABLE " + TABLE_GEJALA + "("
                + KEY_GEJALA_ID + " INTEGER PRIMARY KEY,"
                + KEY_KODE + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_PARAM + " TEXT" + ")";

        String CREATE_KEPUTUSAN_TBL = "CREATE TABLE " + TABLE_KEPUTUSAN + "("
                + KEY_KEPUTUSAN_ID + " INTEGER PRIMARY KEY,"
                + KEY_PENYAKIT + " TEXT,"
                + KEY_GEJALA + " TEXT,"
                + KEY_PROBALITAS + " TEXT" + ")";

        db.execSQL(CREATE_PENYAKIT_TBL);
        db.execSQL(CREATE_GEJALA_TBL);
        db.execSQL(CREATE_KEPUTUSAN_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PENYAKIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GEJALA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KEPUTUSAN);
        onCreate(db);
    }

    public void  onUpdate()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_PENYAKIT);
//        onCreate(db);
    }
    //CRUD PENYAKIT
    public void addPenyakit(Penyakit penyakit){
        System.out.println("penyakit = " + penyakit);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, penyakit.getName());
        values.put(KEY_KODE, penyakit.getKode());
        values.put(KEY_PENANGANAN, penyakit.getPenanganan());
        values.put(KEY_DESC, penyakit.getDesc());
        values.put(KEY_DESC1, penyakit.getDesc1());
        values.put(KEY_DESC2, penyakit.getDesc2());
        values.put(KEY_DESC3, penyakit.getDesc3());
        values.put(KEY_IMG, penyakit.getImg());

        db.insert(TABLE_PENYAKIT, null, values);
        db.close();
    }

    public Penyakit getPenyakit(String code){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        cursor = db.query(TABLE_PENYAKIT,
                new String[] {
                        KEY_PENYAKIT_ID,
                        KEY_KODE,
                        KEY_NAME,
                        KEY_PENANGANAN,
                        KEY_DESC,
                        KEY_DESC1,
                        KEY_DESC2,
                        KEY_DESC3,
                        KEY_IMG}, KEY_KODE + "=?",
                new String[] { code }, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Penyakit penyakit = new Penyakit(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8)

        );

        return penyakit;
    }

    public List<Penyakit> getPenyakits(){
        List<Penyakit> penyakits = new ArrayList<Penyakit>();

        String selectQuery = "SELECT * FROM " + TABLE_PENYAKIT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Penyakit penyakit = new Penyakit(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                );
                penyakits.add(penyakit);
            } while (cursor.moveToNext());
        }

        return penyakits;
    }

    public int updatePenyakit(Penyakit penyakit){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, penyakit.getName());
        values.put(KEY_KODE, penyakit.getKode());
        values.put(KEY_PENANGANAN, penyakit.getPenanganan());
        values.put(KEY_DESC, penyakit.getDesc());
        values.put(KEY_DESC1, penyakit.getDesc1());
        values.put(KEY_DESC2, penyakit.getDesc2());
        values.put(KEY_DESC3, penyakit.getDesc3());
        values.put(KEY_IMG, penyakit.getImg());

        db.update(TABLE_PENYAKIT, values, KEY_PENYAKIT_ID + " = " + penyakit.getId(), null);

        return 0;
    }


    public int deletePenyakit(Penyakit penyakit){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_PENYAKIT,KEY_PENYAKIT_ID + " = " + penyakit.getId(), null);
    }

    //GEJALA

    public void addGejala(Gejala gejala){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_PARAM, gejala.getParameter());
        cv.put(KEY_NAME, gejala.getNama());
        cv.put(KEY_KODE, gejala.getKode());

        db.insert(TABLE_GEJALA, null, cv);
        db.close();
    }

    public Gejala getGejala(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GEJALA, new String[] { KEY_GEJALA_ID, KEY_PARAM, KEY_KODE, KEY_NAME}, KEY_GEJALA_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Gejala gejala = new Gejala(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        return gejala;
    }

    public List<Gejala> getGejalas(){
        List<Gejala> gejalas = new ArrayList<Gejala>();

        String selectQuery = "SELECT * FROM " + TABLE_GEJALA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Gejala penyakit = new Gejala(
                        cursor.getInt(0),
                        cursor.getString(3),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                gejalas.add(penyakit);
            } while (cursor.moveToNext());
        }

        return gejalas;
    }

    public int updateGejala(Gejala gejala){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, gejala.getNama());
        values.put(KEY_KODE, gejala.getKode());

        db.update(TABLE_GEJALA, values, KEY_GEJALA_ID + " = " + gejala.getId(), null);

        return 0;
    }

    public int deleteGejala(Gejala gejala){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_GEJALA,KEY_GEJALA_ID + " = " + gejala.getId(), null);

    }

    //KEPUTUSAN
    public void addKeputusan(Keputusan keputusan){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_GEJALA,keputusan.getGejala());
        cv.put(KEY_PENYAKIT, keputusan.getPenyakit());
        cv.put(KEY_PROBALITAS, keputusan.getProbalitas());

        db.insert(TABLE_KEPUTUSAN, null, cv);
        db.close();
    }

    public Gejala getKeputusan(int id){
        return null;
    }

    public Keputusan getKeputusanByKode(String kode){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KEPUTUSAN,
                new String[] {
                        KEY_KEPUTUSAN_ID,
                        KEY_PENYAKIT,
                        KEY_GEJALA,
                        KEY_PROBALITAS,
                        }, KEY_PENYAKIT + "=?",
                new String[] { kode }, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Keputusan keputusan= new Keputusan(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        return keputusan;
    }
    public Keputusan getKeputusanGejalaByKode(String H,String E){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KEPUTUSAN,
                new String[] {
                        KEY_KEPUTUSAN_ID,
                        KEY_PENYAKIT,
                        KEY_GEJALA,
                        KEY_PROBALITAS,
                        }, KEY_PENYAKIT + "=? AND "+KEY_GEJALA+"=?",
                new String[] { H,E }, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Keputusan keputusan= new Keputusan(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        return keputusan;
    }

    public List<Keputusan> getKeputusans(){
        List<Keputusan> keputusans = new ArrayList<Keputusan>();

        String selectQuery = "SELECT * FROM " + TABLE_KEPUTUSAN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Keputusan penyakit = new Keputusan(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                keputusans.add(penyakit);
            } while (cursor.moveToNext());
        }

        return keputusans;
    }

    public int updateKeputusan(Keputusan keputusan){
        SQLiteDatabase db = this.getWritableDatabase();
//        String CREATE_KEPUTUSAN_TBL = "CREATE TABLE " + TABLE_KEPUTUSAN + "("
//                + KEY_KEPUTUSAN_ID + " INTEGER PRIMARY KEY,"
//                + KEY_PENYAKIT + " TEXT,"
//                + KEY_GEJALA + " TEXT,"
//                + KEY_PROBALITAS + " TEXT" + ")";

        ContentValues values = new ContentValues();
        values.put(KEY_PENYAKIT, keputusan.getPenyakit());
        values.put(KEY_GEJALA, keputusan.getGejala());
        values.put(KEY_PROBALITAS, keputusan.getProbalitas());

        return db.update(TABLE_KEPUTUSAN, values, KEY_KEPUTUSAN_ID + " = " + keputusan.getId(), null);

    }

    public void deleteKeputusan(){

    }
}
