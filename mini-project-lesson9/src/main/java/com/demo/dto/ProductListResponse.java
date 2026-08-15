package com.demo.dto;

import com.demo.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductListResponse {

    private List<Product> products = Collections.emptyList();
    private Integer total = 0;
    private Integer skip = 0;
    private Integer limit = 0;

}
