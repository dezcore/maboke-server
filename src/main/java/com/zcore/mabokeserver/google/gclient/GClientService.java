package com.zcore.mabokeserver.google.gclient;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;

import lombok.RequiredArgsConstructor;

import com.google.api.services.drive.Drive;

@RequiredArgsConstructor
@Service
public class GClientService {
  private Logger logger = LoggerFactory.getLogger(GClientService.class);

  public Drive getService(String token) throws GeneralSecurityException, IOException {
    Drive service = null;
    NetHttpTransport HTTP_TRANSPORT;
    GoogleCredentials credentials;

    if(token != null) {
      credentials = GoogleCredentials.newBuilder().setAccessToken(new AccessToken(token, null)).build();
      HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      service = new Drive.Builder(HTTP_TRANSPORT, GsonFactory.getDefaultInstance(), new HttpCredentialsAdapter(credentials))
      .setApplicationName("Maboke/1.0")
      .build();
    }
    return service;
  }
}
