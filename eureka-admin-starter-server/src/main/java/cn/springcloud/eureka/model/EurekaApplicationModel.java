package cn.springcloud.eureka.model;

import lombok.Data;

import java.util.List;

@Data
public class EurekaApplicationModel {
    private String name;
    private List<EurekaInstance> instance;
}
