package dao;

import domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    // Можно также добавить save, update, delete для полного управления категориями
}
