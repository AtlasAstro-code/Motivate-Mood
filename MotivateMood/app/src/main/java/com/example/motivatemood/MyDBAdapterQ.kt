package com.example.motivatemood
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class MyDBAdapterQ(_context: Context) {
    private val DATABASE_NAME: String = "quotestable"
    private var mContext: Context? = null
    private var mDbHelper: MyDBHelperQ? = null
    private var mSQLiteDatabaseQ: SQLiteDatabase? = null
    private val DATABASE_VERSION = 1

    init {
        this.mContext = _context
        mDbHelper = MyDBHelperQ(_context, DATABASE_NAME, null, DATABASE_VERSION)
    }

    public fun open() {
        mSQLiteDatabaseQ = mDbHelper?.writableDatabase
    }

    public fun insertQuote(quote: String) {
        val cv: ContentValues = ContentValues()
        cv.put("quote", quote)
        mSQLiteDatabaseQ?.insert("quotestable", null, cv)
    }

    public fun selectAllQuotes(): List<String> {
        var allQuotes: MutableList<String> = ArrayList();
        var cursor: Cursor = mSQLiteDatabaseQ?.query("quotestable", null, null, null, null, null, null)!!

        if (cursor.moveToFirst()) {
            do {
                allQuotes.add(cursor.getString(1))
            } while (cursor.moveToNext());
        }
        return allQuotes
    }

    public fun deleteAllQuotes() {
        mSQLiteDatabaseQ?.delete("quotestable", null, null)
    }

    inner class MyDBHelperQ(context: Context?,
                           name: String?,
                           factory: SQLiteDatabase.CursorFactory?,
                           version: Int) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(_db: SQLiteDatabase?) {
            val query = "CREATE TABLE quotestable(id integer primary key autoincrement, quote text);"
            _db?.execSQL(query)
        }

        override fun onUpgrade(_db: SQLiteDatabase?, _oldVersion: Int, _newVersion: Int) {
            val query = "DROP TABLE IF EXISTS quotestable;"
            _db?.execSQL(query)
            onCreate(_db)
        }
    }
}