package sfgpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import sfgpetclinic.model.Vet;

public interface VetRepository extends CrudRepository<Vet,Long> {
}
