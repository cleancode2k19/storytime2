package com.example.storytime
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.util.ArrayList

val DATABASE_NAME1 = "storytimeDB.db"
val TABLE_NAME1="storyBacklog"
val COL_SID = "storyid"
val COL_TITLE = "story_title"
val COL_DESC="story_description"
val COL_IMG="story_thumbnail"
val COL_LINK="story_link"
class DBStoryBacklog (var context: Context): SQLiteOpenHelper(context, DATABASE_NAME1,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "create table "+ TABLE_NAME1+" ("+
                COL_SID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_TITLE+" varchar(250),"+
                COL_DESC+" varchar(500),"+
                COL_IMG+" varchar(500),"+
                COL_LINK+" varchar(500))";
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun insertData(storyBacklog: StoryBacklog){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_TITLE,storyBacklog.story_title)
        cv.put(COL_DESC,storyBacklog.story_description)
        cv.put(COL_IMG,storyBacklog.story_thumbnail)
        cv.put(COL_LINK,storyBacklog.story_link)
        val result = db.insert(TABLE_NAME,null,cv)
        if(result == -1.toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
    }
    fun readSigninData(storyBacklog: StoryBacklog): Boolean {
        var list : MutableList<Reader> = ArrayList()
        var db = this.readableDatabase
        val query ="select * from "+ TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
            return true
        } else {
            Toast.makeText(context,"Fetch Failed",Toast.LENGTH_SHORT).show()
            return false
        }
        result.close()
        db.close()
    }

}