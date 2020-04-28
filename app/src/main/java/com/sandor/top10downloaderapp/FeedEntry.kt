package com.sandor.top10downloaderapp

class FeedEntry {
    var name: String = ""
    var artist: String = ""
    var releaseDate: String = ""
    var summary: String = ""
    var imageURL: String = ""

    override fun toString(): String {
        return """
            name: $name
            artist: $artist
            release date: $releaseDate
            summary: $summary
            imageURL: $imageURL
        """.trimIndent()
    }
}