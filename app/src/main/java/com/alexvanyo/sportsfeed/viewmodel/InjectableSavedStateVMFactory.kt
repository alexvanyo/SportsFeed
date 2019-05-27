package com.alexvanyo.sportsfeed.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateVMFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Custom ViewModelProvider.Factory that support Dagger injection.
 * @see <a href="https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/viewmodel/GithubViewModelFactory.kt">based on (Apache license)<a/>
 */
@Singleton
@AutoFactory
class InjectableSavedStateVMFactory(
    @Provided private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModelFactory>>,
    savedStateRegistryOwner: SavedStateRegistryOwner,
    defaultBundle: Bundle?
) : AbstractSavedStateVMFactory(savedStateRegistryOwner, defaultBundle) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get().create(handle) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
