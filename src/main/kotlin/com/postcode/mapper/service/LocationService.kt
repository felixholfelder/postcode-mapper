package com.postcode.mapper.com.postcode.mapper.service

import com.postcode.mapper.com.postcode.mapper.entity.LocationEntity
import com.postcode.mapper.com.postcode.mapper.model.Location
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service


@Service
class LocationService(val mongoOperations: MongoOperations) {
  val CASE_INSENSITIVE = "i"
  fun getLocations(queryString: String): List<Location> {
    var query = Query().addCriteria(Criteria.where("postcode").regex(toLikeRegex(queryString), CASE_INSENSITIVE))
    var locationEntities: List<LocationEntity> = mongoOperations.find(query, LocationEntity::class.java).sortedBy { it.postcode }
    if (locationEntities.isEmpty()) {
      query = Query().addCriteria(Criteria.where("city").regex(toLikeRegex(queryString), CASE_INSENSITIVE))
      locationEntities = mongoOperations.find(query, LocationEntity::class.java)
        .sortedWith { a, b ->
          when {
            a.city == queryString -> -1   // "a" matches the search key exactly, so it comes first
            b.city!!.startsWith(queryString) -> 1    // "b" matches the search key exactly, so it comes after "a"
            else -> a.city!!.compareTo(b.city)  // If neither matches exactly, sort alphabetically
          }
        }
    }
    return locationEntities.map { it.toModel() }.toList()
  }

  private fun toLikeRegex(source: String): String = source.replace("\\*".toRegex(), ".*")
}
