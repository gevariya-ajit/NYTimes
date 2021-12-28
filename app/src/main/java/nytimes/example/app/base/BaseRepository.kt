package nytimes.example.app.base

import android.util.Log
import io.reactivex.rxjava3.core.ObservableEmitter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRepository {
	
	open fun <T, E> getBaseCallback(
			emitter: ObservableEmitter<T>, responseListener: ((response: E) -> (Unit))
	                               ): Callback<E> {
		return object : Callback<E> {
			override fun onFailure(call: Call<E>, t: Throwable) {
				Log.e("error", t.message ?: "")
				emitter.onError(t)
			}
			
			override fun onResponse(call: Call<E>, response: Response<E>) {
				if (!response.isSuccessful) {
					// Make sure to avoid error delivery in case of observable disposed
					if (!emitter.isDisposed) {
						emitter.onError(Exception())
					}
				} else {
					// Make sure to avoid response delivery in case of observable disposed
					if (!emitter.isDisposed) {
						response
								.body()
								?.let {
									responseListener.invoke(it)
								} ?: run {
							emitter.onError(Exception("Empty body from API response."))
						}
					}
					
				}
			}
		}
	}
}
