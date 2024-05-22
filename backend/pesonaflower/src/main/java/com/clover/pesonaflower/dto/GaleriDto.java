package com.clover.pesonaflower.dto;

import com.clover.pesonaflower.models.UserEntity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class GaleriDto {
    private Long id;
    private String nama;
    @NotEmpty(message = "Nama Bunga tidak boleh kosong")
    private String foto;
//    @NotEmpty(message = "Foto Bunga tidak boleh kosong")

    @Transient
    private MultipartFile fileFoto;
}
