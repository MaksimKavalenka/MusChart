package com.muschart.controller.rest;

import static com.muschart.constants.UrlConstants.Rest.UNITS_URL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.service.database.dao.UnitDatabaseServiceDAO;

@RestController
@RequestMapping(UNITS_URL)
public class UnitRestController {

    @Autowired
    private UnitDatabaseServiceDAO unitService;

    @RequestMapping(value = "/all/id_name", method = RequestMethod.GET)
    public ResponseEntity<List<IdAndNameDTO>> getAllUnitsIdAndName() {
        List<IdAndNameDTO> unitsIdAndName = unitService.getAllUnitsIdAndName();
        if (unitsIdAndName == null) {
            return new ResponseEntity<List<IdAndNameDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IdAndNameDTO>>(unitsIdAndName, HttpStatus.OK);
    }

}