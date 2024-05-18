package com.clover.pesonaflower.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GaleriDto {
    private Long id;
    private String nama;
//    @NotEmpty(message = "Nama Bunga tidak boleh kosong")
    private String foto;
//    @NotEmpty(message = "Foto Bunga tidak boleh kosong")
}
