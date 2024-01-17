package _cowork1;

public class stringSplit {
    public static String parseWeatherInfo(String input) {
        StringBuilder result = new StringBuilder();

        // 주어진 문자열을 줄바꿈 문자("\n")로 분할
        String[] lines = input.split("\\s+");

        // 필요한 정보 추출 및 형식화
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            // System.out.println(line);
            switch (line) {
                case "오늘의":
                    result.append("오늘의 날씨 : ").append(lines[i + 2]).append("\n");
                    break;
                case "현재":
                    // result.append("현재 온도 : ").append(lines[i + 1]).append("\n");
                    result.append("현재 온도 : ").append(lines[i + 1].split("온도")[1]).append("\n");
                    break;
                case "체감":
                    result.append("체감 : ").append(lines[i + 1]).append("\n");
                    break;
                case "습도":
                    result.append("습도 : ").append(lines[i + 1]).append("\n");
                    break;
                // case "북서풍":
                //     result.append("북서풍 : ").append(lines[i + 1]).append("\n");
                //     break;
                case "미세먼지":
                    result.append("미세먼지 : ").append(lines[i + 1]).append("\n");
                    break;
                case "초미세먼지":
                    result.append("초메시먼지 : ").append(lines[i + 1]).append("\n");
                    break;
                case "자외선":
                    result.append("자외선 : ").append(lines[i + 1]).append("\n");
                    break;
                default:
                    break;
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String input = "오늘의 날씨    맑음   현재 온도4.0°    어제보다 0.5° 낮아요  맑음    체감 1.3°   습도 30%   북서풍 3m/s         미세먼지 좋음     초미세먼지 좋음     자외선 좋음";
        String parsedInfo = parseWeatherInfo(input);

        System.out.println(parsedInfo);
    }
}