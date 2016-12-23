package org.humanResources.employment.model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Entity
@Table(name = "employment", schema="HUMAN_RESOURCES")
public class Employment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="title")
    private String title;
   // private Date created;
   // private String summary;

    @Transient
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    public Employment(String title/*, String summary, String date*/) throws ParseException{
        this.title = title;
   //     this.summary = summary;
   //     this.created = format.parse(date);
    }

    Employment(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
/*
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCreatedAsShort(){
        return format.format(created);
    }*/

    public String toString(){
        StringBuilder value = new StringBuilder("EmploymentEntry(");
        value.append("Id: ");
        value.append(id);
        value.append(",Title: ");
        value.append(title);
        /*value.append(",Summary: ");
        value.append(summary);
        value.append(",Created: ");
        value.append(getCreatedAsShort());*/
        value.append(")");
        return value.toString();
    }
}