package com.my.network.socialnetwork.controller;

import com.my.network.socialnetwork.model.product.phone.PhoneBrand;
import com.my.network.socialnetwork.model.product.phone.PhoneBrandRepository;
import com.my.network.socialnetwork.model.product.phone.PhoneModel;
import com.my.network.socialnetwork.model.product.phone.PhoneModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/load")
public class LoadDataController {

    @Autowired
    PhoneBrandRepository phoneBrandRepository;

    @Autowired
    PhoneModelRepository phoneModelRepository;

    @GetMapping(value="/brands")
    public ResponseEntity loadBrands(){
        readBrandFromFile();
        return new ResponseEntity<>("loaded", HttpStatus.OK);
    }

    public void readBrandFromFile(){
        String fileName = "brands.csv";

        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            ArrayList<PhoneBrand> phoneBrands = new ArrayList<>();
            PhoneBrand toAdd = new PhoneBrand();
            while ((line = bufferedReader.readLine()) != null) {
                String[] split_lines = line.split(",");
                //System.out.println(split_lines[0]);
                toAdd.setId(new Long(split_lines[1]));
                toAdd.setName(split_lines[0]);
                //phoneBrands.add(toAdd);
                phoneBrandRepository.save(toAdd);
            }


            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

    }

    @GetMapping(value="/phones")
    public ResponseEntity loadPhones(){
        readLinesFromFile();
        return new ResponseEntity<>("loaded", HttpStatus.OK);
    }

    public void readLinesFromFile() {
        String fileName = "phones.csv";

        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            //initialize keyIndex HashMap
            HashMap<String, Integer> keyIndex = initCSVKeyIndex(bufferedReader.readLine());
            //HashMap<String, Integer>  singleBrandNames = new HashMap<>();
            int index = 1;
            PhoneModel toSave;
            while ((line = bufferedReader.readLine()) != null) {
                toSave = new PhoneModel();
                String[] split_lines = line.split(",");
                toSave.setPhoneBrand(phoneBrandRepository.findPhoneBrandByName(split_lines[0]));
                toSave.setName(split_lines[1]);
                toSave.setSeries(split_lines[2]);
                try {
                    toSave.setScreenSize(new Double(split_lines[3]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                toSave.setProcessorFrequency(split_lines[4]);
                try {
                    toSave.setRearCameraMP(new Double(split_lines[5]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }try {
                    toSave.setFrontCameraMP(new Double(split_lines[6]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                toSave.setOperatingSystem(split_lines[7]);
                try {
                    toSave.setRamInGB(new Double(split_lines[8]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    toSave.setBatteryCapacity(new Integer(split_lines[9]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    toSave.setInternalStorage(new Integer(split_lines[10]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                toSave.setUrl(split_lines[11]);
                toSave.setColor(split_lines[12]);
                /*if(!singleBrandNames.containsKey(split_lines[0])) {
                    singleBrandNames.put(split_lines[0], index);
                    index++;
                    System.out.println(split_lines[0] + ", "+singleBrandNames.get(split_lines[0]));
                }*/
                phoneModelRepository.save(toSave);
                //makeObjectAndSave(line);
            }
            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }


    }

    private void makeObjectAndSave(String line) {

    }

    private HashMap<String, Integer> initCSVKeyIndex(String firstLine) {
        HashMap<String, Integer> mappedKeyIndex = new HashMap<String, Integer>();
        String[] allKeys = firstLine.split(",", -1);
        for (int i = 0; i < allKeys.length; i++) {
            mappedKeyIndex.put(allKeys[i], i);
        }
        return mappedKeyIndex;
    }

    /*public static void main(String[] args) {
        LoadDataController lc = new LoadDataController();
        //lc.readLinesFromFile();
        lc.readBrandFromFile();
    }*/


}
