package stefanholzmueller.pp2.check;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface Outcome {

	// TODO getName / getMessageKey

	boolean isSuccessful();

}