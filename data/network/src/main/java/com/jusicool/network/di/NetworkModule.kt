package com.jusicool.network.di

import android.util.Log
import com.jusicool.network.BuildConfig
import com.jusicool.network.api.AccountApi
import com.jusicool.network.api.AuthApi
import com.jusicool.network.api.CryptoApi
import com.jusicool.network.api.HoldingApi
import com.jusicool.network.api.KoreaInvestmentApi
import com.jusicool.network.api.MarketApi
import com.jusicool.network.api.OrderApi
import com.jusicool.network.util.BaseApiRetrofit
import com.jusicool.network.util.BasicCookieJar
import com.jusicool.network.util.KoreaInvestmentAuthManager
import com.jusicool.network.util.KoreaInvestmentInterceptor
import com.jusicool.network.util.KoreaInvestmentRetrofit
import com.jusicool.network.util.UpbitRetrofit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT = 30L

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.v("Http", message) }.apply {
            level = if (/*BuildConfig.DEBUG*/ true) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cookieJar(BasicCookieJar())
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Provides
    @Singleton
    @Named("koreaInvestmentOkHttpClient")
    fun provideKoreaInvestmentOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        koreaInvestmentAuthenticator: KoreaInvestmentInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(koreaInvestmentAuthenticator)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()


    @Provides
    @Singleton
    fun provideKoreaInvestmentAuthenticator(
        authManager: dagger.Lazy<KoreaInvestmentAuthManager>  // <- Lazy 주입
    ): KoreaInvestmentInterceptor {
        return KoreaInvestmentInterceptor(authManager)
    }

    @Provides
    @Singleton
    fun provideCookieJar(): CookieJar {
        return BasicCookieJar()
    }

    @Provides
    @Singleton
    fun provideMoshiInstance(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    @BaseApiRetrofit
    fun provideBaseApiRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()

    @Provides
    @Singleton
    @UpbitRetrofit
    fun provideUpbitRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.upbit.com")
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()

    @Provides
    @Singleton
    @KoreaInvestmentRetrofit
    fun provideKoreaInvestmentRetrofit(
        @Named("koreaInvestmentOkHttpClient") okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.KOREAINVESTMENT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()

    @Provides
    fun provideAuthApi(@BaseApiRetrofit retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    fun provideAccountApi(@BaseApiRetrofit retrofit: Retrofit): AccountApi =
        retrofit.create(AccountApi::class.java)

    @Provides
    fun provideHoldingApi(@BaseApiRetrofit retrofit: Retrofit): HoldingApi =
        retrofit.create(HoldingApi::class.java)

    @Provides
    fun provideMarketApi(@BaseApiRetrofit retrofit: Retrofit): MarketApi =
        retrofit.create(MarketApi::class.java)

    @Provides
    fun provideCryptoApi(@UpbitRetrofit retrofit: Retrofit): CryptoApi =
        retrofit.create(CryptoApi::class.java)

    @Provides
    fun provideOrderApi(@BaseApiRetrofit retrofit: Retrofit): OrderApi =
        retrofit.create(OrderApi::class.java)

    @Provides
    fun provideKoreaInvestmentApi(@KoreaInvestmentRetrofit retrofit: Retrofit): KoreaInvestmentApi =
        retrofit.create(KoreaInvestmentApi::class.java)
}