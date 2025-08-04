package service;

import domain.Quote;

import java.util.List;

public interface QuoteService {
    List<Quote> findAll(int page, int size);

    Quote save(Quote quote);
    Quote update(Quote quote);
    void deleteById(Long id);
    Quote findById(Long id);
}
