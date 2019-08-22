package cyf.utils

class FastURL(url:String)(implicit URLRegex:(String)=>TraversableOnce[(String,String)],ipDataBase: IpDataBase)extends MapUtil{
  outMap++=URLRegex(url)
  /**
    * 将Ip解析结果添加到map中
    * @param keyOrIp   如果ip在map中存在，输入key添加解析结果;否则输入ip值
    * @param isKey  如果ip在map中不存在，输入false
    * @param f         传入一个AgentUtil对象或IpUtil对象，自由决定输出的内容
    * @return          ip解析结果添加完成后的FastURL
    */
  def putIPtoMap(keyOrIp: String, isKey:Boolean=true)(implicit f:String=>MapUtil=FastURL.ipUtil)=putAnytoMapByFunction(keyOrIp,isKey)(f)
  def putAgentToMap(keyOrAgent:String, isKey:Boolean=true)(implicit f:String=>MapUtil=FastURL.agentUtil)=putAnytoMapByFunction(keyOrAgent,isKey)(f)
  def putAnytoMapByFunction(anyOrKey:String,isWithin:Boolean)(f:String=>MapUtil)={if(isWithin) outMap.get(anyOrKey) match {case Some(agent)=>this++=f(agent).toMap case None =>this} else this++=f(anyOrKey).toMap}

  def -=(key:String)={outMap-=key;this}
  def +=(key:String,value:String)={outMap+=((key,value));this}
  def ++=(traversableOnce: TraversableOnce[(String,String)]) = {outMap++= traversableOnce;this}
  def remove(key:String)=this-=key
  def rename(oldKey:String,newKey:String)={putOneToMap(oldKey,newKey)(outMap);this}
  def renames(keys: Map[String,String])={keys.foreach(f=>rename(f._1,f._2));this}
}

object FastURL{
  implicit def URLRegex(url:String)="[?&]([^&]*)=([^&]*)".r.findAllIn(URLDecoder.decodeURIComponent(url)).map(f=>(f.split("=")(0).substring(1),f.split("=")(1)))
  implicit def ipUtil(ip:String)(implicit ipDataBase: IpDataBase)=IpUtil(ip)(ipDataBase).addOthers
  implicit def agentUtil(agent:String)=AgentUtil(agent).addOthers
  def apply(url: String)(implicit ipDataBase: IpDataBase=IpDataBase.ipDataBase)=new FastURL(url)
}
