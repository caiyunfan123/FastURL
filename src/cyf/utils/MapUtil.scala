package cyf.utils

import com.google.gson.Gson

import scala.collection.JavaConversions.mapAsJavaMap
import scala.collection.mutable
import MapUtil.gson

trait MapUtil {
  def toMap=outMap
  def toJsonString = gson.toJson(mapAsJavaMap(outMap))
  protected def addOthers(map:mutable.Map[String,String]*)=map.foreach(map=>outMap++=map)
  protected var outMap=mutable.Map[String,String]()
  protected def putOneToMap(oldKey:String,newKey:String)(inMap:mutable.Map[String,String])(implicit outMap:mutable.Map[String,String]=outMap)= inMap.get(oldKey) match {
    case Some(value) => outMap += (newKey -> value);inMap -= oldKey
    case None => println(s"${oldKey}不存在")
  }
}
object MapUtil{
  private val gson = new Gson()
}