package com.sr.stock.dbservice.resource;

import com.sr.stock.dbservice.model.Quote;
import com.sr.stock.dbservice.model.Quotes;
import com.sr.stock.dbservice.repository.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DbServiceResource {

    @Autowired
    private QuotesRepository quotesRepository;

    @RequestMapping(value = "/rest/db/{username}", method = RequestMethod.GET)
    public List<String> getQuotes(
            @PathVariable String username
    ) {
//        System.out.println(username);
        return quotesRepository.findByUsername(username)
                .stream()
                .map(Quote::getQuote).collect(Collectors.toList());
    }

    @RequestMapping(value = "/rest/db/add", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<String> add(
            @RequestBody final Quotes quotes
    ) {
        quotes.getQuotes()
                .stream()
                .map(quote -> new Quote(quotes.getUsername(), quote))
                .forEach(quote -> {
                    quotesRepository.save(quote);
                });
        return getQuotesByUsername(quotes.getUsername());
    }

    @RequestMapping(value = "/rest/db/delete/{username}", method = RequestMethod.DELETE)
    public void deleteUser(
            @PathVariable String username
    ) {
        List<Quote> quotes = quotesRepository.findByUsername(username);
        quotes.stream().forEach(quote -> {quotesRepository.delete(quote);});
    }
    private List<String> getQuotesByUsername(String username) {
        return quotesRepository.findByUsername(username)
                .stream()
                .map(Quote::getQuote).collect(Collectors.toList());
    }


}
