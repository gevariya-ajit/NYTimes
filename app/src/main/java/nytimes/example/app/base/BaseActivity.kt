package nytimes.example.app.base

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import io.reactivex.rxjava3.disposables.CompositeDisposable
import nytimes.example.app.R
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
	
	private var progressDialog: Dialog? = null
	lateinit var disposable: CompositeDisposable
	
	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory //	@Inject
	
	//	lateinit var prefUtils: PrefUtils
	abstract fun layoutId(): Int
	
	@CallSuper
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		AndroidInjection.inject(this)
		setContentView(layoutId())
		disposable = CompositeDisposable()
	}
	
	override fun onBackPressed() {
		if (supportFragmentManager.backStackEntryCount >= 1) {            // We need to check last fragment if it handle back press i.e PrepareOrderFragment
			supportFragmentManager.fragments.reversed()
					.forEach { fragment ->
						if (fragment is BaseFragment) {
							if (!fragment.onBackPressed()) {
								if (supportFragmentManager.backStackEntryCount == 1) finish()
								else super.onBackPressed()
							} else super.onBackPressed()
							return
						} else {
							super.onBackPressed()
						}
					}
		} else super.onBackPressed()
	}
	
	//	/**
	//	 * which = 0 for slide left animation and
	//	 * = 1 for slide bottom animation
	//	 * */
	fun addFragment(supportFragmentManager: FragmentManager, fragment: Fragment, which: Int = 0) {
		val fragmentTransaction = supportFragmentManager.beginTransaction()
		if (which == 0) {
			fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
			                                        R.anim.fragment_slide_left_exit,
			                                        R.anim.fragment_slide_right_enter,
			                                        R.anim.fragment_slide_right_exit)
		} else if (which == 1) {
			fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_bottom_enter,
			                                        R.anim.fragment_slide_bottom_exit,
			                                        0,
			                                        0)
			
		}
		fragmentTransaction.addToBackStack(fragment.javaClass.name)
		fragmentTransaction.add(R.id.container, fragment, fragment.javaClass.name)
		fragmentTransaction.commitAllowingStateLoss()
	}
	
	fun addFragmentWithoutTransition(
			supportFragmentManager: FragmentManager, fragment: Fragment,
			addToBackStack: Boolean = false) {
		val fragmentTransaction = supportFragmentManager.beginTransaction()
		if (addToBackStack) {
			fragmentTransaction.addToBackStack(fragment.javaClass.name)
		}
		fragmentTransaction.add(R.id.container, fragment, fragment.javaClass.name)
		fragmentTransaction.commitAllowingStateLoss()
	}
	
	override fun onDestroy() {
		super.onDestroy()
		disposable.dispose()
	}
	
}
