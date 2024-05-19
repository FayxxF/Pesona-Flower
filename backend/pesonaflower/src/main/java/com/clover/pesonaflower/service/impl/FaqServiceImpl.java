package com.clover.pesonaflower.service.impl;

import com.clover.pesonaflower.dto.FaqDto;
import com.clover.pesonaflower.models.Faq;
import com.clover.pesonaflower.repository.FaqRepository;
import com.clover.pesonaflower.service.FaqService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaqServiceImpl implements FaqService {
    private FaqRepository faqRepository;

    public FaqServiceImpl(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
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
                .build();
        return faqDto;
    }

    @Override
    public Faq saveFaq(FaqDto faqDto) {
        Faq faq = mapToFaq(faqDto);
        return faqRepository.save(faq);
    }

    private Faq mapToFaq(FaqDto faq) {
        Faq faqDto = Faq.builder()
                .id(faq.getId())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
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
        Faq faq =  mapToFaq(faqDto);
        faqRepository.save(faq);

    }

    @Override
    public void delete(long faqId) {
        faqRepository.deleteById(faqId);
    }
}
