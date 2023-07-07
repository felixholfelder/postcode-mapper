package com.postcode.mapper.web

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.WriteResult
import com.google.firebase.cloud.FirestoreClient
import com.postcode.mapper.com.postcode.mapper.entity.Location
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream

@RestController
@RequestMapping("/api/load")
class IniLoadController {

    @GetMapping
    fun loadCSV(): String {
        for (location in getLocationsFromFile()) {
            val dbFirestore: Firestore = FirestoreClient.getFirestore()
            val collectionsApiFuture: ApiFuture<WriteResult> = dbFirestore.collection("locations").document().set(location)
            collectionsApiFuture.get().getUpdateTime().toString()
        }
        return "done"
    }

    fun getLocationsFromFile(): List<Location> {
        val inputStream: InputStream = FileInputStream("plz.csv")
        val reader = inputStream.bufferedReader()
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val (postcode, city, lon, lat) = it.split(';', ignoreCase = false)
                Location(postcode, city, lat, lon)
            }.toList()
    }
}