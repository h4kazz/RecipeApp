package lt.techin.exam.category.mapper;

import lt.techin.exam.category.dto.CategoryCreateRequest;
import lt.techin.exam.category.dto.CategoryResponse;
import lt.techin.exam.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryCreateRequest request) {
        return Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}