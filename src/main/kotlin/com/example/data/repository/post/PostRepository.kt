package com.example.data.repository.post

import com.example.utils.Constants.DEFAULT_PAGE_SIZE
import com.example.data.models.Post
import com.example.data.response.PostResponsee

interface PostRepository {

    //DTO for the post collection

    suspend fun createPost(post:Post):Boolean

    suspend fun deletePost(postId:String)

    suspend fun getPostsByFollows(
        userId:String,
        page:Int = 0,
        pageSize:Int = DEFAULT_PAGE_SIZE):List<Post>

    suspend fun getPostsForProfile(
        userId:String,
        page:Int = 0,
        pageSize:Int,):List<Post>

    suspend fun getPost(postId: String):Post?

    suspend fun getPostDetails(postId: String,userId: String):PostResponsee?

    suspend fun getAllPosts( page:Int = 0,
                             pageSize:Int = DEFAULT_PAGE_SIZE,isImage:Boolean):List<Post>

    suspend fun updateCommentCount(postId:String):Boolean


}