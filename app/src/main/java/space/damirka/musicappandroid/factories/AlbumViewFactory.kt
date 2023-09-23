package space.damirka.musicappandroid.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import space.damirka.musicappandroid.viewmodels.AlbumViewModel

class AlbumViewFactory(private val id: Int) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = AlbumViewModel(id) as T
}