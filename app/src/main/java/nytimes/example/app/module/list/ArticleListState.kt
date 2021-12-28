package nytimes.example.app.module.list

import nytimes.example.app.models.ArticleModel

sealed class ArticleListState {

    object ArticlesRequested : ArticleListState()
    class ArticlesLoaded(val list: List<ArticleModel>) : ArticleListState()
    class Error(val throwable: Throwable) : ArticleListState()
}