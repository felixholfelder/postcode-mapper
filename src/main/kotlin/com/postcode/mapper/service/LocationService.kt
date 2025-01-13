package com.postcode.mapper.com.postcode.mapper.service

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.postcode.mapper.com.postcode.mapper.model.Location
import org.springframework.stereotype.Service
import java.io.File


@Service
class LocationService {
  fun getLocations(queryString: String): List<Location> {
    val reader = csvReader { delimiter = ';' }.readAll(File("plz.csv"))
    val list: List<Location> = reader.map { row -> Location(row[0], row[1], row[2], row[3]) }
    val queryUpper = queryString.uppercase()

    fun sortLocations(locations: List<Location>, getKey: (Location) -> String): List<Location> {
      return locations.sortedWith { a, b ->
        val aUpper = getKey(a).uppercase()
        val bUpper = getKey(b).uppercase()

        when {
          aUpper == queryUpper && bUpper != queryUpper -> -1
          bUpper == queryUpper && aUpper != queryUpper -> 1
          aUpper.startsWith(queryUpper) && !bUpper.startsWith(queryUpper) -> -1
          bUpper.startsWith(queryUpper) && !aUpper.startsWith(queryUpper) -> 1
          else -> aUpper.compareTo(bUpper)
        }
      }
    }

    var filtered = list.filter { it.postcode.uppercase().contains(queryUpper) }

    if (filtered.isEmpty()) {
      filtered = list.filter { it.city.uppercase().contains(queryUpper) }
      return sortLocations(filtered, Location::city)
    }

    return sortLocations(filtered, Location::postcode)
  }
}
