package com.my.network.socialnetwork.model.product.phone;

import javax.persistence.*;

@Entity
//@Indexed
public class PhoneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Field(termVector = TermVector.YES)
    private String name;

    @ManyToOne
    private PhoneBrand phoneBrand;

    private String series;

    private Double screenSize;

    private String processorFrequency;

    private Boolean isQuadCore;

    private Boolean isOctaCore;

    private Double rearCameraMP;

    private Double frontCameraMP;

    private String operatingSystem;

    private Double ramInGB;

    private int batteryCapacity;

    private int internalStorage;

    private String url;

    private String color;

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

    public PhoneBrand getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(PhoneBrand phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Double screenSize) {
        this.screenSize = screenSize;
    }

    public String getProcessorFrequency() {
        return processorFrequency;
    }

    public void setProcessorFrequency(String processorFrequency) {
        this.processorFrequency = processorFrequency;
    }

    public Boolean getQuadCore() {
        return isQuadCore;
    }

    public void setQuadCore(Boolean quadCore) {
        isQuadCore = quadCore;
    }

    public Boolean getOctaCore() {
        return isOctaCore;
    }

    public void setOctaCore(Boolean octaCore) {
        isOctaCore = octaCore;
    }

    public Double getRearCameraMP() {
        return rearCameraMP;
    }

    public void setRearCameraMP(Double rearCameraMP) {
        this.rearCameraMP = rearCameraMP;
    }

    public Double getFrontCameraMP() {
        return frontCameraMP;
    }

    public void setFrontCameraMP(Double frontCameraMP) {
        this.frontCameraMP = frontCameraMP;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Double getRamInGB() {
        return ramInGB;
    }

    public void setRamInGB(Double ramInGB) {
        this.ramInGB = ramInGB;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public int getInternalStorage() {
        return internalStorage;
    }

    public void setInternalStorage(int internalStorage) {
        this.internalStorage = internalStorage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
