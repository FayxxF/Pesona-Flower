package com.clover.pesonaflower.service.impl;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.models.Detail;
import com.clover.pesonaflower.models.UserEntity;
import com.clover.pesonaflower.repository.DetailRepository;
import com.clover.pesonaflower.repository.UserRepository;
import com.clover.pesonaflower.security.SecurityUtil;
import com.clover.pesonaflower.service.DetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DetailServiceImpl implements DetailService {
    private DetailRepository detailRepository;
    private UserRepository userRepository;

    public DetailServiceImpl(DetailRepository detailRepository, UserRepository userRepository) {
        this.detailRepository = detailRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<DetailDto> findAllDetail() {
        List<Detail> details = detailRepository.findAll();
        return details.stream().map((detail) -> mapToDetailsDto(detail)).collect(Collectors.toList());
    }

    @Override
    public Detail saveDetail(DetailDto detailDto) { //saving detail yg ditambahin
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Detail detail = mapToDetail(detailDto);
        detail.setCreatedBy(user);
        return detailRepository.save(detail);
    }

    @Override
    public DetailDto findDetailById(Long detailId) {
        Detail detail = detailRepository.findById(detailId).get();
        return mapToDetailsDto(detail);
    }

    @Override
    public void updateDetail(DetailDto detailDto) { //updating, instansiasi maptodetail
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Detail detail = mapToDetail(detailDto);
        detail.setCreatedBy(user);
        detailRepository.save(detail);
    }

    @Override
    public void delete(long detailId) {
        detailRepository.deleteById(detailId);
    }

    private Detail mapToDetail(DetailDto detail) { //mapping aka converting
        Detail detailDto = Detail.builder()
                .id(detail.getId())
                .nama_bunga(detail.getNama_bunga())
                .foto_bunga(detail.getFoto_bunga())
                .detail_bunga(detail.getDetail_bunga())
                .deskripsi_bunga(detail.getDeskripsi_bunga())
                .harga_bunga(detail.getHarga_bunga())
                .createdBy(detail.getCreatedBy())
                .build();
        return detailDto;
    }

    private DetailDto mapToDetailsDto(Detail detail){ //mapping aka converting
        DetailDto detailDto = DetailDto.builder()
                .id(detail.getId())
                .nama_bunga(detail.getNama_bunga())
                .foto_bunga(detail.getFoto_bunga())
                .detail_bunga(detail.getDetail_bunga())
                .deskripsi_bunga(detail.getDeskripsi_bunga())
                .harga_bunga(detail.getHarga_bunga())
                .createdBy(detail.getCreatedBy())
                .build();
        return detailDto;

    }
}
