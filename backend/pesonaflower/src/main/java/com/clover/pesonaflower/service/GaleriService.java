package com.clover.pesonaflower.service;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.dto.GaleriDto;
import com.clover.pesonaflower.models.Detail;
import com.clover.pesonaflower.models.Galeri;

import java.util.List;

public interface GaleriService {
    List<GaleriDto> findAllGaleri();
    Galeri saveGaleri(GaleriDto galeriDto);
    GaleriDto findGaleriById(Long galeriId);

    void updateGaleri(GaleriDto galeri);

    void delete(long galeriId);
}
