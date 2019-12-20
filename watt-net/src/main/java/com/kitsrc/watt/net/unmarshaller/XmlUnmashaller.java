package com.kitsrc.watt.net.unmarshaller;

import com.kitsrc.watt.net.AcsResponse;
import com.kitsrc.watt.net.exceptions.ClientException;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class XmlUnmashaller implements Unmarshaller {

    @Override
    public <T extends AcsResponse> T unmarshal(Class<T> clazz, String content) throws ClientException {
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            javax.xml.bind.Unmarshaller unmarshaller = jc.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(content));
        } catch (JAXBException e) {
            throw newUnmarshalException(clazz, content, e);
        }
    }

    private ClientException newUnmarshalException(Class<?> clazz, String xmlContent, Exception e) {
        return new ClientException("SDK.UnmarshalFailed",
                "unmarshal response from xml content failed, clazz = " + clazz.getSimpleName() + ", origin response = " + xmlContent, e);
    }
}
