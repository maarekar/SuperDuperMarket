package api;

import Generated3.SuperDuperMarketDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

final public class ReadXml {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "Generated3";

    private ReadXml(){
    }

    public static SuperDuperMarketDescriptor loadFile(InputStream inputFile) throws JAXBException, FileNotFoundException{
            //InputStream inputStream = new FileInputStream(new File(inputFile));
            SuperDuperMarketDescriptor SDM = (SuperDuperMarketDescriptor) deserializeFrom(inputFile);
            return SDM;
    }

    private static SuperDuperMarketDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(in);
    }
}
