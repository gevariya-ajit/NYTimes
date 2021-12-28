package nytimes.example.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import nytimes.example.app.module.list.ArticleListViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindsViewModelFactory(viewModelFactory: CustomViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(ArticleListViewModel::class)
    abstract fun bindArticleListViewModel(articleListViewModel: ArticleListViewModel): ViewModel

}
