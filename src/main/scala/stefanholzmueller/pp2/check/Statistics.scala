package stefanholzmueller.pp2.check

import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlRootElement
import scala.beans.BeanInfo
import scala.beans.BeanProperty

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
case class Statistics(@BeanProperty val chance: Double, @BeanProperty val averageQuality: Double) extends CheckStatistics