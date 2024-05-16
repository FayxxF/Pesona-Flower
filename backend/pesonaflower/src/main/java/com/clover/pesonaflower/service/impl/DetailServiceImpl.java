package com.clover.pesonaflower.service.impl;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.models.Detail;
import com.clover.pesonaflower.repository.DetailRepository;
import com.clover.pesonaflower.service.DetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DetailServiceImpl implements DetailService {
    private DetailRepository detailRepository;

    public DetailServiceImpl(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    @Override
    public List<DetailDto> findAllDetail() {
        List<Detail> details = detailRepository.findAll();
        return details.stream().map((detail) -> mapToDetailsDto(detail)).collect(Collectors.toList());
    }

    private DetailDto mapToDetailsDto(Detail detail){
        DetailDto detailDto = DetailDto.builder()
                .nama_bunga(detail.getNama_bunga())
                .foto_bunga(detail.getFoto_bunga())
                .detail_bunga(detail.getDetail_bunga())
                .deskripsi_bunga(detail.getDeskripsi_bunga())
                .merawat_bunga(detail.getMerawat_bunga())
                .build();
        return detailDto;

    }
}
