package service;


import dao.QuoteDAO;
import domain.Quote;
import exception.QuoteNotFoundException;
import exception.SaveQuoteException;
import exception.UpdateQuoteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class QuoteServiceImpl implements QuoteService {

    private final QuoteDAO quoteDAO;

    @Autowired
    public QuoteServiceImpl(QuoteDAO quoteDAO) {
        this.quoteDAO = quoteDAO;

    }

    @Override
    public List<Quote> findAll(int page, int size) {
        return quoteDAO.findAll(page, size);
    }

    @Override
    @Transactional
    public Quote save(Quote quote) {
        return quoteDAO.save(quote).orElseThrow(() ->
                new SaveQuoteException("Failed to save new quote with author  " +
                        quote.getAuthor() + ", content " +
                        quote.getContent() + " and category" +
                        quote.getCategory().getName()));
    }

    @Override
    @Transactional
    public Quote update(Quote quoteForUpdate) {
        Quote existingQuote = findById(quoteForUpdate.getId());
        existingQuote.setAuthor(quoteForUpdate.getAuthor());
        existingQuote.setContent(quoteForUpdate.getContent());
        existingQuote.setCategory(quoteForUpdate.getCategory());
        return quoteDAO.update(existingQuote).orElseThrow(() ->
                new UpdateQuoteException("Failed update quote with ID " + existingQuote.getId()));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        boolean isDeleted = quoteDAO.deleteById(id);
        if (!isDeleted) {
            throw new QuoteNotFoundException("Failed to delete. Quote with id " + id + " not found");
        }
    }

    @Override
    public Quote findById(Long id) {
        return quoteDAO.findById(id).orElseThrow(() ->
                new QuoteNotFoundException("Quote with " + id + "not found"));
    }
}
