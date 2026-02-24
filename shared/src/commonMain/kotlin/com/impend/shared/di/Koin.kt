package com.impend.shared.di

import com.impend.shared.analytics.AnalyticsTransformer
import com.impend.shared.analytics.BehavioralAnalyticsEngine
import com.impend.shared.analytics.ImpulseRiskEngine
import com.impend.shared.analytics.InsightGenerator
import com.impend.shared.analytics.ResilienceEngine
import com.impend.shared.data.local.ImpendDatabase
import com.impend.shared.data.repository.CircleRepositoryImpl
import com.impend.shared.data.repository.ExpenseRepositoryImpl
import com.impend.shared.data.repository.PledgeRepositoryImpl
import com.impend.shared.domain.repository.CircleRepository
import com.impend.shared.domain.repository.ExpenseRepository
import com.impend.shared.domain.repository.PledgeRepository
import com.impend.shared.domain.usecase.AddExpenseUseCase
import com.impend.shared.social.SocialRelay
import com.impend.shared.util.AnonymousIdProvider
import com.impend.shared.domain.repository.PreferencesRepository
import com.impend.shared.data.repository.PreferencesRepositoryImpl
import com.russhwolf.settings.Settings
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(
    appModule: Module = module { },
    platformModule: Module = module { },
    appDeclaration: KoinAppDeclaration = { }
) {
    if (GlobalContext.getOrNull() == null) {
        val koinApp = startKoin {
            appDeclaration()
            modules(
                sharedModule,
                platformModule,
                networkModule,
                appModule
            )
        }
        seedCircles(koinApp.koin.get<ImpendDatabase>())
    }
}

val sharedModule = module {
    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }
    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }
    single<PledgeRepository> { PledgeRepositoryImpl(get()) }
    single<CircleRepository> { CircleRepositoryImpl(get(), get()) }
    factory { AddExpenseUseCase(get()) }
    
    single<SocialRelay> { SocialRelay(get(), get()) }
    single { ImpulseRiskEngine() }
    single<BehavioralAnalyticsEngine> { BehavioralAnalyticsEngine() }
    single<AnalyticsTransformer> { AnalyticsTransformer() }
    single<AnonymousIdProvider> { AnonymousIdProvider(get()) }
    single<InsightGenerator> { InsightGenerator() }
    single<ResilienceEngine> { ResilienceEngine(get()) }
}

fun seedCircles(database: ImpendDatabase) {
    val queries = database.circleEntityQueries
    if (queries.getAllCircles().executeAsList().isEmpty()) {
        queries.insertCircle("c1", "Latte Quitters", "Resisting high-frequency caffeine impulse spending.", 1240, 0.82, 0)
        queries.insertCircle("c2", "Weekend Chillers", "Zero shopping during the weekend. Pure resilience.", 850, 0.75, 0)
        queries.insertCircle("c3", "Tech Fasters", "Breaking the cycle of gadget impulse buys.", 420, 0.91, 0)
    }
}
