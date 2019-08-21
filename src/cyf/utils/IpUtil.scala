package cyf.utils

import scala.collection.mutable
import scala.io.Source
import IpUtil._

class IpUtil(ip:String)(implicit ipRegex:(String)=>mutable.Map[String,String]) extends MapUtil{
  private val ipMap=select(ip)
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
      if(ipGroup.contains((ipArr(0),ipArr(1)))) find(ipGroup((ipArr(0),ipArr(1))))(1)
      else find(ipBase)(0)
    )
  }
  private def find(ipMap:Map[Array[Int],String])(index:Int)(implicit ipArr:Array[Int]): String ={
    val filterMap = ipMap.filter(f=>ipArr(index)==f._1(index))
    if(filterMap.nonEmpty) find(filterMap)(index+1)
    else ipMap.find(f=>(f._1(index) to toIpArr(f._2.split("\\s+")(1))(index)).contains(ipArr(index))).get._2
  }
}
object IpUtil{
  private def toIpArr = (f: String) => for (str <- f.split("\\.")) yield str.toInt
  private lazy val ipBase = Source.fromURL(getClass.getResource("/resource/ipDataBase.txt")).getLines().map(f=>(toIpArr(f.split("\\s+")(0)),f)).toMap
  private lazy val ipGroup = ipBase.groupBy(f=>(f._1(0),f._1(1)))
  private implicit val ipRegex = (strIn:String) => {
    val in = strIn.split("\\s+");
    def getMap(str: String*) = mutable.Map("country"->str(0),"region"->str(1),"city"->str(2),"isp"->str(3));
    val isForeign =".*[^省市].*".r;
    val isNormal = "(.*省)(.*市)".r;
    val isSpecial ="(内蒙古)(.*市)".r;
    in(2) match {
      case isForeign() =>if(in.length==5) getMap(in(2),in(3),null,in(4)) else getMap(in(2),null,null,in(3))
      case isNormal(region,city) =>getMap("中国",region,city,in(3))
      case isSpecial(region,city) =>getMap("中国",region,city,in(3))
      case _ =>getMap("中国",null,in(2),in(3))}
  }
  def apply(ip: String)={
    if(!ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) throw new RuntimeException("ip格式错误")
    new IpUtil(ip)
  }
}