package com.muschart.service.database.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.muschart.jpa.repository.ArtistRepository;
import com.muschart.jpa.repository.GenreRepository;
import com.muschart.jpa.repository.RoleRepository;
import com.muschart.jpa.repository.TrackArtistRepository;
import com.muschart.jpa.repository.TrackRepository;
import com.muschart.jpa.repository.UnitRepository;
import com.muschart.jpa.repository.UserRepository;

public abstract class DatabaseServiceImpl {

    @Autowired
    public ArtistRepository      artistRepository;

    @Autowired
    public GenreRepository       genreRepository;

    @Autowired
    public RoleRepository        roleRepository;

    @Autowired
    public TrackRepository       trackRepository;

    @Autowired
    public UnitRepository        unitRepository;

    @Autowired
    public UserRepository        userRepository;

    @Autowired
    public TrackArtistRepository trackArtistRepository;

}