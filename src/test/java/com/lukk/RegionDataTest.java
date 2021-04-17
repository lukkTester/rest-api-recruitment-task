package com.lukk;

import com.lukk.pojo.DataWrapper;
import com.lukk.pojo.Regions;
import com.lukk.utilities.CalculationUtils;
import com.lukk.utilities.MapUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

public class RegionDataTest {

    private RequestSpecification reqSpec;
    private SoftAssert softAssert;

    @BeforeTest
    public void setup() {

        RestAssured.baseURI = "https://api.carbonintensity.org.uk/regional";

        reqSpec = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    @Test
    public void Scenario1() {

        /* Scenario 1:
            1.	Get carbon intensity for each region
            2.	Get intensity value forecast
            3.	Sort regions for highest to lowest intensity
            4.	Print sorted list in the logs starting with value followed by short name of the region
        */

        Response resp = reqSpec.request().get();

        DataWrapper respBody = resp.as(DataWrapper.class);
        List<Regions> regions = respBody.getData().get(0).getRegions();

        Map<Integer, String> sortedIntensity = new TreeMap<>();

        try {

            ListIterator iterator = regions.listIterator();
            int iter = 0;

            while (iterator.hasNext()) {

                sortedIntensity.put(regions.get(iter).getIntensity().getForecast(),
                        regions.get(iter).getShortname());
                iter++;
                iterator.next();
            }

        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        System.out.println("Regions sorted by intensity forecast:");
        System.out.println(sortedIntensity);
    }

    @Test
    public void Scenario2() {

        /*  Scenario 2:
            1.	For each region get carbon intensity
            2.	Assert that generation mix sums to 100
        */

        softAssert = new SoftAssert();

        Response resp = reqSpec.request().get();

        List<List<Number>> allPerc = resp.jsonPath().getList("data.regions.generationmix[0].perc");

        LinkedList<String> regions = new LinkedList<>();
        regions.addAll(resp.jsonPath().getList("data.regions.shortname[0]"));

        for (List<Number> perc : allPerc) {

            String name = regions.pop();

            float result = CalculationUtils.sumListElements(perc);

            System.out.println(name);
            System.out.println("Sum of perc equals: " + result);
            System.out.println("------------");

            softAssert.assertEquals(100.0, result, 0.1);
        }

        softAssert.assertAll();
    }

    @Test
    public void Scenario3() {

        /* Scenario 3:
            1.	For each region get carbon intensity
            2.	For each fuel type list five regions where the generation percentage is the highest
        */

        int limit = 5;

        Response resp = reqSpec.request().get();

        DataWrapper respBody = resp.as(DataWrapper.class);
        List<Regions> regions = respBody.getData().get(0).getRegions();

        Map<String, Double> biomass = new HashMap<>();
        Map<String, Double> coal = new HashMap<>();
        Map<String, Double> imports = new HashMap<>();
        Map<String, Double> gas = new HashMap<>();
        Map<String, Double> nuclear = new HashMap<>();
        Map<String, Double> other = new HashMap<>();
        Map<String, Double> hydro = new HashMap<>();
        Map<String, Double> solar = new HashMap<>();
        Map<String, Double> wind = new HashMap<>();
        Map<String, Double> ff = new HashMap<>();

        try {

            ListIterator iterator = regions.listIterator();
            int iter = 0;

            while (iterator.hasNext()) {

                biomass.put(regions.get(iter).getShortname(),
                        regions.get(iter).getGenerationmix().get(0).getPerc()
                );
                coal.put(regions.get(iter).getShortname(),
                        regions.get(iter).getGenerationmix().get(1).getPerc()
                );
                imports.put(regions.get(iter).getShortname(),
                        regions.get(iter).getGenerationmix().get(2).getPerc()
                );
                gas.put(regions.get(iter).getShortname(),
                        regions.get(iter).getGenerationmix().get(3).getPerc()
                );
                nuclear.put(regions.get(iter).getShortname(),
                        regions.get(iter).getGenerationmix().get(4).getPerc()
                );
                other.put(regions.get(iter).getShortname(),
                        regions.get(iter).getGenerationmix().get(5).getPerc()
                );
                hydro.put(regions.get(iter).getShortname(),
                        regions.get(iter).getGenerationmix().get(6).getPerc()
                );
                solar.put(regions.get(iter).getShortname(),
                        regions.get(iter).getGenerationmix().get(7).getPerc()
                );
                wind.put(regions.get(iter).getShortname(),
                        regions.get(iter).getGenerationmix().get(8).getPerc()
                );

                iter++;
                iterator.next();

            }

        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        System.out.println(MapUtils.limitMapAndReturnList(MapUtils.sortByValueDescending(biomass), limit));
        System.out.println(MapUtils.limitMapAndReturnList(MapUtils.sortByValueDescending(coal), limit));
        System.out.println(MapUtils.limitMapAndReturnList(MapUtils.sortByValueDescending(imports), limit));
        System.out.println(MapUtils.limitMapAndReturnList(MapUtils.sortByValueDescending(gas), limit));
        System.out.println(MapUtils.limitMapAndReturnList(MapUtils.sortByValueDescending(nuclear), limit));
        System.out.println(MapUtils.limitMapAndReturnList(MapUtils.sortByValueDescending(other), limit));
        System.out.println(MapUtils.limitMapAndReturnList(MapUtils.sortByValueDescending(hydro), limit));
        System.out.println(MapUtils.limitMapAndReturnList(MapUtils.sortByValueDescending(solar), limit));
        System.out.println(MapUtils.limitMapAndReturnList(MapUtils.sortByValueDescending(wind), limit));
    }

}
