package com.muschart.solr.service.impl;

import static com.muschart.constants.SolrConstants.Core.TRACKS_CORE_URI;
import static com.muschart.constants.SolrConstants.Fields.TracksFields.*;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.muschart.exception.UploadException;
import com.muschart.solr.service.dao.TrackSolrServiceDAO;

@Service("trackSolrService")
public class TrackSolrServiceImpl implements TrackSolrServiceDAO {

    @Override
    public void createTrack(long id, String name) throws UploadException {
        try (SolrClient solrClient = new HttpSolrClient(TRACKS_CORE_URI)) {
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