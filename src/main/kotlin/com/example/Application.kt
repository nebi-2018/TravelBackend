package com.example

import com.example.di.mainModule
import io.ktor.application.*
import com.example.plugins.*
import org.koin.ktor.ext.Koin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(Koin){
        modules(mainModule)
    }
    configureRouting()
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
}
