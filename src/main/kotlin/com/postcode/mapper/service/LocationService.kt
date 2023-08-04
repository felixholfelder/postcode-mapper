package com.postcode.mapper.com.postcode.mapper.service

import com.postcode.mapper.com.postcode.mapper.entity.LocationEntity
import com.postcode.mapper.com.postcode.mapper.model.Location
import com.postcode.mapper.com.postcode.mapper.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(val locationRepository: LocationRepository) {
  fun getLocations(query: String): List<Location> {
    val searchQuery = "%$query%"
    var locationEntities: List<LocationEntity> = locationRepository.findByPostcode(searchQuery)
    if (locationEntities.isEmpty()) {
      locationEntities = locationRepository.findByCity(searchQuery)
    }
    return locationEntities.map { it.toModel() }.toList()
  }
}
