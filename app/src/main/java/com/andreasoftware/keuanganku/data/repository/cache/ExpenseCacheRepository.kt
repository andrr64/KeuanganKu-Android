package com.andreasoftware.keuanganku.data.repository.cache

import com.andreasoftware.keuanganku.data.dao.cache.ExpenseCacheDS
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseCacheRepository
@Inject constructor(expenseCache: ExpenseCacheDS)