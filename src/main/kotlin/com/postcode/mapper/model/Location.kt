package com.postcode.mapper.com.postcode.mapper.model

import java.util.*

data class Location(
  val id: UUID?,
  val postcode: String?,
  val city: String?,
  val lat: String?,
  val lng: String?,
)
