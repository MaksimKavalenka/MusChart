package com.muschart.service.solr.dao;

import com.muschart.exception.UploadException;

public interface GenreSolrServiceDAO {

    void createGenre(long id, String name) throws UploadException;

}