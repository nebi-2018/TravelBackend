# BuddyRadar Api
You need to be a user inorder to test the endpoints.
## Tech-Stacks

* Kotlin
* Ktor Framework
* Dependency Injection -> Koin
* Authorization -> JWT
* Database -> MongoDB
* ORM Framework -> KMongo
* Build Tool -> Gradle
* Server -> Netty

## Architecture
![architecture](https://github.com/Dokaw2017/TravelBackend/blob/main/src/main/resources/static/architecture.png)
## Endpoints

### Events
* POST /api/plan/create
* GET /api/plan/get
* GET /api/plan/filter
* GET /api/plan/all
### Posts
* POST /api/post/create
* POST /api/post/get
* DELETE /api/post/delete
* GET /api/post/all
* GET /api/post/detail
### Chats
* GET /api/chat/messages
* GET /api/chats
* WEBSOCKET /api/chat/websocket
### Authentication
* POST /api/user/create
* POST /api/user/login
* GET /api/user/authenticate
### Comment
* POST /api/comment/create
* GET /api/comment/get
* DELETE /api/comment/delete
### Follow
* POST /api/following/follow
* DELETE /api/following/unfollow
### Likes
* POST /api/like
* DELETE /api/unlike
* GET /api/like/parent
### User
* GET /api/user/posts
* GET /api/user/profile
* put /api/user/updatee
* GET /api/user/myprofile



  
  
  
  
 
 

