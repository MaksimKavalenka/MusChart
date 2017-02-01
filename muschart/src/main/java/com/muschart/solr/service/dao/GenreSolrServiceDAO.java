package com.muschart.solr.service.dao;

import com.muschart.exception.UploadException;

public interface GenreSolrServiceDAO {

    void createGenre(long id, String name) throws UploadException;

}