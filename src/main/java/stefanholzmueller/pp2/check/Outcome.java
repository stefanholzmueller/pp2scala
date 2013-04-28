package stefanholzmueller.pp2.check;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface Outcome {

	String getMessage();

	boolean isSuccessful();

}