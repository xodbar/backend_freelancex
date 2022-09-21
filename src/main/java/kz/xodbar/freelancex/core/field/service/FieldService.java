package kz.xodbar.freelancex.core.field.service;

import kz.xodbar.freelancex.core.field.model.Field;
import kz.xodbar.freelancex.core.field.model.FieldModel;

import java.util.List;

public interface FieldService {
    List<Field> getAllFields();

    Field createField(Field field);

    Field getById(Long id);

    Field getByName(String name);

    Field updateField(Field field);

    void deleteField(Field field);

    FieldModel getModelById(Long id);

    FieldModel getModelByName(String name);

    boolean fieldAlreadyExists(String fieldName);
}
