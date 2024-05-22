package com.clover.pesonaflower.dto;


import com.clover.pesonaflower.models.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class DetailDto {
    private Long id;
    @NotEmpty(message = "Nama Bunga tidak boleh kosong")
    private String nama_bunga;
    private String foto_bunga;
    @NotEmpty(message = "Detail Bunga tidak boleh kosong")
    private String detail_bunga;
    @NotEmpty(message = "Deskripsi Bunga tidak boleh kosong")
    private String deskripsi_bunga;
    @NotEmpty(message = "Harga Bunga tidak boleh kosong")
    private String harga_bunga;
    private UserEntity createdBy;
    @Transient
    private MultipartFile fileFoto_bunga;
}
