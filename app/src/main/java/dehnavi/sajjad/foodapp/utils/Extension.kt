package dehnavi.sajjad.foodapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers


//Rxjava
fun <T : Any> Single<T>.applyScheduler(scheduler: Scheduler): Single<T> =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Single<T>.applySchedulerIo(): Single<T> = applyScheduler(Schedulers.io())

fun <T : Any> Observable<T>.applySchedulerObservable(scheduler: Scheduler): Observable<T> =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Observable<T>.applySchedulerObservableIo(): Observable<T> =
    applySchedulerObservable(Schedulers.io())


fun Completable.applySchedulerCompletable(scheduler: Scheduler): Completable =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

fun Completable.applySchedulerCompletableIo(): Completable =
    applySchedulerCompletable(Schedulers.io())

//Check Internet
fun Context.iNetworkAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = cm.activeNetworkInfo
    return info != null && info.isConnected
}

//SnackBar
fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}