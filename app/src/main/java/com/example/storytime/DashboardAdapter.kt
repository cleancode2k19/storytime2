package com.example.storytime
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class DashboardAdapter (val mCtx : Context, val layoutResId:Int,val storyList:List<StoryBacklog>) : ArrayAdapter<StoryBacklog>(mCtx, layoutResId, storyList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(layoutResId,null)
        val textViewTitle = view.findViewById<TextView>(R.id.story_title)
        val storyListData = storyList[position]
        textViewTitle.text=storyListData.story_title
        Log.d("Bingo", storyListData.story_title)

        return view
    }
}