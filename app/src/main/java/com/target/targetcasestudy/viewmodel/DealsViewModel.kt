package com.target.targetcasestudy.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.target.targetcasestudy.data.repository.DealsRepository
import com.target.targetcasestudy.model.Deals
import com.target.targetcasestudy.model.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DealsViewModel @ViewModelInject constructor(private val repository: DealsRepository) :
    ViewModel() {

    private val _postsLiveData = MutableLiveData<State<Deals>>()

    val postsLiveData: LiveData<State<Deals>>
        get() = _postsLiveData

    /**
     * This function retrieve all deals
     */
    fun getAllDeals() {
        viewModelScope.launch {
            repository.getAllDeals().collect {
                _postsLiveData.value = it
            }
        }
    }

    /**
     * This function retrieve selected deal data
     */
    fun getDetailedDealsData(id: String) =
        repository.getDetailedRecipeData(id).asLiveData()
}
