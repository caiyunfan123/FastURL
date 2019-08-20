package cyf.pojo

case class IP(ip: String) {
  if(!ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) throw new RuntimeException("ip格式错误")
}
