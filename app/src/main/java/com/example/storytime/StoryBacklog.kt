package com.example.storytime

class StoryBacklog(val id: Long?, val story_title:String, val story_description:String, val story_link:String){
    constructor():this(0,"","",""){

    }
}