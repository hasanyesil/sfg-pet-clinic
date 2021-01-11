package sfgpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import sfgpetclinic.model.PetType;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
