package com.postcode.mapper.com.postcode.mapper.service

import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QueryDocumentSnapshot
import com.google.firebase.cloud.FirestoreClient
import com.postcode.mapper.com.postcode.mapper.entity.Location
import com.postcode.mapper.com.postcode.mapper.enums.QueryParam
import org.springframework.stereotype.Service

@Service
class LocationService {
  fun getLocations(query: String): MutableList<Location> {
    val firestore: Firestore = FirestoreClient.getFirestore()
    var documents: List<QueryDocumentSnapshot> = searchFor(QueryParam.POSTCODE, query, firestore)
    if (documents.isEmpty()) {
      documents = searchFor(QueryParam.CITY, query, firestore)
    }
    val locations: MutableList<Location> = mutableListOf()
    for (document in documents) {
      locations.add(document.toObject(Location::class.java))
    }
    return locations
  }

  fun searchFor(param: QueryParam, query: String, firestore: Firestore): List<QueryDocumentSnapshot> {
    return firestore.collection("locations")
      .whereGreaterThanOrEqualTo(param.value, query)
      .whereLessThanOrEqualTo(param.value, "${query}\uf7ff")
      .get().get().documents
  }
}
