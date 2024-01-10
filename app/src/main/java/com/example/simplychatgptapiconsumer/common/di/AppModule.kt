package com.example.simplychatgptapiconsumer.common.di

import com.example.simplychatgptapiconsumer.BuildConfig
import com.example.simplychatgptapiconsumer.common.api.ChatGptApi
import com.example.simplychatgptapiconsumer.common.constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideQuestionApi(): ChatGptApi {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.CHATGPT_API_KEY}")
                    .header("OpenAI-Organization", BuildConfig.CHATGPT_ORG_ID)
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatGptApi::class.java)
    }
}
