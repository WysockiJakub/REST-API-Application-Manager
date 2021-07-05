package pl.rest.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.rest.application.dto.ApplicationDto;
import pl.rest.application.dto.RejectReason;
import pl.rest.application.entity.Application;
import pl.rest.application.entity.Status;
import pl.rest.application.exception.ApplicationNotFoundException;
import pl.rest.application.exception.ChangeStatusException;
import pl.rest.application.exception.RejectReasonException;
import pl.rest.application.service.ApplicationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.beans.PropertyEditorSupport;

@Validated
@RestController
@RequestMapping("/api/apps")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(applicationService.getApplicationById(id));
        } catch (ApplicationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<ApplicationDto>> getApplicationList(@RequestParam(name = "name", required = false) String name,
                                                                   @RequestParam(name = "state", required = false) Status state,
                                                                   @RequestParam(name = "page_number", required = false, defaultValue = "0")
                                                                   @Valid @Min(0) Integer pageNumber,
                                                                   @RequestParam(name = "page_size", required = false, defaultValue = "10")
                                                                   @Valid @Positive Integer pageSize
                                                                   ) {
        return ResponseEntity.ok(applicationService.getPageableListOfApplicationDto(state, name, PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping(value = "/history", produces = "application/json")
    public ResponseEntity<Page<ApplicationDto>> getApplicationByAppNumber(@RequestParam(name = "appNumber") Long appNumber,
                                                                          @RequestParam(name = "archived", required = false) Boolean archived,
                                                                          @RequestParam(name = "page_number", required = false, defaultValue = "0")
                                                                          @Valid @Min(0) Integer pageNumber,
                                                                          @RequestParam(name = "page_size", required = false, defaultValue = "10")
                                                                          @Valid @Positive Integer pageSize
    ) {
        return ResponseEntity.ok(applicationService.getPageableListOfApplicationDtoHistory(appNumber, archived, PageRequest.of(pageNumber, pageSize)));
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Application> addApplication(@Valid @RequestBody ApplicationDto applicationDto) {
        applicationService.addApplication(applicationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/change/{id}", produces = "application/json")
    public ResponseEntity<String> changeStatus(@PathVariable Long id) {

        Application application;
        try {
            application = applicationService.changeStatus(id);
        } catch (ApplicationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return new ResponseEntity<>("Application " + application.getAppNumber()
                 + " status has been changed to " + application.getStatus(), HttpStatus.OK);
    }

    @PutMapping(value = "/reject/{id}", produces = "application/json")
    public ResponseEntity<String> rejectApplication(@PathVariable Long id, @RequestBody RejectReason rejectReason) {

        Application application;
        try {
            application = applicationService.rejectApplication(id, rejectReason);
        } catch (RejectReasonException | ChangeStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ApplicationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return new ResponseEntity<>("Application " + application.getAppNumber()
                + " status has been changed to " + application.getStatus() + ". Reject reason: " + application.getRejectReason(), HttpStatus.OK);
    }

    @DeleteMapping (value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteById(@PathVariable Long id, @RequestBody RejectReason rejectReason) {

        Application application;
        try {
            application = applicationService.deleteApplication(id, rejectReason);
        } catch (RejectReasonException | ChangeStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ApplicationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return new ResponseEntity<>("Application " + application.getAppNumber()
                + " deleted", HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(Status.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                super.setValue(stateEnumFromString(text));
            }
        });
    }

    static Status stateEnumFromString(Object value) {
        try {
            return Status.getValueFromString(value.toString());
        } catch (Exception e) {
            throw e;
        }
    }
}
