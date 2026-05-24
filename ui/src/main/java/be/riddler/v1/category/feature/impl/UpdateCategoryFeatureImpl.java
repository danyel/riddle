package be.riddler.v1.category.feature.impl;

import be.riddler.v1.category.client.model.Category;
import be.riddler.v1.category.client.model.UpdateCategory;
import be.riddler.v1.category.client.model.UpdateKeyword;
import be.riddler.v1.category.entity.KeywordEntity;
import be.riddler.v1.category.feature.UpdateCategoryFeature;
import be.riddler.v1.category.mapper.CategoryMapper;
import be.riddler.v1.category.mapper.KeywordMapper;
import be.riddler.v1.category.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * UpdateCategoryFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 18/05/2026
 */
@Service
@RequiredArgsConstructor
class UpdateCategoryFeatureImpl implements UpdateCategoryFeature {
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public @NonNull Category update(@NonNull UUID categoryId, @NonNull UpdateCategory updateCategory) {
        var categoryEntity = categoryRepository.findById(categoryId);

        if (categoryEntity.isPresent()) {
            var category = categoryEntity.get();
            if (!Objects.equals(category.getName(), updateCategory.name())) {
                category.setName(updateCategory.name());
            }

            var collect = updateCategory.keywords().stream().filter(e -> Objects.nonNull(e.id())).collect(Collectors.toMap(UpdateKeyword::id, new Function<UpdateKeyword, UpdateKeyword>() {
                @Override
                public @NonNull UpdateKeyword apply(@NonNull UpdateKeyword updateCategoryFeature) {
                    return updateCategoryFeature;
                }
            }));
            category.getKeywords().removeAll(category.getKeywords().stream().filter(s -> !collect.containsKey(s.getId())).toList());
            updateCategory.keywords()
                    .forEach(updateKeyword -> {
                        if (updateKeyword.id() != null) {
                            category.getKeywords()
                                    .stream()
                                    .filter(keywordEntity -> hasDifferentWord(updateKeyword, keywordEntity))
                                    .forEach(keywordEntity -> keywordEntity.setWord(updateKeyword.word()));
                        } else {
                            var toSave = KeywordMapper.fromUpdateKeyword(updateKeyword);
                            toSave.setCategory(category);
                            category.getKeywords().add(toSave);
                        }
                    });
            categoryRepository.save(category);
            return CategoryMapper.fromCategoryEntity(category);
        }

        throw new EntityNotFoundException("Category with id " + categoryId + " not found");
    }

    private static boolean hasDifferentWord(UpdateKeyword updateKeyword, KeywordEntity keywordEntity) {
        return Objects.equals(keywordEntity.getId(), updateKeyword.id()) && !Objects.equals(updateKeyword.word(), keywordEntity.getWord());
    }
}
