package pl.rest.application.dto;

import javax.validation.constraints.NotBlank;

public class RejectReason {

    @NotBlank
    private String rejectReason;

    public RejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public RejectReason() {
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
