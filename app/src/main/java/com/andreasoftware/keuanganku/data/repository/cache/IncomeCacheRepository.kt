package com.andreasoftware.keuanganku.data.repository.cache

import com.andreasoftware.keuanganku.data.dao.cache.IncomeCacheDS
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IncomeCacheRepository
@Inject constructor(incomeCache: IncomeCacheDS)