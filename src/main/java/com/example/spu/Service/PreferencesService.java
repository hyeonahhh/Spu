package com.example.spu.Service;

import com.example.spu.Dto.PreferencesDto;

public interface PreferencesService {
    PreferencesDto searchById(Long id);
    void savePreferences(Long id, PreferencesDto preferencesDto);
    void updatePreferences(Long id, PreferencesDto preferencesDto);
}
