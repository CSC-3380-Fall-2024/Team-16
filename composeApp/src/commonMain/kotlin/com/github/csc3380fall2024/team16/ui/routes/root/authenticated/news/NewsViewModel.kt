package com.github.csc3380fall2024.team16.ui.routes.root.authenticated.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.csc3380fall2024.team16.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepo: NewsRepository, private val token: String) : ViewModel() {
	var error by mutableStateOf(false)
		private set
	
	fun fetch(vararg queries: String) = viewModelScope.launch {
		try {
			newsRepo.fetch(token, *queries)
			error = false
		} catch (e: Exception) {
			error = true
		}
	}
}
