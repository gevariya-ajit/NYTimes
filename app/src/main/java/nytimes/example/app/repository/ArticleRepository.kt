package nytimes.example.app.repository

import io.reactivex.rxjava3.core.Observable
import nytimes.example.app.api.ApiService
import nytimes.example.app.base.BaseRepository
import nytimes.example.app.models.ArticleListResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(private val apiService: ApiService) :
    BaseRepository() {

    fun getMostPopularArticles(): Observable<ArticleListResponse> {
        return Observable.create { emitter ->
            apiService
                .getMostPopularArticles()
                .enqueue(
                    getBaseCallback<ArticleListResponse, ArticleListResponse>(
                        emitter) { response ->
                        emitter.onNext(response)
                        emitter.onComplete()
                    })
        }
    }

}