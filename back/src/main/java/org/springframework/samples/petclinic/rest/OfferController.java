package org.springframework.samples.petclinic.rest;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Offer;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.OfferService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/offer")
public class OfferController {
	
	@Autowired
	private OfferService offerService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Offer>> getOffersList() {
		Collection<Offer> offers = this.offerService.findAll();
		if (offers.isEmpty()) {
			return new ResponseEntity<Collection<Offer>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Offer>>(offers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/valids", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Offer>> getValidsOffersList() {
		Collection<Offer> offers = this.offerService.getValidOffer();
		if (offers.isEmpty()) {
			return new ResponseEntity<Collection<Offer>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Offer>>(offers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{offerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Offer> getPet(@PathVariable("offerId") int offerId){
		Offer offer = this.offerService.findById(offerId);
		if(offer == null){
			return new ResponseEntity<Offer>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Offer>(offer, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{offerId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deletePet(@PathVariable("offerId") int offerId){
		Offer offer = this.offerService.findById(offerId);
		if(offer == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.offerService.delete(offer.getId());
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Offer> add(@RequestBody @Valid Offer offer){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(offer == null){
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Offer>(headers, HttpStatus.BAD_REQUEST);
		}
		this.offerService.addOffer(offer);
		return new ResponseEntity<Offer>(offer, headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{offerId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Offer> update(@RequestBody @Valid Offer offer, @PathVariable("offerId") int offerId){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(offer == null || offer.getId()==null || offerId != offer.getId().intValue()){
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Offer>(headers, HttpStatus.BAD_REQUEST);
		}
		this.offerService.updateOffer(offer);
		return new ResponseEntity<Offer>(offer, headers, HttpStatus.OK);
	}

}
