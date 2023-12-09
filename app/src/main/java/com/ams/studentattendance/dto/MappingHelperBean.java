package com.ams.studentattendance.dto;

import java.util.List;

public class MappingHelperBean {
    List<Integer> ids;
    List<String> values;

    public MappingHelperBean(List<Integer> ids, List<String> values) {
        this.ids = ids;
        this.values = values;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public MappingHelperBean() {
    }
}
