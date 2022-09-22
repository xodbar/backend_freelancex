package kz.xodbar.freelancex.api.fields;

import java.util.List;
import kz.xodbar.freelancex.core.field.model.Field;
import kz.xodbar.freelancex.core.field.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fields")
public class FieldController {

    private final FieldService fieldService;

    @GetMapping
    public ResponseEntity<Field[]> getAllFields() {
        Field[] arr = fieldService.getAllFields().toArray(new Field[0]);
        return ResponseEntity.ok(arr);
    }
}
