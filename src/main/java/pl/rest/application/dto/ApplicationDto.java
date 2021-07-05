package pl.rest.application.dto;

import pl.rest.application.entity.Status;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ApplicationDto {

    private Long id;
    private Long appNumber;
    private LocalDateTime lastChangeDate;
    @NotBlank
    private String name;
    @NotBlank
    private String content;
    private Status status;
    private boolean archived;
    private String rejectReason;

    public ApplicationDto(Long id, Long appNumber, LocalDateTime lastChangeDate, @NotBlank String name,
                          @NotBlank String content, Status status, boolean archived, String rejectReason) {
        this.id = id;
        this.appNumber = appNumber;
        this.lastChangeDate = lastChangeDate;
        this.name = name;
        this.content = content;
        this.status = status;
        this.archived = archived;
        this.rejectReason = rejectReason;
    }

    public ApplicationDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(Long appNumber) {
        this.appNumber = appNumber;
    }

    public LocalDateTime getLastChange() {
        return lastChangeDate;
    }

    public void setLastChangeDate(LocalDateTime lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
