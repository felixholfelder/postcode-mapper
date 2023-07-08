package com.postcode.mapper.service

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.FileInputStream

@Service
class FirebaseInitializer {
  @Value("\${db.url}")
  val dbUrl: String = "";

  @PostConstruct
  fun initialize() {
    try {
      val serviceAccount = FileInputStream("./postcode-mapper.json")
      val options: FirebaseOptions = FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl(dbUrl)
        .build()
      FirebaseApp.initializeApp(options)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }
}