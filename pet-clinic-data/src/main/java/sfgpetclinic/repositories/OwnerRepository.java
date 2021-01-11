package sfgpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import sfgpetclinic.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner,Long> {
}
