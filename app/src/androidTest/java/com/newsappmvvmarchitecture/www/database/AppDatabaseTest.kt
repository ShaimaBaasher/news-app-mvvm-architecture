package com.newsappmvvmarchitecture.www.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.newsappmvvmarchitecture.data.database.AppDatabase
import com.newsappmvvmarchitecture.data.database.NewsDao
import com.newsappmvvmarchitecture.domain.core.NewsEntityDAO
import com.newsappmvvmarchitecture.domain.core.home.Results
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: NewsDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.newsDao()
    }

    @Test
    fun writeAndReadLocalNews() = runBlocking {
        val weatherResponseList = mutableListOf<Results>()
        weatherResponseList.add(
            Results(
                123456,
                "nyt://article/229225e8-e303-5a75-979a-c40da4de0756",
                "https://www.nytimes.com/2022/09/30/us/fort-myers-beach-hurricane-ian.html",
                "New York Times",
                "On Florida’s Islands, Scenes of Paradise Lost, Maybe for Good",
                "On Fort Myers Beach"
            )
        )

        val newsEntityDAO = NewsEntityDAO(0, "OK", 10, weatherResponseList)

        dao.insert(newsEntityDAO)

        val weathers = dao.loadAll()

        assertNotNull(weathers)
    }

    @After
    fun closeDb() {
        db.close()
    }

}