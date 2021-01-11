package sfgpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import sfgpetclinic.model.Visit;

public interface VisitRepository extends CrudRepository<Visit,Long> {
}
