package by.gsu.controller.rest;

import static by.gsu.constants.UrlConstants.Rest.UNITS_URL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.UnitDAO;
import by.gsu.entity.UnitEntity;

@RestController
@RequestMapping(UNITS_URL)
public class UnitRestController extends by.gsu.controller.rest.RestController {

    @Autowired
    private UnitDAO unitDAO;

    @RequestMapping(value = "/all"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UnitEntity>> getAllUnits() {
        List<UnitEntity> unions = unitDAO.getAllUnits();
        if (unions == null) {
            return new ResponseEntity<List<UnitEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UnitEntity>>(unions, HttpStatus.OK);
    }

}
