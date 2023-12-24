package com.loc.newsapp.di

import android.app.Application
import androidx.room.Room
import com.loc.newsapp.data.local.NewsDao
import com.loc.newsapp.data.local.NewsDataBase
import com.loc.newsapp.data.local.NewsTypeConverter
import com.loc.newsapp.data.manager.LocalUserManagerImpl
import com.loc.newsapp.data.remote.NewsApi
import com.loc.newsapp.data.repository.NewsRepositoryImpl
import com.loc.newsapp.domain.manager.LocalUserManager
import com.loc.newsapp.domain.repository.NewsRepository
import com.loc.newsapp.domain.useCases.app_entry.AppEntryUseCases
import com.loc.newsapp.domain.useCases.app_entry.ReadAppEntry
import com.loc.newsapp.domain.useCases.app_entry.SaveAppEntry
import com.loc.newsapp.domain.useCases.news.DeleteArticle
import com.loc.newsapp.domain.useCases.news.GetNews
import com.loc.newsapp.domain.useCases.news.NewsUseCases
import com.loc.newsapp.domain.useCases.news.SearchNews
import com.loc.newsapp.domain.useCases.news.SelectArticle
import com.loc.newsapp.domain.useCases.news.SelectArticles
import com.loc.newsapp.domain.useCases.news.UpsertArticle
import com.loc.newsapp.ui.theme.Constants
import com.loc.newsapp.ui.theme.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserManagerImpl(application)


    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(
            localUserManager = localUserManager
        ), saveAppEntry = SaveAppEntry(
            localUserManager = localUserManager
        )
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsApi::class.java)


    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi,newsDao: NewsDao): NewsRepository =
        NewsRepositoryImpl(newsApi = newsApi, newsDao = newsDao )

    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository): NewsUseCases {
        return NewsUseCases(
            GetNews(newsRepository), SearchNews(newsRepository), UpsertArticle(newsRepository),
            DeleteArticle(newsRepository), SelectArticles(newsRepository), SelectArticle(newsRepository)
        )
    }


    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDataBase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDataBase::class.java,
            name = Constants.NEWS_DATABASE_NAME
        )
            .allowMainThreadQueries()
            .addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDataBase: NewsDataBase
    ): NewsDao = newsDataBase.newsDao
}

