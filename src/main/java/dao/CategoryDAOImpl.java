package dao;

import domain.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    private final JdbcTemplate jdbcTemplate;

    public CategoryDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT id, name FROM category ORDER BY name";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    @Override
    public Optional<Category> findById(Long id) {
        String sql = "SELECT id, name FROM category WHERE id = ?";
        try {
            Category category = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), id);
            return Optional.ofNullable(category);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
