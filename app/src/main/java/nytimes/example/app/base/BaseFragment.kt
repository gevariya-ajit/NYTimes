package nytimes.example.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
	
	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	
	fun getBaseActivity(): BaseActivity {
		return activity as BaseActivity
	}
	
	@CallSuper
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		AndroidSupportInjection.inject(this)
		setHasOptionsMenu(true)
	}
	
	override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
	                         ): View {
		return container?.inflate(layoutId()) ?: inflater.inflate(layoutId(), container)
	}
	
	abstract fun layoutId(): Int
	
	open fun onBackPressed(): Boolean = false
	
}

fun ViewGroup.inflate(layoutId: Int): View {
	return LayoutInflater
			.from(context)
			.inflate(layoutId, this, false)
}

fun String.capitalize(): String {
	return replaceFirstChar {
		if (it.isLowerCase()) it.titlecase(
				Locale.getDefault()) else it.toString()
	}
}

