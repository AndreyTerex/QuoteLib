package controller;

import domain.Quote;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.CategoryService;
import service.QuoteService;

import java.util.List;

@Controller
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService quoteService;
    private final CategoryService categoryService;

    @Autowired
    public QuoteController(QuoteService quoteService, CategoryService categoryService) {
        this.quoteService = quoteService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listQuotes(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size,
                             Model model) {
        List<Quote> quotes = quoteService.findAll(page, size);
        model.addAttribute("quotes", quotes);
        return "quotes/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("quote", new Quote());
        model.addAttribute("categories", categoryService.findAll());
        return "quotes/create-form";
    }

    @PostMapping
    public String createQuote(@Valid @ModelAttribute Quote quote, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "quotes/create-form";
        }

        try{
            quoteService.save(quote);
            return "redirect:/quotes";
        } catch (Exception e){
            model.addAttribute("errorMessage", "Error saving quote: " + e.getMessage());
            model.addAttribute("categories",categoryService.findAll());
            return "quotes/create-form";
        }
    }

    @GetMapping("/{id}")
    public String showQuoteDetails(@PathVariable Long id, Model model) {
        Quote quote = quoteService.findById(id);
        model.addAttribute("quote", quote);
        return "quotes/details";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Quote quote = quoteService.findById(id);
        model.addAttribute("quote", quote);
        model.addAttribute("categories", categoryService.findAll());
        return "quotes/edit-form";
    }

    @PutMapping("/{id}")
    public String updateQuote(@PathVariable Long id,
                              @Valid @ModelAttribute Quote quote,
                              BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "quotes/edit-form";
        }
        try {
            quote.setId(id);
            quoteService.update(quote);
            return "redirect:/quotes";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating quote: " + e.getMessage());
            model.addAttribute("categories", categoryService.findAll());
            return "quotes/edit-form";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteQuote(@PathVariable Long id) {
        quoteService.deleteById(id);
        return "redirect:/quotes";
    }
}
