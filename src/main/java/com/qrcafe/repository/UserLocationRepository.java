package com.qrcafe.repository;

import com.qrcafe.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
  List<UserLocation> getUserLocationsByUser_Username(String user_username);
  UserLocation getUserLocationById(Long id);
}
