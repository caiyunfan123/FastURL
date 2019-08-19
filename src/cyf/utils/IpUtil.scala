package cyf.utils

import scala.io.Source
import IpUtil._

//ip解析的最终版本，精简化API
class IpUtil private(ipBase:Map[Array[Int],String]){
  val start = System.currentTimeMillis()
  def select(ip: String) ={implicit val ipArr = toIpArr(ip);if(ipGroup.contains((ipArr(0),ipArr(1))))find(ipGroup((ipArr(0),ipArr(1))))(1) else find(ipBase)(0)}
  private val ipGroup:Map[(Int,Int),Map[Array[Int],String]] =ipBase.groupBy(f=>(f._1(0),f._1(1)))
}
object IpUtil{
  private var ipDatabase:IpUtil = null
  private def toIpArr = (f: String) => for (str <- f.split("\\.")) yield str.toInt
  private def find(ipMap:Map[Array[Int],String])(index:Int)(implicit ipArr:Array[Int]): String ={val filterMap = ipMap.filter(f=>ipArr(index)==f._1(index));if(filterMap.nonEmpty) find(filterMap)(index+1) else ipMap.find(f=>(f._1(index) to toIpArr(f._2.split("\\s+")(1))(index)).contains(ipArr(index))).get._2}
  implicit val ipRegex = (strIn:String) => {
    val in = strIn.split("\\s+");
    def getMap(str: String*) = Map("country"->str(0),"region"->str(1),"city"->str(2),"isp"->str(3));
    val isForeign =".*[^省市].*".r;
    val isNormal = "(.*省)(.*市)".r;
    val isSpecial ="(内蒙古)(.*市)".r;
    in(2) match {
      case isForeign() =>if(in.length==5) getMap(in(2),in(3),null,in(4)) else getMap(in(2),null,null,in(3))
      case isNormal(region,city) =>getMap("中国",region,city,in(3))
      case isSpecial(region,city) =>getMap("中国",region,city,in(3))
      case _ =>getMap("中国",null,in(2),in(3))}
  }
  def apply:IpUtil = {if(ipDatabase==null) ipDatabase = new IpUtil(Source.fromURL(getClass.getResource("/resource/ipDataBase.txt")).getLines().map(f=>(toIpArr(f.split("\\s+")(0)),f)).toMap);ipDatabase}
  def select(ip:String)(implicit f:String=>Map[String,String])=f(apply.select(ip))
}
