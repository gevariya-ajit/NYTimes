package nytimes.example.app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import nytimes.example.app.R

object Utils {
	
	fun showNoConnectionToast(context: Context) {
		Toast
				.makeText(
						context,
						R.string.not_able_to_connect_to_server,
						Toast.LENGTH_SHORT)
				.show()
	}
	
	fun isNetworkAvailable(context: Context): Boolean {
		val connectivityManager =
			context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			val nw = connectivityManager.activeNetwork ?: return false
			val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
			return when {
				actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
				actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
				actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
				actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
				else -> false
			}
		} else {
			return connectivityManager.activeNetworkInfo?.isConnected ?: false
		}
	}
}