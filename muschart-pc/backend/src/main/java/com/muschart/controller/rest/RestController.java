package com.muschart.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;

import com.muschart.service.database.dao.ArtistDatabaseServiceDAO;
import com.muschart.service.database.dao.GenreDatabaseServiceDAO;
import com.muschart.service.database.dao.RoleDatabaseServiceDAO;
import com.muschart.service.database.dao.TrackDatabaseServiceDAO;
import com.muschart.service.database.dao.UnitDatabaseServiceDAO;
import com.muschart.service.database.dao.UserDatabaseServiceDAO;
import com.muschart.service.solr.dao.ArtistSolrServiceDAO;
import com.muschart.service.solr.dao.GenreSolrServiceDAO;
import com.muschart.service.solr.dao.SearchSolrServiceDAO;
import com.muschart.service.solr.dao.TrackSolrServiceDAO;

public abstract class RestController {

    @Autowired
    public ArtistDatabaseServiceDAO artistDatabaseService;

    @Autowired
    public GenreDatabaseServiceDAO  genreDatabaseService;

    @Autowired
    public RoleDatabaseServiceDAO   roleDatabaseService;

    @Autowired
    public TrackDatabaseServiceDAO  trackDatabaseService;

    @Autowired
    public UnitDatabaseServiceDAO   unitDatabaseService;

    @Autowired
    public UserDatabaseServiceDAO   userDatabaseService;

    @Autowired
    public ArtistSolrServiceDAO     artistSolrService;

    @Autowired
    public GenreSolrServiceDAO      genreSolrService;

    @Autowired
    public SearchSolrServiceDAO     searchSolrService;

    @Autowired
    public TrackSolrServiceDAO      trackSolrService;

}