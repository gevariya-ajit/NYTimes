package nytimes.example.app.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nytimes.example.app.module.details.ArticleDetailsFragment
import nytimes.example.app.module.list.ArticleListFragment

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindArticleListFragment(): ArticleListFragment

    @ContributesAndroidInjector
    abstract fun bindArticleDetailsFragment(): ArticleDetailsFragment
}