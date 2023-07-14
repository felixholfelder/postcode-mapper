package com.postcode.mapper.com.postcode.mapper.service

import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import com.postcode.mapper.com.postcode.mapper.entity.Location
import com.postcode.mapper.com.postcode.mapper.enums.QueryParam
import org.springframework.stereotype.Service

@Service
class LocationService {
  fun getLocations(query: String): List<Location> {
    val firestore: Firestore = FirestoreClient.getFirestore()
    var locations: List<Location> = searchFor(QueryParam.POSTCODE, query, firestore)
    if (locations.isEmpty()) {
      locations = searchFor(QueryParam.CITY, query, firestore)
    }
    return locations
  }

  private inline fun <reified T> searchFor(param: QueryParam, query: String, firestore: Firestore): List<T> {
    return firestore.collection("locations")
      .whereGreaterThanOrEqualTo(param.value, query)
      .whereLessThanOrEqualTo(param.value, "${query}\uf7ff")
      .get().get().toObjects(T::class.java)
  }
}
