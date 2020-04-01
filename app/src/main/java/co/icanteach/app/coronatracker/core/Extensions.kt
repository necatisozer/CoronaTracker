package co.icanteach.app.coronatracker.core

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * http://kotlinextensions.com/
 */
fun <T : ViewDataBinding> ViewGroup?.inflate(
    @LayoutRes layoutId: Int,
    attachToParent: Boolean = true
): T {
    return DataBindingUtil.inflate(
        LayoutInflater.from(this!!.context),
        layoutId,
        this,
        attachToParent
    )
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    Glide.with(context).load(url).into(this)
}

inline fun <T> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    crossinline observer: (t: T) -> Unit
) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

inline fun <T> apiCall(crossinline call: suspend () -> T): Flow<Resource<T>> {
    return flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(call()))
        } catch (exception: Exception) {
            emit(Resource.Error(exception))
        }
    }
}