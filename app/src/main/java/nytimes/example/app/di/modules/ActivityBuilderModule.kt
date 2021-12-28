package nytimes.example.app.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nytimes.example.app.MainActivity

@Module(includes = [FragmentBuilderModule::class])
abstract class ActivityBuilderModule {
	
	@ContributesAndroidInjector
	abstract fun bindMainActivity(): MainActivity
}
