package com.muschart.service.solr.impl;

import static com.muschart.constants.DefaultConstants.DEFAULT_ROWS_COUNT;
import static com.muschart.constants.DefaultConstants.DEFAULT_QUERY;
import static com.muschart.constants.SolrConstants.Fields.SolrFields.SHARD;
import static com.muschart.constants.SolrConstants.Fields.TracksFields.*;

import java.util.List;

import static com.muschart.constants.SolrConstants.Core.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.muschart.dto.output.SuggestionOutputDTO;
import com.muschart.service.solr.dao.SearchSolrServiceDAO;
import com.muschart.solr.common.RequestHeader;
import com.muschart.solr.common.SolrURI;
import com.muschart.solr.common.WriterType;
import com.muschart.utility.Parser;

@Service("searchSolrService")
public class SearchSolrServiceImpl implements SearchSolrServiceDAO {

    @Override
    public List<SuggestionOutputDTO> getSuggestions(String query) {
        SolrURI solrUri = new SolrURI(TRACKS_CORE_URI, RequestHeader.SELECT);
        solrUri.setShards(ARTISTS_CORE_URI, GENRES_CORE_URI, TRACKS_CORE_URI);
        solrUri.setFieldList(ID, NAME, SHARD);
        solrUri.setFilterQuery(NAME, query);
        solrUri.setRowsCount(DEFAULT_ROWS_COUNT);
        solrUri.setQuery(DEFAULT_QUERY);
        solrUri.setWriterType(WriterType.JSON);

        RestTemplate restTemplate = new RestTemplate();
        return Parser.getSuggestionOutputDtoList(restTemplate.getForObject(solrUri.toString(), String.class));
    }

}