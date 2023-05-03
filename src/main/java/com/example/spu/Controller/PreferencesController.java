package com.example.spu.Controller;

import com.example.spu.Dto.PreferencesDto;
import com.example.spu.Service.PreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferences")
public class PreferencesController {

    @Autowired
    private PreferencesService preferencesService;

    @GetMapping("/{id}")
    public ResponseEntity<PreferencesDto> searchById(@PathVariable Long id) {
        PreferencesDto preferencesDto = preferencesService.searchById(id);
        return ResponseEntity.ok(preferencesDto);
    }

    /**
     *
     * @param id spu user primary key
     * @param preferencesDto
     */
    @PostMapping("/save/{id}")
    public void savePreferences(@PathVariable Long id, @RequestBody PreferencesDto preferencesDto) {
        PreferencesDto preferencesDto1 = preferencesDto;
        preferencesService.savePreferences(id, preferencesDto);
    }

    /**
     *
     * @param id 수정할 preferences primary key
     * @param preferencesDto
     */
    @PutMapping("/update/{id}")
    public void updatePreferences(@PathVariable Long id, @RequestBody PreferencesDto preferencesDto) {
        preferencesService.updatePreferences(id, preferencesDto);
    }

}
