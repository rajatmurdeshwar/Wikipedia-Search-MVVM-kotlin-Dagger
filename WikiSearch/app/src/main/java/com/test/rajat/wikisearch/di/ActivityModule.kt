package com.test.rajat.wikisearch.di

import com.test.rajat.wikisearch.view.MainActivity
import com.test.rajat.wikisearch.view.WikiPageActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributePageActivity(): WikiPageActivity
}