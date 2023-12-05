package com.faradhy.storyusersapp

import com.faradhy.storyusersapp.data.response.RowItemStory

object DataDummy {

    fun generateDummyStoryResponse(): List<RowItemStory> {
        val items: MutableList<RowItemStory> = arrayListOf()
        for (i in 0..100) {
            val story = RowItemStory(
                i.toString(),
                "createdAt + $i",
                "name + $i",
                "description + $i",
                i.toDouble(),
                "",
                i.toDouble()
            )
            items.add(story)
        }
        return items
    }
}