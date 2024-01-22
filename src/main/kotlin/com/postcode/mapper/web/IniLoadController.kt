package com.postcode.mapper.web

import com.postcode.mapper.com.postcode.mapper.entity.LocationEntity
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.FileInputStream
import java.io.InputStream

@RestController
@RequestMapping("/api/load")
class IniLoadController(val mongoOperations: MongoOperations) {

  @PostMapping
  fun loadCSV(): String {
    getLocationsFromFile().forEach { mongoOperations.save(it) }
    return "done!"
  }

  fun getLocationsFromFile(): List<LocationEntity> {
    val inputStream: InputStream = FileInputStream("plz.csv")
    val reader = inputStream.bufferedReader()
    return reader.lineSequence()
      .filter { it.isNotBlank() }
      .map {
        val (postcode, city, lng, lat) = it.split(';', ignoreCase = false)
        LocationEntity(null, postcode, city, lat, lng)
      }.toList()
  }
}