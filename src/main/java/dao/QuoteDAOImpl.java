package dao;

import domain.Category;
import domain.Quote;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class QuoteDAOImpl implements QuoteDAO{

    private final JdbcTemplate jdbcTemplate;

    public QuoteDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Quote> findAll(int page,int size){
        int offset = (page - 1)*size;
        String sql = "SELECT " +
                "q.id as quote_id, q.author,q.content, " +
                "c.id as category_id, c.name as category_name " +
                "FROM quote q " +
                "LEFT JOIN category c ON q.category_id = c.id " +
                "ORDER BY q.id " +
                "LIMIT ? OFFSET ? ";
        return jdbcTemplate.query(sql, QUOTE_ROW_MAPPER,size,offset);
    }

    @Override
    public Optional<Quote> save(Quote quote) {
        String sql = "INSERT INTO quote (author,content,category_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            setQuoteParameters(ps,quote);
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            quote.setId(keyHolder.getKey().longValue());
            return Optional.of(quote);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Quote> update(Quote quote) {
        String sql = "UPDATE quote SET author = ?, content = ?, category_id = ? WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql,
                quote.getAuthor(),
                quote.getContent(),
                (quote.getCategory() != null ? quote.getCategory().getId() : null),
                quote.getId());

        return updatedRows > 0 ? Optional.of(quote) : Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM quote WHERE id = ?";
        int deletedRows = jdbcTemplate.update(sql, id);
        return deletedRows > 0;
    }

    @Override
    public Optional<Quote> findById(Long id) {
        String sql = "SELECT q.id as quote_id, q.author,q.content, " +
                " c.id as category_id, c.name as category_name " +
                "FROM quote q " +
                "LEFT JOIN category c ON q.category_id = c.id " +
                "WHERE q.id = ?";
        try {
            Quote quote = jdbcTemplate.queryForObject(sql, QUOTE_ROW_MAPPER, id);
            return Optional.of(quote);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    private void setQuoteParameters(PreparedStatement ps, Quote quote) throws java.sql.SQLException {
        ps.setString(1, quote.getAuthor());
        ps.setString(2, quote.getContent());

        if (quote.getCategory() != null && quote.getCategory().getId() != null) {
            ps.setLong(3, quote.getCategory().getId());
        } else {
            ps.setNull(3, java.sql.Types.BIGINT);
        }
    }
    private static final RowMapper<Quote> QUOTE_ROW_MAPPER = (resultSet, rowNum) -> {
        Category category = null;
        Long categoryId = resultSet.getLong("category_id");
        if (!resultSet.wasNull()) {
            category = Category.builder()
                    .id(categoryId)
                    .name(resultSet.getString("category_name"))
                    .build();
        }

        return Quote.builder()
                .id(resultSet.getLong("quote_id"))
                .author(resultSet.getString("author"))
                .content(resultSet.getString("content"))
                .category(category)
                .build();
    };
}
