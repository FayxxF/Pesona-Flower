package com.clover.pesonaflower.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailDto {
    private Long id;
    @NotEmpty(message = "Nama Bunga tidak boleh kosong")
    private String nama_bunga;
    @NotEmpty(message = "Foto Bunga tidak boleh kosong")
    private String foto_bunga;
    @NotEmpty(message = "Detail Bunga tidak boleh kosong")
    private String detail_bunga;
    @NotEmpty(message = "Deskripsi Bunga tidak boleh kosong")
    private String deskripsi_bunga;
    @NotEmpty(message = "Cara Merawat Bunga tidak boleh kosong")
    private String merawat_bunga;
    @NotEmpty(message = "Harga Bunga tidak boleh kosong")
    private String harga_bunga;
}
