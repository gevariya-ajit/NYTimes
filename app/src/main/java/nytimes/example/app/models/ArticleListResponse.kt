package nytimes.example.app.models

import com.google.gson.annotations.SerializedName
import nytimes.example.app.models.ArticleModel

class ArticleListResponse {
    @SerializedName("copyright")
    var copyright: String? = null

    @SerializedName("results")
    var results: List<ArticleModel>? = null
        private set

    @SerializedName("num_results")
    var numResults = 0

    @SerializedName("status")
    var status: String? = null
    fun setArticles(articles: List<ArticleModel>?) {
        results = articles
    }
}