package com.clover.pesonaflower.dto;

import com.clover.pesonaflower.models.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FaqDto {
    private Long id;
    private String question;
//    @NotEmpty(message = "Pertanyaan tidak boleh kosong")
    private String answer;
//    @NotEmpty(message = "Jawaban tidak boleh kosong")

    private UserEntity createdBy;
}
