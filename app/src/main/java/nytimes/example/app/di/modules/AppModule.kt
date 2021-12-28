package nytimes.example.app.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

import nytimes.example.app.di.ViewModelModule
import nytimes.example.app.NYTimesApp
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class AppModule {
	
	@Provides
	@Singleton
	fun provideApp(app: Application): NYTimesApp {
		return app as NYTimesApp
	}
	
	@Provides
	@Singleton
	fun provideContext(app: Application): Context {
		return app.applicationContext
	}
}
