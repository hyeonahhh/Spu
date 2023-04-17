package com.example.spu.Repository;

import com.example.spu.model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesRepository extends JpaRepository<Preferences, Long> {

}
