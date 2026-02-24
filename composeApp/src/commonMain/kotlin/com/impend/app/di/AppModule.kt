package com.impend.app.di

import com.impend.app.presentation.HomeViewModel
import com.impend.app.presentation.AddExpenseViewModel
import com.impend.app.presentation.PledgeViewModel
import com.impend.app.presentation.ProgressViewModel
import com.impend.app.presentation.CirclesViewModel
import com.impend.shared.analytics.AnalyticsTransformer
import com.impend.shared.domain.repository.CircleRepository
import com.impend.shared.domain.repository.ExpenseRepository
import com.impend.shared.domain.repository.PledgeRepository
import com.impend.shared.domain.usecase.AddExpenseUseCase
import com.impend.shared.analytics.ImpulseRiskEngine
import com.impend.shared.analytics.BehavioralAnalyticsEngine
import com.impend.shared.analytics.InsightGenerator
import com.impend.shared.analytics.ResilienceEngine
import com.impend.shared.domain.repository.PreferencesRepository
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val appModule = module {
    viewModel { 
        HomeViewModel(
            get<ExpenseRepository>(), 
            get<AddExpenseUseCase>(), 
            get<ImpulseRiskEngine>(), 
            get<BehavioralAnalyticsEngine>(), 
            get<InsightGenerator>(),
            get<CircleRepository>(),
            get<PreferencesRepository>()
        ) 
    }
    viewModel { 
        AddExpenseViewModel(
            get<ExpenseRepository>(),
            get<PledgeRepository>(),
            get<AddExpenseUseCase>(),
            get<ResilienceEngine>()
        ) 
    }
    viewModel { PledgeViewModel(get<PledgeRepository>()) }
    viewModel { ProgressViewModel(get<ExpenseRepository>(), get<PledgeRepository>(), get<AnalyticsTransformer>()) }
    viewModel { CirclesViewModel(get<CircleRepository>()) }
}
