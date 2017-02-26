package com.muschart.service.solr.impl;

import static com.muschart.constants.SolrConstants.Core.GENRES_CORE_URI;
import static com.muschart.constants.SolrConstants.Fields.GenresFields.*;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.muschart.exception.UploadException;
import com.muschart.service.solr.dao.GenreSolrServiceDAO;

@Service("genreSolrService")
public class GenreSolrServiceImpl implements GenreSolrServiceDAO {

    @Override
    public void createGenre(long id, String name) throws UploadException {
        try (SolrClient solrClient = new HttpSolrClient(GENRES_CORE_URI)) {
            SolrInputDocument inputDocument = new SolrInputDocument();

            inputDocument.setField(ID, id);
            inputDocument.setField(NAME, name);

            solrClient.add(inputDocument);
            solrClient.commit(true, true);
        } catch (IOException | SolrServerException e) {
            throw new UploadException(e.getMessage());
        }
    }

}