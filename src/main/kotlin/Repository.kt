/***
Copyright (c) 2021 Razeware LLC

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or
sell copies of the Software, and to permit persons to whom
the Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

Notwithstanding the foregoing, you may not use, copy, modify,
merge, publish, distribute, sublicense, create a derivative work,
and/or sell copies of the Software in any work that is designed,
intended, or marketed for pedagogical or instructional purposes
related to programming, coding, application development, or
information technology. Permission for such use, copying,
modification, merger, publication, distribution, sublicensing,
creation of derivative works, or sale is expressly withheld.

This project and source code may use libraries or frameworks
that are released under various Open-Source licenses. Use of
those libraries and frameworks are governed by their own
individual licenses.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
DEALINGS IN THE SOFTWARE.
 ***/

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class Repository(
  private val apiKey: String,
  private val client: HttpClient = defaultHttpClient,
) {

  private val transformer = WeatherTransformer()

  suspend fun weatherForCity(city: String): Lce<WeatherResults> {
    return try {
      val result = getWeatherForCity(city)
      val content = transformer.transform(result)
      Lce.Content(content)
    } catch (e: Exception) {
      e.printStackTrace()
      Lce.Error(e)
    }
  }

  private suspend fun getWeatherForCity(city: String): WeatherResponse =
    client.get(
      "https://api.weatherapi.com/v1/forecast.json" +
          "?key=$apiKey" +
          "&q=$city" +
          "&days=5" +
          "&aqi=no" +
          "&alerts=no"
    )

  companion object {
    val defaultHttpClient = HttpClient(CIO) {
      install(JsonFeature) {
        serializer = KotlinxSerializer(
          json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
          }
        )
      }
    }
  }
}
