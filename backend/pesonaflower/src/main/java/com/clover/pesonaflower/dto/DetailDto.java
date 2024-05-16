package com.clover.pesonaflower.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailDto {
    private String nama_bunga;
    private String foto_bunga;
    private String detail_bunga;
    private String deskripsi_bunga;
    private String merawat_bunga;
}
