package com.clover.pesonaflower.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "detail")

public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama_bunga;
    private String foto_bunga;
    private String detail_bunga;
    private String deskripsi_bunga;
    private String harga_bunga;

    @Transient
    private MultipartFile fileFoto_bunga;

}
