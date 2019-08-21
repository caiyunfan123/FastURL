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

  def addBrowserName(implicit newKey:String="bro_name")=putToMap("bro_name",newKey)(browserMap)
  def addBrowserType(implicit newKey:String="bro_type")=putToMap("bro_type",newKey)(browserMap)
  def addBrowserGroup(implicit newKey:String="bro_group")=putToMap("bro_group",newKey)(browserMap)
  def addBrowserManu(implicit newKey:String="bro_manu")=putToMap("bro_manu",newKey)(browserMap)
  def addOSName(implicit newKey:String="os_name")=putToMap("os_name",newKey)(OSMap)
  def addOSType(implicit newKey:String="os_type")=putToMap("os_type",newKey)(OSMap)
  def addOSGroup(implicit newKey:String="os_name")=putToMap("os_name",newKey)(OSMap)
  def addOSManu(implicit newKey:String="os_manu")=putToMap("os_manu",newKey)(OSMap)
  def addEngine(implicit newKey:String="engine")=putToMap("engine",newKey)(browserMap)
  def addVersion(implicit newKey:String="version")=putToMap("version",newKey)(browserMap)
  def addSelf(implicit key:String="agent")={outMap+=((key,agent));this}
  def addOthers={super.addOthers(browserMap,OSMap);this}

  private def putToMap(oldKey:String,newKey:String)(map:mutable.Map[String,String])={putOneToMap(oldKey,newKey)(map);this}
}