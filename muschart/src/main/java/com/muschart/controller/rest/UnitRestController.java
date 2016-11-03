package com.muschart.controller.rest;

import static com.muschart.constants.UrlConstants.Rest.JSON_EXT;
import static com.muschart.constants.UrlConstants.Rest.UNIT_URL;
import static com.muschart.constants.UrlConstants.Rest.Operation.GET_OPERATION;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muschart.bean.entity.IdAndNameEntity;
import com.muschart.service.dao.UnitServiceDAO;

@RestController
@RequestMapping(UNIT_URL)
public class UnitRestController {

    @Autowired
    private UnitServiceDAO unitService;

    @RequestMapping(value = GET_OPERATION + "/all/id_name" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<IdAndNameEntity>> getAllUnitsIdAndName() {
        List<IdAndNameEntity> unitsIdAndName = unitService.getAllUnitsIdAndName();
        if (unitsIdAndName == null) {
            return new ResponseEntity<List<IdAndNameEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IdAndNameEntity>>(unitsIdAndName, HttpStatus.OK);
    }

}
