package cyf.utils
import eu.bitwalker.useragentutils.{Browser, OperatingSystem, UserAgent}
object AgentUtil{
  private def getAgentMap(v:Any):Map[String,String]= {
    v match {
      case a: Browser => Map("bro_name" -> a.getName, "bro_type" -> a.getBrowserType.getName, "bro_group" -> a.getGroup.getName, "bro_manu" -> a.getManufacturer.getName, "bro_eng" -> a.getRenderingEngine.name)
      case b: OperatingSystem => Map("os_name" -> b.getName, "os_type" -> b.getDeviceType.getName, "os_group" -> b.getGroup.getName, "os_manu" -> b.getManufacturer.getName)
      case c: UserAgent => getAgentMap(c.getBrowser)++getAgentMap(c.getOperatingSystem) ++ Map("bro_version" -> c.getBrowserVersion.getVersion)
    }
  }
  def getBrowserMap(agent:String)=getAgentMap(UserAgent.parseUserAgentString(agent).getBrowser)
  def getOsMap(agent:String)=getAgentMap(UserAgent.parseUserAgentString(agent).getOperatingSystem)
  def getAllMap(agent:String)=getAgentMap(UserAgent.parseUserAgentString(agent))
}