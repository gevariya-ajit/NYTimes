package nytimes.example.app.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseViewModel<T> : ViewModel() {

    var disposable = CompositeDisposable()
    protected val statePublisher = PublishSubject.create<T>()

    /**
     * Get way to communicate between view model to view/fragment
     */
    fun getState() = statePublisher
    
    fun publish(state: T) {
        statePublisher.onNext(state)
    }

}
