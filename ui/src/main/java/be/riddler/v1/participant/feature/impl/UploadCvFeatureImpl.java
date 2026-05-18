package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.category.entity.CategoryEntity;
import be.riddler.v1.category.repository.CategoryRepository;
import be.riddler.v1.participant.entity.ParticipantEntity;
import be.riddler.v1.participant.feature.UploadCvFeature;
import be.riddler.v1.participant.repository.ParticipantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * UploadCvFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class UploadCvFeatureImpl implements UploadCvFeature {
    private final ParticipantRepository participantRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void uploadCv(UUID participantId, byte[] cvBytes) {
        Assert.notNull(cvBytes, "CV must not be null");
        Assert.isTrue(cvBytes.length > 0, "CV must not be empty");
        ParticipantEntity participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("Participant not found with ID: " + participantId));
        participant.setCv(cvBytes);

        try {
            String cvText = extractTextFromPdfBytes(cvBytes);
            Set<CategoryEntity> dynamicCategories = determineCategoriesFromText(cvText);
            participant.setCategories(dynamicCategories);
            log.info("CV processed successfully for participant {}. Assigned categories: {}",
                    participantId, dynamicCategories.stream().map(CategoryEntity::getName).toList());
        } catch (Exception e) {
            log.error("Failed to parse and extract text indexes from participant CV: {}", participantId, e);
        }
        participantRepository.save(participant);
    }

    private String extractTextFromPdfBytes(byte[] pdfBytes) throws IOException {
        try (PDDocument document = Loader.loadPDF(new RandomAccessReadBuffer(pdfBytes))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            return (text != null) ? text : "";
        }
    }

    private Set<CategoryEntity> determineCategoriesFromText(String text) {
        Set<CategoryEntity> matchedCategories = new HashSet<>();
        String lowerCaseText = text.toLowerCase();
        List<CategoryEntity> dbRules = categoryRepository.findAllWithKeywords();

        for (CategoryEntity category : dbRules) {
            long tokenMatchCount = category.getKeywords().stream()
                    .map(keyword -> keyword.getWord().toLowerCase())
                    .filter(lowerCaseText::contains)
                    .count();

            if (tokenMatchCount >= 2) {
                matchedCategories.add(category);
            }
        }

        if (matchedCategories.isEmpty()) {
            CategoryEntity defaultCategory = categoryRepository.findByNameIgnoreCase("Unclassified/General")
                    .orElseGet(() -> categoryRepository.save(
                            CategoryEntity.builder().name("Unclassified/General").build()
                    ));
            matchedCategories.add(defaultCategory);
        }

        return matchedCategories;
    }
}
