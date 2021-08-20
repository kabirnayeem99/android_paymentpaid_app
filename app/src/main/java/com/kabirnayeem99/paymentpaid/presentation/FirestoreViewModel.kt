package com.kabirnayeem99.paymentpaid.presentation

import androidx.lifecycle.*
import com.kabirnayeem99.paymentpaid.domain.models.Work
import com.kabirnayeem99.paymentpaid.domain.repositories.WorkRepository


class FirestoreViewModel(private val workRepository: WorkRepository) : ViewModel() {

    private val _workList = workRepository.getWorksList()
    val workList: LiveData<List<Work>> = _workList
    private val _paymentListByMonth = workRepository.getPaymentListByMonth()
    val paymentListByMonth: LiveData<List<Long>> = _paymentListByMonth
    private val _paymentOfCurrentYear = workRepository.getTotalPaymentsByYear()
    val paymentOfCurrentYear: LiveData<Long> = _paymentOfCurrentYear


    fun saveWork(work: Work) {
        workRepository.saveWork(work)
    }

    fun delete(work: Work) {
        workRepository.deleteWork(work)
    }


}