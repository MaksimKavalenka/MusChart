package by.gsu.controller.rest;

import static by.gsu.constants.RestConstants.UNITS_PATH;
import static by.gsu.constants.RestConstants.JSON_EXT;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.model.UnitModel;

@RestController
public class UnitRestController extends by.gsu.controller.rest.RestController {

    @RequestMapping(value = UNITS_PATH
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UnitModel>> getAllUnits() {
        List<UnitModel> unions = unitDAO.getAllUnits();
        if (unions == null) {
            return new ResponseEntity<List<UnitModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UnitModel>>(unions, HttpStatus.OK);
    }

}
