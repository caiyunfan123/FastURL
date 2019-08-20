package cyf.test

import cyf.utils.FastURL

object Test{
  def main(args: Array[String]): Unit = {
    val url = "192.168.30.2^A1563797574.259^Amaster^A/web/index.html?en=e_crt&oid=123460&on=%E6%B5%8B%E8%AF%95%E8%AE%A2%E5%8D%95123460&cua=1&cut=%24&pt=alipay&ver=1&pl=website&sdk=js&u_ud=BD8C4842-F5E3-4F0C-99DE-05DA244ED8C9&u_sd=87661021-76AA-4492-839E-FA7E012DFA90&c_time=1563797574950&l=zh-CN&b_iev=Mozilla%2F5.0%20(Windows%20NT%206.1%3B%20Win64%3B%20x64)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F74.0.3729.169%20Safari%2F537.36&b_rst=1280*720"
    val start = System.currentTimeMillis()
    println(FastURL(url).putIPtoMap("1.2.3.4", false).putAgentToMap("b_iev").remove("b_iev").toJsonString)
    println(System.currentTimeMillis()-start)
  }
}