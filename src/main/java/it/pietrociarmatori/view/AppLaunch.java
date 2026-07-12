package it.pietrociarmatori.view;

import it.pietrociarmatori.view.view1.App;
import it.pietrociarmatori.view.view2.AppCli;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppLaunch {
    public static void main(String[] args) throws IOException {
        InputStream input = new FileInputStream("resources/appMode.properties");

        Properties properties = new Properties();
        properties.load(input);

        String opCode = properties.getProperty("MODE");
        if(opCode.equals("V1")) {
            App.main(args);
        }else if(opCode.equals("V2")){
            AppCli.main(args);
        }
    }
}
