package com.andreasoftware.keuanganku.common.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ExpenseCategoryDaoQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IncomeCategoryDaoQualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ExpenseDaoQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IncomeDaoQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WalletDaoQualifier