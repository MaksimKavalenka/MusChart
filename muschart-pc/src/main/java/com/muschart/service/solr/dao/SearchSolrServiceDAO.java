package com.muschart.service.solr.dao;

import java.util.List;

import com.muschart.dto.output.SuggestionOutputDTO;

public interface SearchSolrServiceDAO {

    List<SuggestionOutputDTO> getSuggestions(String query);

}