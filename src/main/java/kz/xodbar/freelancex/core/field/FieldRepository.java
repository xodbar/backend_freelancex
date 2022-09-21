package kz.xodbar.freelancex.core.field;

import kz.xodbar.freelancex.core.field.model.FieldModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<FieldModel, Long> {
    FieldModel findByName(String name);
}
