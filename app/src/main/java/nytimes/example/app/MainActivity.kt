package nytimes.example.app

import android.os.Bundle
import nytimes.example.app.base.BaseActivity
import nytimes.example.app.module.list.ArticleListFragment

class MainActivity : BaseActivity() {

    override fun layoutId() = R.layout.activity_main;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragmentWithoutTransition(
            supportFragmentManager,
            ArticleListFragment.newInstance(),
            addToBackStack = true
        )
    }
}