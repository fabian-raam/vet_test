package com.tecsup.petclinic.util;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Vet;

import java.util.ArrayList;
import java.util.List;

public class TObjectCreator {

	public static List<Vet> getActiveVets() {
		return List.of(
				new Vet(1, "James", "Carter", "james.carter@petclinic.com", "6085551234", true),
				new Vet(2, "Helen", "Leary", "helen.leary@petclinic.com", "6085552345", true),
				new Vet(3, "Linda", "Douglas", "linda.douglas@petclinic.com", "6085553456", true),
				new Vet(4, "Rafael", "Ortega", "rafael.ortega@petclinic.com", "6085554567", true),
				new Vet(5, "Henry", "Stevens", "henry.stevens@petclinic.com", "6085555678", true));
	}

	public static List<Vet> getInactiveVets() {
		return List.of(new Vet(6, "Sharon", "Jenkins", "sharon.jenkins@petclinic.com", "6085556789", false));
	}

	public static Pet getPet() {
		return new Pet(1,"Leo",1,1, null);
	}

	public static Pet newPet() {
		Pet newEntity = new Pet();
		newEntity.setName("Punky");
		newEntity.setTypeId(1);
		newEntity.setOwnerId(1);
		return newEntity;
	}

	public static Pet newPetCreated() {
		Pet pet = newPet();
		pet.setId(1000);
		return pet;
	}

	public static PetDTO newPetDTO() {
		PetDTO newDTO = new PetDTO();
		newDTO.setName("Punky");
		newDTO.setTypeId(1);
		newDTO.setOwnerId(1);
		return newDTO;
	}

	public static PetDTO newPetDTOCreated() {
		PetDTO petDTO = newPetDTO();
		petDTO.setId(1000);
		return petDTO;
	}


	public static Pet newPetForUpdate() {
		return new Pet(0,"Bear",1,1,null);
	}

	public static Pet newPetCreatedForUpdate() {
		Pet pet = newPetForUpdate();
		pet.setId(4000);
		return pet;
	}

	public static Pet newPetForDelete() {
		return new Pet(0,"Bird",1,1, null);
	}

	public static Pet newPetCreatedForDelete() {
		Pet pet = newPetForDelete();
		pet.setId(2000);
		return pet;
	}

	public static List<Pet> getPetsForFindByName() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(1,"Leo",1,1, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByTypeId() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(9,"Lucky",5,7, null));
		pets.add(new Pet(11,"Freddy",5,9, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByOwnerId() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(12,"Lucky",2,10, null));
		pets.add(new Pet(13,"Sly",1,10, null));
		return pets;
	}
}
