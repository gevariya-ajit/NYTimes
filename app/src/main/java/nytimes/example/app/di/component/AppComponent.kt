package nytimes.example.app.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import nytimes.example.app.di.modules.ActivityBuilderModule
import nytimes.example.app.di.modules.AppModule
import nytimes.example.app.di.modules.NetworkModule
import nytimes.example.app.NYTimesApp
import javax.inject.Singleton

@Singleton
@Component(
		modules = [(AndroidSupportInjectionModule::class), (AppModule::class), (NetworkModule::class), (ActivityBuilderModule::class)])
interface AppComponent {
	
	@Component.Builder
	interface Builder {
		
		@BindsInstance
		fun application(application: Application): Builder
		
		fun build(): AppComponent
	}
	
	fun inject(instance: NYTimesApp)
	
}


