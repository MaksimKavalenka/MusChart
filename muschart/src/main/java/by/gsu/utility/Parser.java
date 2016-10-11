package by.gsu.utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import by.gsu.bean.IdAndNameEntity;
import by.gsu.constants.EntityConstants.Structure.AbstractFields;

public abstract class Parser {

    public static List<Long> getIdsFromJson(final String json) {
        List<Long> ids = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(AbstractFields.ID);
            ids.add(id);
        }
        return ids;
    }

    public static List<IdAndNameEntity> parseObjectsToIdAndNameEntities(
            final List<Object[]> objects) {
        List<IdAndNameEntity> idAndNameEntities = new ArrayList<>(objects.size());
        for (Object[] object : objects) {
            IdAndNameEntity artistIdAndName = new IdAndNameEntity((Long) object[0],
                    (String) object[1]);
            idAndNameEntities.add(artistIdAndName);
        }
        return idAndNameEntities;
    }

}
