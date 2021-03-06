package com.example.plugins

import com.example.route.userId
import com.example.service.chat.ChatSession
import io.ktor.application.*
import io.ktor.sessions.*
import io.ktor.util.*

fun Application.configureSessions(){
    install(Sessions){
        cookie<ChatSession>("SESSION")
    }

    intercept(ApplicationCallPipeline.Features){
        if (call.sessions.get<ChatSession>() == null){
            //val userId = call.parameters["userId"] ?: return@intercept
            call.sessions.set(ChatSession(call.userId, generateNonce()))
        }
    }
}