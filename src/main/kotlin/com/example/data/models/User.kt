package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.*

data class User(
    val firstName:String,
    val lastname:String,
    val email:String,
    val username:String,
    val password:String,
    val phoneNumber:Long,
    val gender:String,
    val location:List<String>,
    val hobbies:List<String>,
    val birthDay: Date,
    val buddyId:List<String>,
    val inviteId:List<String>,
    val friendsId:String,
    val profileImageUrl:String,
    val chatGroupId:List<String>,
    @BsonId
    val id:String = ObjectId().toString()
)
