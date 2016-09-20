package test_suite;


import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RunWith(DataProviderRunner.class)

public class TestBase implements MyCategories {
    protected static Path tempFolder;
    protected static String folderPath;
    @BeforeClass()
    public static void setUp() {
        createTempFileWithDir();

    }

    @AfterClass()
    public static void tearDown () {
        cleanUp();
    }

    @Before()
    public void before () {
        try {
            File f = new File(folderPath, "Game of Thrones");
            f.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @After()
    public void after () {
        cleanUpFiles();
    }


    protected void cleanUpFiles() {
        for (int i = tempFolder.toFile().listFiles().length-1; i >= 0; i-- ){
            tempFolder.toFile().listFiles()[i].delete();
        }
    }

    private static void createTempFileWithDir() {
        try {
            tempFolder = Files.createTempDirectory("tempFolder");
            folderPath = tempFolder.toFile().toString();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cleanUp() {
        tempFolder.toFile().delete();
    }

    @DataProvider
    public static Object[][] books ()  {

        List<Object[]> userData = new ArrayList<Object[]>();
        userData.add(new Object[]{"Game of thrones"});
        return (Object[][])userData.toArray(new Object[][]{});
    }

    @DataProvider
    public static Object[][] loadBookNameFromFile() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                TestBase.class.getResourceAsStream("/bookNames.data")));

        List<Object[]> userData = new ArrayList<Object[]>();
        String line = in.readLine();
        while (line != null) {
            userData.add(line.split(","));
            line = in.readLine();
        }

        in.close();

        return (Object[][])userData.toArray(new Object[][]{});
    }

}
