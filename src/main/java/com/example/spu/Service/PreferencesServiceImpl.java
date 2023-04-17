package com.example.spu.Service;

import com.example.spu.Dto.PreferencesDto;
import com.example.spu.Repository.PreferencesRepository;
import com.example.spu.Repository.UserRepository;
import com.example.spu.model.Preferences;
import com.example.spu.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferencesServiceImpl implements PreferencesService{

    private final UserRepository userRepository;
    private final PreferencesRepository preferencesRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public PreferencesDto searchById(Long id) {
        Preferences preferences = preferencesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());

        return modelMapper.map(preferences, PreferencesDto.class);
    }


    @Override
    @Transactional
    public void savePreferences(Long id, PreferencesDto preferencesDto) {
        Preferences preferences = preferencesRepository.save(preferencesDto.toEntity());

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        user.setPreferences(preferences);
    }

    @Override
    @Transactional
    public void updatePreferences(Long id, PreferencesDto preferencesDto) {
        Preferences preferences = preferencesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        preferences.setFavorite(preferencesDto.getFavorite());
        preferences.setLessFavorite(preferencesDto.getLessFavorite());
        preferences.setNormal(preferencesDto.getNormal());
        preferences.setDislike(preferences.getDislike());
    }
}
