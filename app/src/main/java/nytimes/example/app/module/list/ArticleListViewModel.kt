package nytimes.example.app.module.list

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import nytimes.example.app.base.BaseViewModel
import nytimes.example.app.repository.ArticleRepository
import javax.inject.Inject

class ArticleListViewModel
@Inject constructor(private val repository: ArticleRepository) : BaseViewModel<ArticleListState>() {

    fun getArticlesList() {
        repository.getMostPopularArticles()
            .concatMapIterable { it.results }.toList()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { publish(ArticleListState.ArticlesRequested) }
            .subscribeOn(Schedulers.io())
            .subscribe({ listResponse ->
                publish(ArticleListState.ArticlesLoaded(listResponse))
            }, {
                //Log.e("exception", it.localizedMessage ?: "Exception")
                publish(ArticleListState.Error(it))
            })
            .addTo(disposable)
    }
}