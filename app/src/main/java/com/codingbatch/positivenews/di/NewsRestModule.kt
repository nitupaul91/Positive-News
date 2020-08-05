package com.codingbatch.positivenews.di

import com.codingbatch.positivenews.data.remote.NewsRestInterface
import com.codingbatch.positivenews.data.remote.RetrofitFactory
import com.codingbatch.positivenews.util.Constants.URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class NewsRestModule {

    @Provides
    fun bindsNewsRestInterface(): NewsRestInterface {
        return RetrofitFactory.getRetrofitInstance(URL).create(
            NewsRestInterface::
            class.java
        )
    }
}