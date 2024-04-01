package com.ssafy.sungchef.data.di

import com.ssafy.sungchef.BuildConfig
import com.ssafy.sungchef.data.api.CookingService
import com.ssafy.sungchef.data.api.RecipeService
import com.ssafy.sungchef.data.api.RecommendationService
import com.ssafy.sungchef.data.api.RefrigeratorService
import com.ssafy.sungchef.data.api.TokenService
import com.ssafy.sungchef.data.api.UserService
import com.ssafy.sungchef.util.AuthAuthenticator
import com.ssafy.sungchef.util.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthRetrofit

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideInterceptorOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        authenticator: AuthAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(authenticator)
            .build()
    }

    @Singleton
    @Provides
    @MainRetrofit
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @AuthRetrofit
    fun provideAuthRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideRecommendationService(@MainRetrofit retrofit: Retrofit): RecommendationService =
        retrofit.create(RecommendationService::class.java)

    @Provides
    @Singleton
    fun provideUserService(@MainRetrofit retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideRecipeService(@MainRetrofit retrofit: Retrofit): RecipeService =
        retrofit.create(RecipeService::class.java)

    @Provides
    @Singleton
    fun provideFridgeService(@MainRetrofit retrofit: Retrofit): CookingService =
        retrofit.create(CookingService::class.java)

    @Provides
    @Singleton
    fun provideRefrigeratorService(@MainRetrofit retrofit: Retrofit) : RefrigeratorService =
        retrofit.create(RefrigeratorService::class.java)

    @Provides
    @Singleton
    fun provideTokenService(@AuthRetrofit retrofit: Retrofit) : TokenService =
        retrofit.create(TokenService::class.java)
}