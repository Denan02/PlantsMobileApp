package com.example.spirala1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object BiljkeRepository {
    suspend fun getBiljke(id:String, token: String, latinskiNazivBiljke:String) : VraceneBiljke?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getBiljkeLatinski(id, token,latinskiNazivBiljke)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
    suspend fun getBiljkePoID(id:Int, token: String) : SveDetaljnijeBiljke?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getBiljkeID(id, token)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
    suspend fun getBiljkeSubString(token: String, q: String, boja:String) : VraceneBiljke?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getBiljkeSubString(token, q, boja)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

}