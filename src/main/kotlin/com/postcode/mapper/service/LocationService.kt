package com.postcode.mapper.com.postcode.mapper.service

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.postcode.mapper.com.postcode.mapper.model.Location
import org.springframework.stereotype.Service
import java.io.File


@Service
class LocationService {
  fun getLocations(queryString: String): List<Location> {
    val reader = csvReader { delimiter = ';' }.readAll(File("plz.csv"))
    var t: List<Location>

    val list: List<Location> = reader
      .map { row -> Location(row[0], row[1], row[2], row[3]) }

    var filtered: List<Location> = list.filter { it.postcode.contains(queryString) }
    t = filtered.sortedWith { a, b ->
      val aUpper = a.postcode.uppercase()
      val bUpper = b.postcode.uppercase()
      val queryUpper = queryString.uppercase()

      when {
        aUpper == queryUpper && bUpper != queryUpper -> -1
        bUpper == queryUpper && aUpper != queryUpper -> 1
        aUpper.startsWith(queryUpper) && !bUpper.startsWith(queryUpper) -> -1
        bUpper.startsWith(queryUpper) && !aUpper.startsWith(queryUpper) -> 1
        else -> aUpper.compareTo(bUpper)
      }
    }

    if (filtered.isEmpty()) {
      filtered = list.filter { it.city.uppercase().contains(queryString.uppercase()) }
      t = filtered.sortedWith { a, b ->
        val aUpper = a.city.uppercase()
        val bUpper = b.city.uppercase()
        val queryUpper = queryString.uppercase()

        when {
          aUpper == queryUpper && bUpper != queryUpper -> -1
          bUpper == queryUpper && aUpper != queryUpper -> 1
          aUpper.startsWith(queryUpper) && !bUpper.startsWith(queryUpper) -> -1
          bUpper.startsWith(queryUpper) && !aUpper.startsWith(queryUpper) -> 1
          else -> aUpper.compareTo(bUpper)
        }
      }
    }

    return t
  }
}
