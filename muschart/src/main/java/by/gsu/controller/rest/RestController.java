package by.gsu.controller.rest;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import by.gsu.constants.EntityConstants.Structure.AbstractFields;

public abstract class RestController {

    public static final String JSON_EXT = ".json";

    public List<Long> getIds(final String json) {
        List<Long> ids = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(AbstractFields.ID);
            ids.add(id);
        }
        return ids;
    }

}
