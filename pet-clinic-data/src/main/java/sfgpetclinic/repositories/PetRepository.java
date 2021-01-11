package sfgpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import sfgpetclinic.model.Pet;

public interface PetRepository extends CrudRepository<Pet,Long> {
}
