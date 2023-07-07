package com.postcode.mapper.web

import com.postcode.mapper.com.postcode.mapper.entity.Location
import com.postcode.mapper.com.postcode.mapper.service.LocationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/locations")
class LocationController(val locationService: LocationService) {
  @GetMapping
  fun getLocations(@RequestParam query: String): MutableList<Location> {
    return locationService.getLocations(query)
  }
}