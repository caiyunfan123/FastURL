package cyf.utils

import com.google.gson.Gson
import scala.collection.JavaConversions.mapAsJavaMap
import scala.collection.mutable

class FastURL(private val map:mutable.Map[String,String]){
  def toMap=map
  def toJsonString=FastURL.mapToJson(map)
  /**
    * 将Ip解析结果添加到map中
    * @param keyOrIp    如果ip在map中存在，输入key添加解析结果;否则输入ip值
    * @param isWithin   如果ip在map中不存在，输入false
    * @return           map更新之后的FastURL
    */
  def putIPtoMap(keyOrIp: String, isWithin:Boolean=true)={putAnytoMapByFunction(keyOrIp,isWithin)(FastURL.IPtoMap _);this}
  def putAgentToMap(keyOrAgent:String, isWithin:Boolean=true)={putAnytoMapByFunction(keyOrAgent,isWithin)(FastURL.agentToMap _);this}
  def putAnytoMapByFunction(anyOrKey:String,isWithin:Boolean)(f:String=>Map[String,String])={if(isWithin) map.get(anyOrKey) match {case Some(agent)=>map++=f(agent) case None =>map} else map ++= f(anyOrKey);this}
  def removeKey(key:String)={map-=key;this}
  def empty=FastURL.empty
}
object FastURL{
  private val gson = new Gson()
  def apply(url: String): FastURL = new FastURL(mutable.Map[String,String]()++="[?&]([^&]*)=([^&]*)".r.findAllIn(URLDecoder.decodeURIComponent(url)).map(f=>(f.split("=")(0).substring(1),f.split("=")(1))))
  def empty = new FastURL(mutable.Map[String,String]())
  def IPtoMap(ip:String)=IpUtil.select(ip)(IpUtil.ipRegex)
  def agentToMap(agent:String)=AgentUtil.getAllMap(agent)
  def mapToJson(map:mutable.Map[String,String])=gson.toJson(mapAsJavaMap(map))
  def main(args: Array[String]): Unit = {
    val url = "192.168.30.2^A1563797574.259^Amaster^A/web/index.html?en=e_crt&oid=123460&on=%E6%B5%8B%E8%AF%95%E8%AE%A2%E5%8D%95123460&cua=300&cut=%24&pt=alipay&ver=1&pl=website&sdk=js&u_ud=BD8C4842-F5E3-4F0C-99DE-05DA244ED8C9&u_sd=87661021-76AA-4492-839E-FA7E012DFA90&c_time=1563797574950&l=zh-CN&b_iev=Mozilla%2F5.0%20(Windows%20NT%206.1%3B%20Win64%3B%20x64)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F74.0.3729.169%20Safari%2F537.36&b_rst=1280*720"
    println(FastURL(url).putIPtoMap("1.2.3.4",false).putAgentToMap("b_iev").removeKey("b_iev").toJsonString)
  }
}
