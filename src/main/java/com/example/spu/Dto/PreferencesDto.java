package com.example.spu.Dto;

import com.example.spu.model.Preferences;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreferencesDto {
    private Long id;
    private String favorite;
    private String lessFavorite;
    private String normal;
    private String dislike;

    @Builder
    public Preferences toEntity() {
        return Preferences.builder()
                .id(id)
                .favorite(favorite)
                .lessFavorite(lessFavorite)
                .normal(normal)
                .dislike(dislike)
                .build();
    }
}
