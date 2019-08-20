package cyf.utils
import eu.bitwalker.useragentutils.{Browser, OperatingSystem, UserAgent}

import scala.collection.mutable
object AgentUtil{
  private def getAgentMap(v:Any)= {
    v match {
      case a: Browser => mutable.Map("bro_name" -> a.getName, "bro_type" -> a.getBrowserType.getName, "bro_group" -> a.getGroup.getName, "bro_manu" -> a.getManufacturer.getName, "engine" -> a.getRenderingEngine.name)
      case b: OperatingSystem => mutable.Map("os_name" -> b.getName, "os_type" -> b.getDeviceType.getName, "os_group" -> b.getGroup.getName, "os_manu" -> b.getManufacturer.getName)
      case c: UserAgent => mutable.Map("version" -> c.getBrowserVersion.getVersion)
    }
  }
  def getBrowserMap(agent:String)=getAgentMap(UserAgent.parseUserAgentString(agent).getBrowser)
  def getOsMap(agent:String)=getAgentMap(UserAgent.parseUserAgentString(agent).getOperatingSystem)
  def getVersionMap(agent:String)=getAgentMap(UserAgent.parseUserAgentString(agent))
  def apply(agent: String): AgentUtil = new AgentUtil(agent)
}
class AgentUtil(agent: String) extends MapUtil{
  private lazy val browserMap=AgentUtil.getBrowserMap(agent)++=AgentUtil.getVersionMap(agent)
  private lazy val OSMap=AgentUtil.getOsMap(agent)

  def setBrowserName(newKey:String)=setMap("bro_name",newKey)(browserMap)
  def setBrowserType(newKey:String)=setMap("bro_type",newKey)(browserMap)
  def setBrowserGroup(newKey:String)=setMap("bro_group",newKey)(browserMap)
  def setBrowserManu(newKey:String)=setMap("bro_manu",newKey)(browserMap)
  def setOSName(newKey:String)=setMap("os_name",newKey)(OSMap)
  def setOSType(newKey:String)=setMap("os_type",newKey)(OSMap)
  def setOSGroup(newKey:String)=setMap("os_name",newKey)(OSMap)
  def setOSManu(newKey:String)=setMap("os_manu",newKey)(OSMap)
  def setEngine(newKey:String)=setMap("engine",newKey)(browserMap)
  def setVersion(newKey:String)=setMap("version",newKey)(browserMap)
  def setSelf(key:String)={outMap+=((key,agent));this}
  def setOthers={super.setOthers(browserMap,OSMap);this}

  private def setMap(oldKey:String,newKey:String)(map:mutable.Map[String,String])={putOneToMap(oldKey,newKey)(map);this}
}