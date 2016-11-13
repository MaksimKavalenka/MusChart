package com.muschart.utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.muschart.constants.EntityConstants.Structure.AbstractFields;
import com.muschart.dto.IdAndNameDTO;

public abstract class Parser {

    public static List<Long> getIdsFromJson(String json) {
        List<Long> ids = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(AbstractFields.ID);
            ids.add(id);
        }
        return ids;
    }

    public static List<IdAndNameDTO> parseObjectsToIdAndNameEntities(List<Object[]> objects) {
        List<IdAndNameDTO> idsAndNamesDto = new ArrayList<>(objects.size());
        for (Object[] object : objects) {
            IdAndNameDTO idAndNameDto = new IdAndNameDTO((Long) object[0], (String) object[1]);
            idsAndNamesDto.add(idAndNameDto);
        }
        return idsAndNamesDto;
    }

    public static String getErrorsMessagesFromObjectError(Errors errors) {
        StringBuilder errorsMessages = new StringBuilder();
        for (ObjectError error : errors.getAllErrors()) {
            errorsMessages.append(error.getDefaultMessage() + "; ");
        }
        return errorsMessages.toString();
    }

}
