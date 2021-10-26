package com.example.route

import com.example.Utils.Constants.BANNER_IMAGE_PATH
import com.example.data.request.UpdateProfileRequest
import com.example.Utils.Constants.BASE_URL
import com.example.Utils.Constants.DEFAULT_PAGE_SIZE
import com.example.Utils.Constants.PROFILE_PICTURE_PATH
import com.example.Utils.Constants.USER_NOT_FOUND
import com.example.Utils.QueryParams
import com.example.Utils.save
import com.example.data.response.ApiResponse
import com.example.service.PostService
import com.example.service.UserService
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.io.File
import java.util.*


fun Route.getPostsForProfile(
    postService: PostService,
) {
    authenticate {
        get("/api/user/posts") {

            val page = call.parameters[QueryParams.PARAM_PAGE]?.toIntOrNull() ?: 0
            val pageSize = call.parameters[QueryParams.PARAM_PAGE_SIZE]?.toIntOrNull() ?: DEFAULT_PAGE_SIZE

            val posts = postService.getPostsForProfile(
                userId = call.userId,
                page = page,
                pageSize = pageSize
            )
            call.respond(
                HttpStatusCode.OK,
                posts
            )

        }
    }
}

fun Route.getUserProfile(userService: UserService) {
    authenticate {
        get("/api/user/profile") {
            val userId = call.parameters[QueryParams.PARAM_USER_ID]
            if (userId == null || userId.isBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest
                )
                return@get
            }
            val profileResponse = userService.getUserProfile(userId, call.userId)
            if (profileResponse == null) {
                call.respond(
                    HttpStatusCode.OK, ApiResponse(
                        false,
                        USER_NOT_FOUND,
                        ""
                    )
                )
                return@get
            }
            call.respond(
                HttpStatusCode.OK, profileResponse
        )

        }
    }
}

fun Route.general(){
    get ("/"){
        call.respond("Hello Zak, I am running as you can tell!")
    }
}

fun Route.updateUserProfile(
    userService: UserService
) {
    val gson: Gson by inject()
    authenticate {

        put("/api/user/update") {

            val multipart = call.receiveMultipart()
            var updateProfileRequest: UpdateProfileRequest? = null
            var profilePictureFilename: String? = null
            var bannerPictureFilename: String? = null
            //var fileName: String? = null
            multipart.forEachPart { partData ->
                when (partData) {
                    is PartData.FormItem -> {
                        if (partData.name == "update_profile_data") {
                            updateProfileRequest = gson.fromJson(
                                partData.value,
                                UpdateProfileRequest::class.java
                            )

                        }
                    }
                    is PartData.FileItem -> {
                        if (partData.name == "profile_picture") {

                            profilePictureFilename = partData.save(PROFILE_PICTURE_PATH)
                        } else if (partData.name == "banner_image") {
                            bannerPictureFilename = partData.save(BANNER_IMAGE_PATH)
                        }

                        /*val fileBytes = partData.streamProvider().readBytes()
                        val fileExtension = partData.originalFileName?.takeLastWhile { it != '.' }
                        fileName = UUID.randomUUID().toString() + "." + fileExtension

                        File("${PROFILE_PICTURE_PATH}$fileName").writeBytes(fileBytes)
                        //fileName = partData.save(PROFILE_PICTURE_PATH)*/

                    }
                    is PartData.BinaryItem -> Unit
                }
            }
            val profilePictureUrl = "${BASE_URL}profile_pictures/$profilePictureFilename"
            val bannerPictureUrl = "${BASE_URL}banner_images/$bannerPictureFilename"
            //val profilePictureUrl = "${BASE_URL}src/main/$PROFILE_PICTURE_PATH$fileName"

            updateProfileRequest?.let { request ->

                val updateAcknowledged = userService.updateUser(
                    userId = call.userId,
                    profileImageUrl = profilePictureUrl,
                    bannerImageUrl = bannerPictureUrl,
                    updateProfileRequest = request
                )

                if (updateAcknowledged) {
                    call.respond(HttpStatusCode.OK, ApiResponse<Unit>(true, ""))

                } else {
                    File("${PROFILE_PICTURE_PATH}/$profilePictureFilename").delete()
                    if (updateAcknowledged) {
                        call.respond(HttpStatusCode.OK, ApiResponse<Unit>(true, ""))
                    } else {
                        File("${PROFILE_PICTURE_PATH}/$profilePictureFilename").delete()

                        call.respond(HttpStatusCode.InternalServerError)

                    }
                } ?: kotlin.run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

            }
        }

    }
}


