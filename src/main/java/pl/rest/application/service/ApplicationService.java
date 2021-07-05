package pl.rest.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rest.application.dto.ApplicationDto;
import pl.rest.application.dto.RejectReason;
import pl.rest.application.entity.Application;
import pl.rest.application.entity.Status;
import pl.rest.application.exception.ApplicationNotFoundException;
import pl.rest.application.exception.ChangeStatusException;
import pl.rest.application.exception.RejectReasonException;
import pl.rest.application.repository.ApplicationRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ApplicationService {

    private final ApplicationRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public ApplicationService(ApplicationRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Long createAppNumber() {
        return Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
    }

    public LocalDateTime dateOfStatusChange() {
        return LocalDateTime.now();
    }

    public Application archiveApplication(Application currentApp) {
        currentApp.setArchived(true);
        return currentApp;
    }

    public Status changeStatus(Status status) {

        if (status == Status.CREATED) {
            return Status.VERIFIED;
        } else if (status == Status.VERIFIED) {
            return Status.ACCEPTED;
        } else if (status == Status.ACCEPTED) {
            return Status.PUBLISHED;
        } else {
            throw new RuntimeException("Can not change application status");
        }
    }

    public void addApplication(ApplicationDto applicationDto) {
        Application application = modelMapper.map(applicationDto, Application.class);
        application.setAppNumber(createAppNumber());
        application.setStatus(Status.CREATED);
        application.setLastChangeDate(dateOfStatusChange());
        repository.save(application);
    }

    public Page<ApplicationDto> getPageableListOfApplicationDto(Status state, String name, @NonNull Pageable page) {

        Page<Application> applications;
        if (state != null && name != null) {
            applications = repository.findAllByNameAndStatus(name, state, page);
        } else if (state == null && name == null) {
            applications = repository.findAll(page);
        } else if (name == null) {
            applications = repository.findAllByStatus(state, page);
        } else {
            applications = repository.findAllByName(name, page);
        }
        List<ApplicationDto> list = applications.getContent().stream()
                .map(app -> modelMapper.map(app, ApplicationDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(list, applications.getPageable(), applications.getTotalElements());
    }

    public Page<ApplicationDto> getPageableListOfApplicationDtoHistory(Long appNumber, Boolean isArchived, @NonNull Pageable page) {

        Page<Application> applications;
        if (isArchived == null) {
            applications = repository.findAllByAppNumber(appNumber, page);
        } else if (!isArchived) {
            applications = repository.findAllByAppNumberAndArchived(appNumber, isArchived, page);
        } else {
            applications = repository.findAllByAppNumberAndArchived(appNumber, isArchived, page);
        }
        List<ApplicationDto> list = applications.getContent().stream()
                .map(app -> modelMapper.map(app, ApplicationDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(list, applications.getPageable(), applications.getTotalElements());
    }

    public ApplicationDto getApplicationById(Long id) {

        Application app = ifApplicationExistReturn(repository.findById(id));
        return modelMapper.map(app, ApplicationDto.class);
    }

    public Application changeStatus(Long id) {

        Application appToArchive = ifApplicationExistReturn(repository.findById(id));
        repository.save(archiveApplication(appToArchive));

        Application newStatusApp = new Application();
        newStatusApp.setName(appToArchive.getName());
        newStatusApp.setContent(appToArchive.getContent());
        newStatusApp.setAppNumber(appToArchive.getAppNumber());
        newStatusApp.setArchived(false);
        newStatusApp.setStatus(changeStatus(appToArchive.getStatus()));
        newStatusApp.setLastChangeDate(dateOfStatusChange());
        repository.save(newStatusApp);

        return newStatusApp;
    }

    public Application rejectApplication(Long id, @Valid RejectReason rejectReason) {

        checkIfRejectReasonExist(rejectReason);

        Application appToArchive = ifApplicationExistReturn(repository.findById(id));
        if (!appToArchive.isArchived()) {
            repository.save(archiveApplication(appToArchive));
        } else {
            throw new ChangeStatusException("Can not change status archived application");
        }

        if (appToArchive.getStatus() == Status.VERIFIED || appToArchive.getStatus() == Status.ACCEPTED && appToArchive.isArchived()) {
            Application newStatusApp = new Application();
            newStatusApp.setName(appToArchive.getName());
            newStatusApp.setContent(appToArchive.getContent());
            newStatusApp.setAppNumber(appToArchive.getAppNumber());
            newStatusApp.setArchived(false);
            newStatusApp.setStatus(Status.REJECTED);
            newStatusApp.setLastChangeDate(dateOfStatusChange());
            newStatusApp.setRejectReason(rejectReason.getRejectReason());
            repository.save(newStatusApp);
            return newStatusApp;
        } else {
            throw new ChangeStatusException("Can not reject application with status CREATED, PUBLISHED or REJECTED");
        }
    }

    public Application deleteApplication(Long id, @Valid RejectReason rejectReason) {

        checkIfRejectReasonExist(rejectReason);

        Application app = ifApplicationExistReturn(repository.findById(id));
        if (app.getStatus() == Status.CREATED) {
            repository.delete(app);
        } else {
            throw new ChangeStatusException("Can not delete application with not CREATED status");
        }
        return app;
    }

    public void checkIfRejectReasonExist(RejectReason rejectReason) {

        if (rejectReason.getRejectReason() == null) {
            throw new RejectReasonException("Can not delete/reject application with no reason");
        }
        if (rejectReason.getRejectReason().isEmpty()) {
            throw new RejectReasonException("Can not delete/reject application with no reason");
        }
    }

    public Application ifApplicationExistReturn(Optional<Application> opt) {
        if(!opt.isPresent()) {
            throw new ApplicationNotFoundException("Application does not exist");
        }
        return opt.get();
    }
}
