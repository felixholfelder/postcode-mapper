package com.postcode.mapper.com.postcode.mapper.entity

import com.postcode.mapper.com.postcode.mapper.model.Location
import jakarta.persistence.*
import java.util.*

@Entity(name = "LOCATION")
@Table(name = "LOCATION", schema = "public")
data class LocationEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  val id: UUID?,

  @Column(name = "postcode")
  val postcode: String?,

  @Column(name = "city")
  val city: String?,

  @Column(name = "lat")
  val lat: String?,

  @Column(name = "lng")
  val lng: String?,
) {

  constructor() : this(null, null, null, null, null)
  fun toModel(): Location = Location(id, postcode, city, lat, lng)
}
