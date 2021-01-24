package sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity{

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")   //this annotation will add pet type id to pets table for relation.
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "owner_id")  //Owner can had many pets but pets can have one owner
    private Owner owner;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet") //one pet can make multiple visit
    private Set<Visit> visits = new HashSet<>();

}
