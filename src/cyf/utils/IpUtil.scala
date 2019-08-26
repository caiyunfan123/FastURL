package cyf.utils

import scala.collection.mutable
import IpUtil.toIpArr

class IpUtil(ip:String)(implicit ipDataBase: IpDataBase,ipRegex:(String)=>mutable.Map[String,String]) extends MapUtil{
  private val ipMap=select(IpUtil.isIp.findFirstIn(ip).getOrElse(throw new RuntimeException(s"${ip}内不含ip")))
  def addCountry(implicit newKey:String="country")=putToMap("country",newKey)
  def addRegion(implicit newKey:String="region")=putToMap("region",newKey)
  def addCity(implicit newKey:String="city")=putToMap("city",newKey)
  def addIsp(implicit newKey:String="isp")=putToMap("isp",newKey)
  def addSelf(implicit key:String="ip")={outMap+=((key,ip));this}
  def addOthers= {super.addOthers(ipMap);this}

  private def putToMap(oldKey:String,newKey:String)={putOneToMap(oldKey,newKey)(ipMap);this}
  private def select(ip: String) ={
    implicit val ipArr = toIpArr(ip)
    ipRegex(
      if(ipDataBase.ipGroup.contains((ipArr(0),ipArr(1)))) find(ipDataBase.ipGroup((ipArr(0),ipArr(1))))(2)
      else find(ipDataBase.ipBase)(0)
    )
  }
  private def find(ipMap:Map[Array[Int],String])(index:Int)(implicit ipArr:Array[Int]): String ={
    val filterMap = ipMap.filter(f=>ipArr(index)==f._1(index))
    if(filterMap.nonEmpty)
      if(index==3)filterMap.iterator.next()._2 else find(filterMap)(index+1)
    else ipMap.find(a=>(toIpArr(a._2.split("\\s+")(0))(index) to a._1(index)).contains(ipArr(index))).getOrElse(throw new RuntimeException(s"找不到${ip}"))._2
  }
}
object IpUtil{
  val isForeign ="[^省市]*".r;
  val isNormal = "(.*省)(.*市)".r;
  val isSpecial ="(内蒙古)(.*市)".r;
  val isIp = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))".r
  private[utils] def toIpArr = (f: String) => for (str <- f.split("\\.")) yield str.toInt
  private implicit val ipRegex = (strIn:String) => {
    val in = strIn.split("\\s+");
    def getMap(str: String*) = mutable.Map("country"->str(0),"region"->str(1),"city"->str(2),"isp"->str(3));
    in(2) match {
      case isForeign() =>if(in.length==5) getMap(in(2),in(3),null,in(4)) else getMap(in(2),null,null,in(3))
      case isNormal(region,city) =>getMap("中国",region,city,in(3))
      case isSpecial(region,city) =>getMap("中国",region,city,in(3))
      case _ =>getMap("中国",in(2),in(2),in(3))}
  }
  def apply(ip: String)(implicit ipDataBase:IpDataBase=IpDataBase.ipDataBase)=new IpUtil(ip)
}