package lt.techin.exam.category.dto;

public record CategoryResponse(
        Long id,
        String name,
        String description
) {}