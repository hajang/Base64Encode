/**
 * Created by Ha on 2016-02-04.
 */


    /*
        1. 소스의 바이너리 데이타로 부터 3바이트씩 꺼낸다. 만약 나머지 소스 문자열이 3바이트가 되지 않는다면 0으로 채운다.
        2. 최초 바이트의 MSB를 6비트씩 4개의 숫자로
        3. 각각의 수치를 하단의 표를 토대로 아스키 문자로 변환한다. 다만 실제의 데이터 길이가 3바이트에 미치지 못한다면 '='로 대체 한다.
        4. 이후 데이터가 없어질때까지 1~3을 반복한다.
     */
class Base64Encode {
    private final static String base64chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static String encode(String input) {

        // 결과, 패딩 문자열
        String result = "", padding = "";
        int c = input.length() % 3; // pad count

        // 문자열을 3의 배수로 만들기 위해서 오른쪽에 패딩 문자열(0)을 붙인다.
        if (c > 0) {
            for (; c < 3; c++) {
                padding += "=";
                input += "\0";
            }
        }

        // 3씩 증가시키며 문자열 끝까지
        for (c = 0; c < input.length(); c += 3) {

            // 76개의 문자마다 새로운 줄("\n") 을 추가한다(MIME 규칙)
            if (c > 0 && (c / 3 * 4) % 76 == 0)
                result += "\r\n";

            // 3개의 ASCII character는 한개의 24비트 숫자가 된다.
            int n = (input.charAt(c) << 16) + (input.charAt(c + 1) << 8)
                    + (input.charAt(c + 2));

            // 위의 24비트 숫자는 각각 6비트 숫자로 분리된다.
            int n1 = (n >> 18) & 63, n2 = (n >> 12) & 63, n3 = (n >> 6) & 63, n4 = n & 63;

            // 위 4개의 6비트 숫자는 base64 문자열 리스트(전역으로 선언된 base64chars 배열)의 인덱스로 사용된다.
            result += "" + base64chars.charAt(n1) + base64chars.charAt(n2)
                    + base64chars.charAt(n3) + base64chars.charAt(n4);
        }

        return result.substring(0, result.length() - padding.length()) + padding;
    }
}