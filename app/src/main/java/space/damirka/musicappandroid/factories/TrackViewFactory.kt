package space.damirka.musicappandroid.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import space.damirka.musicappandroid.viewmodels.TrackViewModel

class TrackViewFactory(private val id: Int) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = TrackViewModel(id) as T
}