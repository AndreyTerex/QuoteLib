package dao;

import domain.Quote;

import java.util.List;
import java.util.Optional;

public interface QuoteDAO {
    Optional<Quote> save(Quote quote);
    Optional<Quote> update(Quote quote);
    boolean deleteById(Long id);
    Optional<Quote> findById(Long id);

    List<Quote> findAll(int page, int size);
}
