package com.orangehrm.utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DataProviders {

    private static final String FILE_PATH = System.getProperty("user.dir")+"/src/main/resources/testdata/TestData.xlsx";

    @DataProvider(name="validLoginData")
    public static Object[][] validLoginData() throws IOException {
        return getSheetData("validLoginData");
    }

    @DataProvider(name="invalidLoginData")
    public static Object[][] invalidLoginData() throws IOException {
        return getSheetData("invalidLoginData");
    }

    @DataProvider(name="emplVerification")
    public static Object[][] emplVerification() throws IOException {
        return getSheetData("emplVerification");
    }

    private static Object[][] getSheetData(String sheetName) throws IOException {
        List<String[]> sheetData = ExcelReaderUtility.getSheetData(FILE_PATH, sheetName);

        Object[][] data = new Object[sheetData.size()][sheetData.getFirst().length];
        for (int i = 0; i < sheetData.size(); i++) {
            data[i] = sheetData.get(i);
            System.out.println(Arrays.toString(sheetData.get(i)));
        }

        return data;
    }
}
