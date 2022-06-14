package com.mahd.employee.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mahd.employee.models.Tag;
import com.mahd.employee.repository.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.Accounts;
import com.mahd.employee.models.Pipeline;
import com.mahd.employee.repository.AccountRepo;
import com.mahd.employee.repository.PipelineRepo;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class PipelineController {

	@Autowired
	private PipelineRepo pipelineRepo;
	
	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private TagRepo tagRepo;
	
	@RequestMapping(value = "/api/v1/pipeline/create/{tagId}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Pipeline> addPipeline(@RequestBody Pipeline pipeline,@RequestParam(value = "date") String sampleDate1,@PathVariable(value = "tagId") String id) throws ParseException{
		Date startDate = new SimpleDateFormat("yyyy-mm-dd").parse(sampleDate1);
		pipeline.setCloseDate(startDate);

//		Accounts accounts = accountRepo.findById(id).orElseThrow(()-> new RuntimeException("Account Not found for this Id:"+id));
//		pipeline.setAccounts(accounts);
		Long tagId = Long.parseLong(id);
		Tag tag = tagRepo.findById(tagId).orElseThrow(()-> new RuntimeException("Tag not found for this id:"+tagId));
		pipeline.setTag(tag);
		pipelineRepo.save(pipeline);

		return ResponseEntity.ok().body(pipeline);
	}
	
	@GetMapping("/api/v1/pipeline")
	private List<Pipeline> getPipelines(){
		return pipelineRepo.findAllByDeleted(false);
	}
	
	@GetMapping("/api/v1/pipeline/{id}")
	private ResponseEntity<Pipeline> getSinglePipeline(@PathVariable(value = "id") Long id){
		Pipeline fetchedPipeline = pipelineRepo.findById(id).orElseThrow(()->new RuntimeException("Pipeline Not Found for the id "+id));
		return ResponseEntity.ok().body(fetchedPipeline);
	}
	
	@PutMapping("/api/v1/pipeline/{id}")
	public ResponseEntity<Pipeline> updatePipeline(@RequestBody Pipeline pipeline,@PathVariable(value = "id") Long id){
		Pipeline updPipeline = pipelineRepo.findById(id).orElseThrow(()->new RuntimeException("Pipeline Not Found for the id "+id));
		updPipeline.setName(pipeline.getName());
		updPipeline.setPrimaryContact(pipeline.getPrimaryContact());
		updPipeline.setCloseDate(updPipeline.getCloseDate());
		updPipeline.setStatus(pipeline.getStatus());
		updPipeline.setStage(pipeline.getStage());
		updPipeline.setSource(pipeline.getSource());
		updPipeline.setValue(pipeline.getValue());
		updPipeline.setPriority(pipeline.getPriority());
		updPipeline.setWinPercentage(pipeline.getWinPercentage());
		updPipeline.setDescription(pipeline.getDescription());
		pipelineRepo.save(updPipeline);
		
		return ResponseEntity.ok().body(updPipeline);
	}
	
	@DeleteMapping("/api/v1/pipeline/{id}")
	private ResponseEntity<Pipeline> deletePipeline(@PathVariable(value = "id") Long id){
		Pipeline pipeline = pipelineRepo.findById(id).orElseThrow(()-> new RuntimeException("Pipeline Not found for this id "+id));
		pipeline.setDeleted(true);
		pipelineRepo.save(pipeline);
		return ResponseEntity.ok().body(pipeline);
	}


}
