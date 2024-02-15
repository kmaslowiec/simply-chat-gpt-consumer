package com.example.simplychatgptapiconsumer.common.di

import com.example.simplychatgptapiconsumer.BuildConfig
import com.example.simplychatgptapiconsumer.common.api.ChatGptApi
import com.example.simplychatgptapiconsumer.common.constant.BASE_URL
import com.example.simplychatgptapiconsumer.common.constant.CONNECT_TIMEOUT
import com.example.simplychatgptapiconsumer.common.constant.READ_TIMEOUT
import com.example.simplychatgptapiconsumer.common.constant.WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideQuestionApi(): ChatGptApi {
        val client = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
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
