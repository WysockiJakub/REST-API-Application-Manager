package pl.rest.application.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long appNumber;

    private LocalDateTime lastChangeDate;

    @NotBlank
    private String name;

    @NotBlank
    private String content;

    private Status status;

    private boolean archived = false;

    private String rejectReason;

    public Application(Long appNumber, LocalDateTime lastChangeDate, @NotBlank String name, @NotBlank String content, Status status, boolean archived, String rejectReason) {
        this.appNumber = appNumber;
        this.lastChangeDate = lastChangeDate;
        this.name = name;
        this.content = content;
        this.status = status;
        this.archived = archived;
        this.rejectReason = rejectReason;
    }

    public Application(Long appNumber, LocalDateTime lastChangeDate, @NotBlank String name, @NotBlank String content, Status status, boolean archived) {
        this.appNumber = appNumber;
        this.lastChangeDate = lastChangeDate;
        this.name = name;
        this.content = content;
        this.status = status;
        this.archived = archived;
    }

    public Application(Long appNumber, LocalDateTime lastChangeDate, @NotBlank String name, @NotBlank String content) {
        this.appNumber = appNumber;
        this.lastChangeDate = lastChangeDate;
        this.name = name;
        this.content = content;
    }

    public Application(Long appNumber, LocalDateTime lastChangeDate, @NotBlank String name, @NotBlank String content, Status status) {
        this.appNumber = appNumber;
        this.lastChangeDate = lastChangeDate;
        this.name = name;
        this.content = content;
        this.status = status;
    }

    public Application(Long appNumber, @NotBlank String name, @NotBlank String content, Status status) {
        this.appNumber = appNumber;
        this.name = name;
        this.content = content;
        this.status = status;
    }

    public Application(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Application(String name, String content, Status status) {
        this.name = name;
        this.content = content;
        this.status = status;
    }

    public Application() {
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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public LocalDateTime getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(LocalDateTime lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
