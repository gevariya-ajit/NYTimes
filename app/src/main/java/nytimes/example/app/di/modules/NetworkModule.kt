package nytimes.example.app.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import nytimes.example.app.api.ApiService
import nytimes.example.app.repository.ArticleRepository
import nytimes.example.app.utils.Utils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val onlineInterceptor = Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            val cacheControl = originalResponse.header("Cache-Control")
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
            ) {
                originalResponse
                    .newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 5000)
                    .build()
            } else {
                originalResponse
            }
        }
        val offlineInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (!Utils.isNetworkAvailable(context = context)) {
                request = request
                    .newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached")
                    .build()
            }
            chain.proceed(request)
        }
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val cache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient
            .Builder()
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (Utils.isNetworkAvailable(context))
                    request
                        .newBuilder()
                        .header("Cache-Control", "public, max-age=" + 5)
                        .build()
                else
                    request
                        .newBuilder()
                        .header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                        )
                        .build()
                chain.proceed(request)
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .client(client)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.nytimes.com/svc/mostpopular/v2/")
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(apiService: ApiService): ArticleRepository {
        return ArticleRepository(apiService)
    }

}
