package com.postcode.mapper.com.postcode.mapper.service

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.postcode.mapper.com.postcode.mapper.model.Location
import org.springframework.stereotype.Service
import java.io.File


@Service
class LocationService {
  fun getLocations(queryString: String): List<Location> {
    val reader = csvReader { delimiter = ';' }.readAll(File("plz.csv"))

    val list: List<Location> = reader
      .map { row -> Location(row[0], row[1], row[2], row[3]) }

    var filtered: List<Location> = list.filter { it.postcode.contains(queryString) }
    if (filtered.isEmpty())
      filtered = list.filter { it.city.uppercase().contains(queryString.uppercase()) }

    val t = filtered.sortedWith { a, b ->
      val aCityUpper = a.city.uppercase()
      val bCityUpper = b.city.uppercase()
      val queryUpper = queryString.uppercase()

      when {
        aCityUpper == queryUpper && bCityUpper != queryUpper -> -1
        bCityUpper == queryUpper && aCityUpper != queryUpper -> 1
        aCityUpper.startsWith(queryUpper) && !bCityUpper.startsWith(queryUpper) -> -1
        bCityUpper.startsWith(queryUpper) && !aCityUpper.startsWith(queryUpper) -> 1
        else -> aCityUpper.compareTo(bCityUpper)
      }
    }

    return t
  }
}
