package nytimes.example.app.api

import nytimes.example.app.models.ArticleListResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {


    @GET("viewed/1.json?api-key=GQPLN8OGa7kGIk4IdYGYhXf1Ky0RnffZ")
    fun getMostPopularArticles(): Call<ArticleListResponse>


}