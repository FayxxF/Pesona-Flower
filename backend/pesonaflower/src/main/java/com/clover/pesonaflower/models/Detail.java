package com.clover.pesonaflower.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "detail")
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //kalo error ganti ke auto
    private String nama_bunga;
    private String foto_bunga;
    private String detail_bunga;
    private String deskripsi_bunga;
    private String merawat_bunga;
}
