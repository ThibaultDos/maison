package dev.colleguesapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.colleguesapi.Collegue;
import dev.colleguesapi.exceptions.CollegueNonTrouveException;
import dev.colleguesapi.service.CollegueService;


@RestController // Ici cette classe va répondre aux requêtes "/collegues"
@RequestMapping("/collegues")

public class CollegueController {
	
	@Autowired
	private CollegueService collegueService;
	
	@GetMapping
	public List<String> listerCollegues(@RequestParam("nom") String nomSaisiDansLaRequete) {
		
		List<Collegue> listeColleguesTrouves = collegueService.rechercherParNom(nomSaisiDansLaRequete);
		
		List<String> listeMatricules = new ArrayList<>();
		for (Collegue c : listeColleguesTrouves){
			listeMatricules.add(c.getMatricule()) ;
		}			
		return listeMatricules ;			
	}
	
	@GetMapping
	@RequestMapping("/{matricule}")//récupère le contenu de la variable matricule
	public ResponseEntity<Collegue> infosCollegue(@PathVariable String matricule) throws CollegueNonTrouveException {
		
		Collegue infosCollegue = new Collegue();
		infosCollegue = collegueService.rechercherParMatricule(matricule); 
		return ResponseEntity.status(HttpStatus.OK).body(infosCollegue);
	}
	
	@ExceptionHandler(value={CollegueNonTrouveException.class})
	public ResponseEntity<String> reponseMatriculeException(){
		
		return ResponseEntity.status(404).body("Collègue non trouvé.");
	}
	
		
	
	
}

