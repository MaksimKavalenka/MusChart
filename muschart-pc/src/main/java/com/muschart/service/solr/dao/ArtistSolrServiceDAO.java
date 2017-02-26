package com.muschart.service.solr.dao;

import com.muschart.exception.UploadException;

public interface ArtistSolrServiceDAO {

    void createArtist(long id, String name) throws UploadException;

}