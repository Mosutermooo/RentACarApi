package com.example.db

import com.example.utils.Constants.dbUrl
import com.example.utils.Constants.driver
import com.example.utils.Constants.user
import com.example.utils.Constants.password
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.ktorm.database.Database
import org.ktorm.dsl.insert

object Database {
    fun connect(): Database {
         return Database.connect(
            url = dbUrl,
            driver = driver,
            user = user,
            password = password
        )
    }



}