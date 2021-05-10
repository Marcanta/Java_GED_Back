package com.marcanta.ged.repositories;

import com.marcanta.ged.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
