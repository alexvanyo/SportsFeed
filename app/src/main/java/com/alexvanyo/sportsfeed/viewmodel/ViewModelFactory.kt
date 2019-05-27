package com.alexvanyo.sportsfeed.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface ViewModelFactory {
    fun create(handle: SavedStateHandle): ViewModel
}