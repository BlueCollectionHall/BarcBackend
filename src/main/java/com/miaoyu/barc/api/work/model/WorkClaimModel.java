package com.miaoyu.barc.api.work.model;

import java.time.LocalDateTime;

public class WorkClaimModel {
    private String work_id;
    private String initiator_uuid;
    private String recipient_uuid;
    private LocalDateTime created_at;

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getInitiator_uuid() {
        return initiator_uuid;
    }

    public void setInitiator_uuid(String initiator_uuid) {
        this.initiator_uuid = initiator_uuid;
    }

    public String getRecipient_uuid() {
        return recipient_uuid;
    }

    public void setRecipient_uuid(String recipient_uuid) {
        this.recipient_uuid = recipient_uuid;
    }
}
