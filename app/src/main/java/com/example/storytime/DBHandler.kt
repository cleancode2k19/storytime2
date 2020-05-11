package com.example.storytime

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.security.AccessControlContext

val DATABASE_NAME = "storytime"
val TABLE_NAME="reader"
val COL_ID = "readerid"
val COL_NAME = "name"
val COL_EMAIL="email"
val COL_PASSWORD="password"

class DBHandler (var context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "create table "+ TABLE_NAME+" ("+
                COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_NAME+" varchar(250),"+
                COL_EMAIL+" varchar(150),"+
                COL_PASSWORD+" varchar(150))";
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun insertData(reader : Reader){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NAME,reader.name)
        cv.put(COL_EMAIL,reader.email)
        cv.put(COL_PASSWORD,reader.password)
        val result = db.insert(TABLE_NAME,null,cv)
        if(result == -1.toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
    }
}