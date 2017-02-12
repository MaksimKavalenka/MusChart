package com.muschart.utility;

import static com.muschart.constants.EntityConstants.Structure.Entities.ARTIST;
import static com.muschart.constants.EntityConstants.Structure.Entities.GENRE;
import static com.muschart.constants.EntityConstants.Structure.Entities.TRACK;
import static com.muschart.constants.SolrConstants.Core.*;
import static com.muschart.constants.SolrConstants.Fields.SolrFields.SHARD;
import static com.muschart.constants.SolrConstants.Fields.TracksFields.*;
import static com.muschart.constants.SolrConstants.Key.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.dto.output.SuggestionOutputDTO;

public abstract class Parser {

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

    public static List<SuggestionOutputDTO> getSuggestionOutputDtoList(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject response = jsonObject.getJSONObject(RESPONSE);
        JSONArray docs = response.getJSONArray(DOCS);

        List<SuggestionOutputDTO> suggestionsOutput = new ArrayList<>(docs.length());
        for (int i = 0; i < docs.length(); i++) {
            String entity = "";
            JSONObject suggestionObject = docs.getJSONObject(i);

            switch (suggestionObject.getString(SHARD)) {
                case ARTISTS_CORE_URI:
                    entity = ARTIST;
                    break;
                case GENRES_CORE_URI:
                    entity = GENRE;
                    break;
                case TRACKS_CORE_URI:
                    entity = TRACK;
                    break;
                default:
                    break;
            }

            SuggestionOutputDTO suggestionOutput = new SuggestionOutputDTO(suggestionObject.getLong(ID), suggestionObject.getString(NAME), entity);
            suggestionsOutput.add(suggestionOutput);
        }

        return suggestionsOutput;
    }

}