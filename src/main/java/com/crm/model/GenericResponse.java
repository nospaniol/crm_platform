package com.crm.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class GenericResponse implements Serializable {

    String result;
    List<LoginResponse> loginResponse;
    List<DashboardResponse> dashboardResponse;
}
