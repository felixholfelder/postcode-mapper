package com.postcode.mapper.com.postcode.mapper.service

import com.postcode.mapper.com.postcode.mapper.entity.LocationEntity
import com.postcode.mapper.com.postcode.mapper.model.Location
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service


@Service
class LocationService(val mongoOperations: MongoOperations) {
  val CASE_INSENSITIVE = "i";
  fun getLocations(queryString: String): List<Location> {
    var query = Query().addCriteria(Criteria.where("postcode").regex(toLikeRegex(queryString), CASE_INSENSITIVE))
    var locationEntities: List<LocationEntity> = mongoOperations.find(query, LocationEntity::class.java)
    if (locationEntities.isEmpty()) {
      query = Query().addCriteria(Criteria.where("city").regex(toLikeRegex(queryString), CASE_INSENSITIVE))
      locationEntities = mongoOperations.find(query, LocationEntity::class.java)
    }
    return locationEntities.map { it.toModel() }.toList()
  }

  private fun toLikeRegex(source: String): String {
    return source.replace("\\*".toRegex(), ".*")
  }
}
