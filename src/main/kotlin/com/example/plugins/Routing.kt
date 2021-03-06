package com.example.plugins

import com.example.route.*
import com.example.service.*
import com.example.service.PlanService
import com.example.service.UserService
import com.example.service.chat.ChatController
import com.example.service.chat.ChatService
import io.ktor.routing.*
import io.ktor.http.content.*
import io.ktor.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    
    val userService:UserService by inject()
    val eventService:PlanService by inject()
    val followService:FollowService by inject()
    val postService: PostService by inject()
    val likeService: LikeService by inject()
    val commentService: CommentService by inject()
    val chatService: ChatService by inject()
    val chatController:ChatController by inject()

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()
    routing {
        //User routes
        authenticate()
        general()
        createUser(userService)
        loginUser(
            userService,
            jwtIssuer,
            jwtAudience,
            jwtSecret
        )
        getUserProfile(userService)
        getPostsForProfile(postService)
        //updateUserProfile(userService)
        updateProfile(userService)
        getMyProfile(userService)

        //Following Routes
        followUser(followService)
        unFollowUser(followService)

        //Post Routes
        cratePostRoute(postService)
        getPostsForFollows(postService)
        deletePost(postService,likeService,commentService)
        getAllPosts(postService)
        createPost(postService)
        getPostDetails(postService)
        //getAllPost(postService,userService)

        //Event Routes
        createEvent(eventService)
        getEventsByUser(eventService)
        getAllEvents(eventService)
        filterPlan(eventService)

        //Like Route
        likeParent(likeService)
        unlikeParent(likeService)
        getLikesForParent(likeService)

        //Comment Route
        createComment(commentService)
        deleteComment(commentService,likeService)
        getCommentsForPost(commentService)

        //Chat Route
        getChatsForUser(chatService)
        getMessagesForChat(chatService)
        chatWebSocket(chatController)

        static{
            resources("static")
        }
    }

}
