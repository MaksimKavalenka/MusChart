package com.muschart.controller.rest;

import static com.muschart.constants.UrlConstants.Rest.SEARCH_URL;
import static com.muschart.constants.UrlConstants.Rest.Operation.SUGGEST_OPERATION;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muschart.dto.output.SuggestionOutputDTO;

@RestController
@RequestMapping(SEARCH_URL)
public class SearchRestController extends com.muschart.controller.rest.RestController {

    @RequestMapping(value = SUGGEST_OPERATION, method = RequestMethod.POST)
    public ResponseEntity<List<SuggestionOutputDTO>> getSuggestions(@RequestBody @Valid String query) {
        List<SuggestionOutputDTO> suggestionsOutput = searchSolrService.getSuggestions(query);
        return new ResponseEntity<List<SuggestionOutputDTO>>(suggestionsOutput, HttpStatus.OK);
    }

}