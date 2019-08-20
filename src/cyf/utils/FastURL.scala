package cyf.utils

import com.google.gson.Gson

import scala.collection.JavaConversions.mapAsJavaMap
import scala.collection.mutable

class FastURL(url:String){
  private var map=mutable.Map[String,String]()++=FastURL.URLtoMap(url)
  def toMap=map
  def toJsonString=FastURL.mapToJson(map)
  /**
    * 将Ip解析结果添加到map中
    * @param keyOrIp    如果ip在map中存在，输入key添加解析结果;否则输入ip值
    * @param isWithin   如果ip在map中不存在，输入false
    * @return           map更新之后的FastURL
    */
  def putIPtoMap(keyOrIp: String, isWithin:Boolean=true)=putAnytoMapByFunction(keyOrIp,isWithin)(FastURL.IPtoMap _)
  def putAgentToMap(keyOrAgent:String, isWithin:Boolean=true)=putAnytoMapByFunction(keyOrAgent,isWithin)(FastURL.agentToMap _)
  def putURLtoMap(url:String)=putAnytoMapByFunction(url,false)(FastURL.URLtoMap _)
  def putAnytoMapByFunction(anyOrKey:String,isWithin:Boolean)(f:String=>Map[String,String])={if(isWithin) map.get(anyOrKey) match {case Some(agent)=>this++=f(agent) case None =>this} else this++=f(anyOrKey)}
  def empty={map=map.empty;this}
  def remove(key:String)=this-=key
  def -=(key:String)={map-key;this}
  def +=(key:String,value:String)={map+=((key,value));this}
  def ++=(traversableOnce: TraversableOnce[(String,String)]) = {map++= traversableOnce;this}
}

object FastURL{
  private val gson = new Gson()
  var isActor = false
  def apply(url: String) =new FastURL(url)
  def IPtoMap(ip:String)=IpUtil.select(ip)(IpUtil.ipRegex)
  def agentToMap(agent:String)=AgentUtil.getAllMap(agent)
  def URLtoMap(url:String)="[?&]([^&]*)=([^&]*)".r.findAllIn(URLDecoder.decodeURIComponent(url)).map(f=>(f.split("=")(0).substring(1),f.split("=")(1))).toMap
  def mapToJson(map:mutable.Map[String,String])=gson.toJson(mapAsJavaMap(map))
}
