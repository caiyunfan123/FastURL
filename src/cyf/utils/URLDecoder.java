package cyf.utils;

public class URLDecoder
{


    /**
     *
     * Description:
     *
     * @param encodedURI
     * @return
     * @see
     */
    public static String decodeURIComponent(String encodedURI)
    {
        char actualChar;

        StringBuffer buffer = new StringBuffer();

        int bytePattern, sumb = 0;

        for (int i = 0, more = -1; i < encodedURI.length(); i++ )
        {
            actualChar = encodedURI.charAt(i);

            switch (actualChar)
            {
                case '%':
                {
                    actualChar = encodedURI.charAt(++i);
                    int hb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
                    actualChar = encodedURI.charAt(++i);
                    int lb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
                    bytePattern = (hb << 4) | lb;
                    break;
                }
                case '+':
                {
                    bytePattern = ' ';
                    break;
                }
                default:
                {
                    bytePattern = actualChar;
                }
            }

            if ((bytePattern & 0xc0) == 0x80)
            { // 10xxxxxx
                sumb = (sumb << 6) | (bytePattern & 0x3f);
                if (--more == 0) buffer.append((char)sumb);
            }
            else if ((bytePattern & 0x80) == 0x00)
            { // 0xxxxxxx
                buffer.append((char)bytePattern);
            }
            else if ((bytePattern & 0xe0) == 0xc0)
            { // 110xxxxx
                sumb = bytePattern & 0x1f;
                more = 1;
            }
            else if ((bytePattern & 0xf0) == 0xe0)
            { // 1110xxxx
                sumb = bytePattern & 0x0f;
                more = 2;
            }
            else if ((bytePattern & 0xf8) == 0xf0)
            { // 11110xxx
                sumb = bytePattern & 0x07;
                more = 3;
            }
            else if ((bytePattern & 0xfc) == 0xf8)
            { // 111110xx
                sumb = bytePattern & 0x03;
                more = 4;
            }
            else
            { // 1111110x
                sumb = bytePattern & 0x01;
                more = 5;
            }
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        String a = "192.168.30.2^A1563797574.259^Amaster^A/web/index.html?en=e_crt&oid=123460&on=%E6%B5%8B%E8%AF%95%E8%AE%A2%E5%8D%95123460&cua=300&cut=%24&pt=alipay&ver=1&pl=website&sdk=js&u_ud=BD8C4842-F5E3-4F0C-99DE-05DA244ED8C9&u_sd=87661021-76AA-4492-839E-FA7E012DFA90&c_time=1563797574950&l=zh-CN&b_iev=Mozilla%2F5.0%20(Windows%20NT%206.1%3B%20Win64%3B%20x64)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F74.0.3729.169%20Safari%2F537.36&b_rst=1280*720";
        System.out.println(decodeURIComponent(a));

    }
}