package cyf.utils

import scala.io.Source

class IpDataBase private(){
  import IpUtil.toIpArr
  private[utils] val ipBase = Source.fromURL(getClass.getResource("/resource/ipDataBase.txt")).getLines().map(f=>(toIpArr(f.split("\\s+")(0)),f)).toMap
  private[utils] val ipGroup = ipBase.groupBy(f=>(f._1(0),f._1(1)))

}
object IpDataBase{
  lazy val ipDataBase=new IpDataBase
}