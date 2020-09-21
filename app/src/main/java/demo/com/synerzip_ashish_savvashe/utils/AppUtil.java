package demo.com.synerzip_ashish_savvashe.utils;

import demo.com.synerzip_ashish_savvashe.R;

public class AppUtil {


  /**
   * Get animation file according to weather status code
   *
   * @param weatherCode int weather status code
   * @return id of animation json file
   */
  public static int getWeatherAnimation(int weatherCode) {
    if (weatherCode / 100 == 2) {
      return R.raw.storm_weather;
    } else if (weatherCode / 100 == 3) {
      return R.raw.rainy_weather;
    } else if (weatherCode / 100 == 5) {
      return R.raw.rainy_weather;
    } else if (weatherCode / 100 == 6) {
      return R.raw.snow_weather;
    } else if (weatherCode / 100 == 7) {
      return R.raw.unknown;
    } else if (weatherCode == 800) {
      return R.raw.clear_day;
    } else if (weatherCode == 801) {
      return R.raw.few_clouds;
    } else if (weatherCode == 803) {
      return R.raw.broken_clouds;
    } else if (weatherCode / 100 == 8) {
      return R.raw.cloudy_weather;
    }
    return R.raw.unknown;
  }

  /**
   * Get weather status string according to weather status code
   *
   * @param weatherCode weather status code
   * @return String weather status
   *
   */
  public static String getWeatherStatus(int weatherCode) {
    if (weatherCode / 100 == 2) {
      return  "Thunderstorm";
    } else if (weatherCode / 100 == 3) {
      return  "Drizzle";
    } else if (weatherCode / 100 == 5) {
      return  "Rain";
    } else if (weatherCode / 100 == 6) {
      return  "Atmosphere";
    } else if (weatherCode / 100 == 7) {
      return  "Clear";
    } else if (weatherCode == 800) {
      return  "Few Clouds";
    } else if (weatherCode == 801) {
      return  "Clear";
    } else if (weatherCode == 803) {
      return   "Broken Clouds";
    } else if (weatherCode / 100 == 8) {
      return  "Cloud";
    }
    return  "Snow";
  }
}
