package gladun.vlad.testme.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gladun.vlad.testme.BuildConfig
import gladun.vlad.testme.data.network.AuthInterceptor
import gladun.vlad.testme.data.network.CommonInterceptor
import gladun.vlad.testme.data.network.JsonAdapters
import gladun.vlad.testme.data.network.LatestListingService
import gladun.vlad.testme.utils.Constants
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Reusable
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(JsonAdapters())
        .build()

    @Provides
    @Reusable
    fun provideRetrofitBuilder(moshi: Moshi): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))

    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder = OkHttpClient
        .Builder()
        .cache(Cache(File(Constants.REQUEST_CACHE_FILE), Constants.REQUEST_CACHE_SIZE))

    @Provides
    @Reusable
    @UnAuthHttpClient
    fun provideUnAuthOkHttpClient(clientBuilder: OkHttpClient.Builder): OkHttpClient = clientBuilder
        .addInterceptor(CommonInterceptor())
        .build()

    @Provides
    @Reusable
    @AuthHttpClient
    fun provideAuthOkHttpClient(clientBuilder: OkHttpClient.Builder): OkHttpClient = clientBuilder
        .addInterceptor(AuthInterceptor())
        .build()

    @Provides
    @Reusable
    fun provideLatestListingService(
        retrofitBuilder: Retrofit.Builder,
        @AuthHttpClient okHttpClient: OkHttpClient
    ): LatestListingService {
        return retrofitBuilder
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(okHttpClient)
            .build()
            .create(LatestListingService::class.java)
    }
}

@Qualifier
annotation class AuthHttpClient

@Qualifier
annotation class UnAuthHttpClient