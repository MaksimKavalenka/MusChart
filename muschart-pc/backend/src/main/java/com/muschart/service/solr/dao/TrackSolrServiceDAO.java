package com.muschart.service.solr.dao;

import com.muschart.exception.UploadException;

public interface TrackSolrServiceDAO {

    void createTrack(long id, String name) throws UploadException;

}