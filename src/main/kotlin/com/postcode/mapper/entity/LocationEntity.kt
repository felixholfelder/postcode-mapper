package com.postcode.mapper.com.postcode.mapper.entity

import com.postcode.mapper.com.postcode.mapper.model.Location
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "location")
data class LocationEntity(
  @Id
  val id: String?,

  @Indexed
  val postcode: String?,

  @Indexed
  val city: String?,

  @Indexed
  val lat: String?,

  @Indexed
  val lng: String?,
) {

  constructor() : this(null, null, null, null, null)
  fun toModel(): Location = Location(id, postcode, city, lat, lng)
}
