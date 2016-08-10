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

import by.gsu.database.dao.IUnitDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.UnitFactory;
import by.gsu.model.Unit;

@RestController
public class UnitRestController {

    @RequestMapping(value = UNITS_PATH
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Unit>> getAllUnits() {
        try (IUnitDAO unionDAO = UnitFactory.getEditor()) {
            List<Unit> unions = unionDAO.getAllUnits();
            if (unions == null) {
                return new ResponseEntity<List<Unit>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Unit>>(unions, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Unit>>(HttpStatus.CONFLICT);
        }
    }

}
