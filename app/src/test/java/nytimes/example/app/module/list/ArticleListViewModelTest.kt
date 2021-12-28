package nytimes.example.app.module.list

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.TestSubscriber
import kotlinx.coroutines.runBlocking
import nytimes.example.app.api.ApiService
import nytimes.example.app.models.ArticleListResponse
import nytimes.example.app.models.ArticleModel
import nytimes.example.app.repository.ArticleRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class ArticleListViewModelTest {

    @Mock
    lateinit var apiService: ApiService
    private lateinit var articleRepository: ArticleRepository
    private lateinit var articleListViewModel: ArticleListViewModel

    lateinit var list: List<ArticleModel>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        articleRepository = ArticleRepository(apiService)
        articleListViewModel = ArticleListViewModel(articleRepository)
        //listenState()

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        listenState()
    }

    private fun listenState() {
        val consumer = Consumer<ArticleListState> {
            when (it) {
                is ArticleListState.ArticlesLoaded -> {
                    list = it.list
                    assert(list.isEmpty())
                    assert(list[0].media?.isNotEmpty() == true)
                }
            }
        }
        articleListViewModel.getState().subscribe(consumer)
    }

    @Test
    fun getArticlesList() {
        val testObserver = TestObserver<ArticleListResponse>()
        articleRepository.getMostPopularArticles().subscribe(testObserver)
        testObserver.await()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
    }


}