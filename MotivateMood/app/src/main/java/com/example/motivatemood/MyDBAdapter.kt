package com.example.motivatemood
import android.content.ContentValues
import android.content.Context
import android.database.Cursor as Cursor1
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class MyDBAdapter(_context: Context) {
    private val DATABASE_NAME: String = "action"
    private var mContext: Context? = null
    private var mDbHelper: MyDBHelper? = null
    private var mSQLiteDatabase: SQLiteDatabase? = null
    private val DATABASE_VERSION = 1

    init {
        this.mContext = _context
        mDbHelper = MyDBHelper(_context, DATABASE_NAME, null, DATABASE_VERSION)
    }

    public fun open() {
        mSQLiteDatabase = mDbHelper?.writableDatabase
    }

    public fun insertAction(action: String) {
        val cv: ContentValues = ContentValues()
        cv.put("action", action)
        mSQLiteDatabase?.insert("actions", null, cv)
    }

    public fun selectAllActions(): List<String> {
        var allActions: MutableList<String> = ArrayList()
        var cursor: Cursor1 = mSQLiteDatabase?.query("actions", null, null, null, null, null, null)!!
        if (cursor.moveToFirst()) {
            do {
                allActions.add(cursor.getString(1))
            } while (cursor.moveToNext())
        }
        return allActions;
    }

    public fun deleteAllActions() {
        mSQLiteDatabase?.delete("actions", null, null)
    }

    inner class MyDBHelper(context: Context?,
                           name: String?,
                           factory: SQLiteDatabase.CursorFactory?,
                           version: Int) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(_db: SQLiteDatabase?) {
            val query = "CREATE TABLE actions(id integer primary key autoincrement, axtions text);"
            _db?.execSQL(query)
        }

        override fun onUpgrade(_db: SQLiteDatabase?, _oldVersion: Int, _newVersion: Int) {
            val query = "DROP TABLE IF EXISTS actions;"
            _db?.execSQL(query)
            onCreate(_db)
        }
    }
}