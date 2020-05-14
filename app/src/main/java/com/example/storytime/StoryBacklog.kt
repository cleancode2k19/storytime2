package com.example.storytime

class StoryBacklog {
    var storyid: Int = 0
    var story_title: String = ""
    var story_description: String = ""
    var story_thumbnail: String = ""
    var story_link: String = ""
    constructor(story_title:String, story_description:String, story_thumbnail:String, story_link:String){
        this.story_title = story_title
        this.story_description = story_description
        this.story_thumbnail = story_thumbnail
        this.story_link = story_link
    }

}