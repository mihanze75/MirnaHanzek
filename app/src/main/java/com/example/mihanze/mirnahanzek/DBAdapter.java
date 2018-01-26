package com.example.mihanze.mirnahanzek;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mihanze on 1/26/18.
 */

public class DBAdapter {

        static final String KEY_ROWID = "_id";
        static final String KEY_ID_SPORTASA = "id_sportasa";
        static final String KEY_IME = "ime";
        static final String KEY_PREZIME = "prezime";
        static final String KEY_NAZIV = "naziv";
        static final String KEY_VRSTA = "vrsta";
        static final String KEY_OLIMP = "olimpijski";
        static final String TAG = "DBAdapter";

        static final String DATABASE_NAME = "MyDB";
        static final String DATABASE_TABLE = "sport";
        static final String DATABASE_TABLE2 = "sportas";
        static final int DATABASE_VERSION = 2;

        static final String DATABASE_CREATE =
                "create table sport (_id text primary key , "
                        + "naziv text not null, vrsta text not null, olimpijski text not null);";

        static final String DATABASE_CREATE2 =
                "create table sportas (id_sportasa text primary key , "
                        + "ime text not null, prezime text not null);";

        static final String DATABASE_DELETE =
                "delete * from sport";

        static final String DATABASE_DELETE2 =
                "delete * from sportas";

        final Context context;

        DBAdapter.DatabaseHelper DBHelper;
        SQLiteDatabase db;

    public DBAdapter(Context ctx)
        {
            this.context = ctx;
            DBHelper = new DBAdapter.DatabaseHelper(context);
        }

        private static class DatabaseHelper extends SQLiteOpenHelper
        {
            DatabaseHelper(Context context)
            {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db)
            {
                try {
                    db.execSQL(DATABASE_CREATE);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL(DATABASE_CREATE2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
            {
                Log.w(TAG, "Upgrading db from" + oldVersion + "to"
                        + newVersion );
                db.execSQL("DROP TABLE IF EXISTS sport");
                db.execSQL("DROP TABLE IF EXISTS sportas");
                onCreate(db);
            }
        }

        //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a contact into the database---
    public long insertContact(String id, String name, String tip, String olimpijski)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROWID, id);
        initialValues.put(KEY_NAZIV, name);
        initialValues.put(KEY_VRSTA, tip);
        initialValues.put(KEY_OLIMP, olimpijski);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public long insertContact2(String id2, String name, String surname)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ID_SPORTASA, id2);
        initialValues.put(KEY_IME, name);
        initialValues.put(KEY_PREZIME, surname);
        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean deleteContact2(long rowId)
    {
        return db.delete(DATABASE_TABLE2, KEY_ROWID + "=" + rowId, null) > 0;
    }
    //---retrieves all the contacts---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAZIV,
                KEY_VRSTA, KEY_OLIMP}, null, null, null, null, null);
    }

    public Cursor getAllContacts2()
    {
        return db.query(DATABASE_TABLE2, new String[] {KEY_ID_SPORTASA ,KEY_IME,
                KEY_PREZIME}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAZIV,
                                KEY_VRSTA, KEY_OLIMP}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getContact2(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {KEY_ID_SPORTASA ,KEY_IME,
                                KEY_PREZIME}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
 /*   public boolean updateContact(long rowId, String name, String email)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
   */

}
