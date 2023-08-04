package com.postcode.mapper.com.postcode.mapper.repository

import com.postcode.mapper.com.postcode.mapper.entity.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LocationRepository : JpaRepository<LocationEntity, String> {

  @Query("select * from location where postcode like :query", nativeQuery = true)
  fun findByPostcode(query: String): List<LocationEntity>

  @Query("select * from location where city like :query", nativeQuery = true)
  fun findByCity(query: String): List<LocationEntity>
}