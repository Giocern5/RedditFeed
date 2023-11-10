package com.example.tinderonsight.model

data class PostDetails(val title: String,
                       val thumbnail: String,
                       val is_video: Boolean,
                       val url: String,
                       val subreddit_subscribers: String,
                       val author: String,
                       val ups: String,
                       val media_only: Boolean,
                       val created: Long)

data class Post(val kind: String, val data: PostDetails)
data class PostData( val children: List<Post>, val after: String)
data class PostResp(val data: PostData)
