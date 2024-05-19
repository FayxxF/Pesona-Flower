package com.clover.pesonaflower.service.impl;

import com.clover.pesonaflower.dto.FaqDto;
import com.clover.pesonaflower.models.Detail;
import com.clover.pesonaflower.models.Faq;
import com.clover.pesonaflower.models.UserEntity;
import com.clover.pesonaflower.repository.FaqRepository;
import com.clover.pesonaflower.repository.UserRepository;
import com.clover.pesonaflower.security.SecurityUtil;
import com.clover.pesonaflower.service.FaqService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaqServiceImpl implements FaqService {
    private FaqRepository faqRepository;
    private UserRepository userRepository;

    public FaqServiceImpl(FaqRepository faqRepository, UserRepository userRepository) {
        this.faqRepository = faqRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<FaqDto> findAllFaq() {
        List<Faq> faqs = faqRepository.findAll();
        return faqs.stream().map((faq) -> mapTofaqsDto(faq)).collect(Collectors.toList());
    }

    private FaqDto mapTofaqsDto(Faq faq) {
        FaqDto faqDto = FaqDto.builder()
                .id(faq.getId())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .createdBy(faq.getCreatedBy())
                .build();
        return faqDto;
    }

    @Override
    public Faq saveFaq(FaqDto faqDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Faq faq = mapToFaq(faqDto);
        faq.setCreatedBy(user);
        return faqRepository.save(faq);
    }

    private Faq mapToFaq(FaqDto faq) {
        Faq faqDto = Faq.builder()
                .id(faq.getId())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .createdBy(faq.getCreatedBy())
                .build();
        return faqDto;
    }

    @Override
    public FaqDto findFaqById(Long faqId) {
        Faq faq = faqRepository.findById(faqId).get();
        return mapTofaqsDto(faq);
    }

    @Override
    public void updateFaq(FaqDto faqDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Faq faq =  mapToFaq(faqDto);
        faq.setCreatedBy(user);
        faqRepository.save(faq);

    }

    @Override
    public void delete(long faqId) {
        faqRepository.deleteById(faqId);
    }
}
