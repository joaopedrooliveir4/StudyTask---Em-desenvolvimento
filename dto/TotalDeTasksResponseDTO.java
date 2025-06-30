package com.joaopedro.studytask.dto;

public class TotalDeTasksResponseDTO {
    private Long total;
    private String message;

    public TotalDeTasksResponseDTO(Long total, String message) {
        this.total = total;
        this.message = message;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
