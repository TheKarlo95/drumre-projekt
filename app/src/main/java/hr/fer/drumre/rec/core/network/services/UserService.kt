package hr.fer.drumre.rec.core.network.services

import hr.fer.drumre.rec.core.network.model.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @GET("/User")
    suspend fun get(): List<User>

    @POST("User/Login")
    suspend fun login(): User
}
