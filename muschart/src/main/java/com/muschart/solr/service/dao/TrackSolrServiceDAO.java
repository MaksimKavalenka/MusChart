package com.muschart.solr.service.dao;

import com.muschart.exception.UploadException;

public interface TrackSolrServiceDAO {

    void createTrack(long id, String name) throws UploadException;

}