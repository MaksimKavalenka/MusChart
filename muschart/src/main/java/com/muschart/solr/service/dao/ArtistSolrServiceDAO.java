package com.muschart.solr.service.dao;

import com.muschart.exception.UploadException;

public interface ArtistSolrServiceDAO {

    void createArtist(long id, String name) throws UploadException;

}