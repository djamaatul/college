<template>
  <ion-page>
    <ion-content :fullscreen="true" class="ion-padding">
      <div class="container">

        <div class="current-weather-card">
          <div class="header">
            <div>
              <h1>{{ currentWeather.weatherStatus }}</h1>
              <p>{{ currentWeather.time.format("HH:mm") }}</p>
            </div>
            <span class="icon">{{ currentWeather.icon }}</span>
          </div>

          <h2 class="temp">{{ currentWeather.temp }} {{ currentWeather.unit }} </h2>
        </div>

        <ion-segment v-model="selectedDay" scrollable>
          <ion-segment-button v-for="day in days" :value="day.format('YYYY-MM-DD')">
            <ion-label>{{ day.format("ddd, DD MM") }}</ion-label>
          </ion-segment-button>
        </ion-segment>

        <div class="slider hourly-card-container">
          <div v-for="value in selectedWeatherDay" class="hourly-card">
            <span>{{ value.time.format("HH:mm") }}</span>
            <span class="icon">{{ value.icon }}</span>
            <h2>{{ value.temp }} {{ value.unit }}</h2>
            <p>{{ value.weatherStatus }}</p>
          </div>
        </div>

      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import { IonContent, IonPage, IonSegment } from '@ionic/vue';
import moment, { Moment } from 'moment';
import { onMounted, ref, computed } from 'vue';

type Weather = {
  time: Moment,
  temp: number,
  unit: string
} & WeatherStatus

type WeatherStatus = {

  weatherStatus: string,
  icon: string
}

const currentWeather = ref<Weather>({
  time: moment(),
  temp: 0,
  weatherStatus: "-",
  icon: "-",
  unit: "-"
});

const selectedDay = ref(moment().format("YYYY-MM-DD"))
const days = ref<Moment[]>();
const weatherDays = ref<Weather[]>([]);

const selectedWeatherDay = computed(() => {
  return weatherDays.value.filter(weather => {
    return weather.time.format("YYYY-MM-DD") === selectedDay.value
  });
})


async function getWeatherData() {
  const response = await fetch("https://api.open-meteo.com/v1/forecast?latitude=-6.2146&longitude=106.8451&hourly=,temperature_2m,weather_code&current=temperature_2m,weather_code&timezone=Asia%2FBangkok")

  const data = await response.json();

  const currentWeatherStatus = getStatusWeather(data.current.weather_code);

  currentWeather.value = {
    time: moment(data.current.time, "YYYY-MM-DDTHH:mm"),
    temp: data.current.temperature_2m,
    weatherStatus: currentWeatherStatus.weatherStatus,
    icon: currentWeatherStatus.icon,
    unit: data.current_units.temperature_2m
  }

  const dayExist = new Set<string>();
  weatherDays.value = data.hourly.time.map((time: string, index: number) => {
    const weatherStatus = getStatusWeather(data.hourly.weather_code[index]);
    dayExist.add(moment(time, "YYYY-MM-DDTHH:mm").format("YYYY-MM-DD"))
    return {
      time: moment(time, "YYYY-MM-DDTHH:mm"),
      temp: data.hourly.temperature_2m[index],
      weatherStatus: weatherStatus.weatherStatus,
      icon: weatherStatus.icon,
      unit: data.hourly_units.temperature_2m
    }
  })

  days.value = [...dayExist].map((date: string) => moment(date, "YYYY-MM-DD"));
}

function getStatusWeather(code: number): WeatherStatus {
  if (code == 0) return {
    weatherStatus: "Cerah",
    icon: "☀️"
  }

  if ([1, 2].includes(code)) return {
    weatherStatus: "Berawan",
    icon: "☁️"
  }

  if (code === 3) return {
    weatherStatus: "Mendung",
    icon: "☁️"
  }

  if ([45, 48].includes(code)) return {
    weatherStatus: "Berkabut",
    icon: "🌫️"
  }

  if ([51, 53, 55].includes(code)) return {
    weatherStatus: "Gerimis",
    icon: "🌦️"
  }

  if ([61, 63, 65, 80, 81, 82].includes(code)) return {
    weatherStatus: "Hujan",
    icon: "🌦️"
  }

  if ([95, 96, 99].includes(code)) return {
    weatherStatus: "Badai",
    icon: "🌩️"
  }

  return {
    weatherStatus: "Normal",
    icon: "☀️"
  }

}

onMounted(() => {
  getWeatherData();
})

</script>

<style scoped>
.current-weather-card {
  display: flex;
  flex-direction: column;
  padding: 10px;
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.5), rgba(255, 255, 255, 0.9));
  border-radius: 10px;
}

.current-weather-card .header {
  display: flex;
  justify-content: space-between;
}

.current-weather-card .icon,
.temp {
  font-size: 60px;
}

.container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  min-height: 100vh;
}

.slider {
  display: flex;
  justify-content: left;
  gap: 4px
}

.slider ion-segment-button,
.hourly-card {
  flex-shrink: 0;
  min-width: 100px;
}

.hourly-card-container {
  overflow-x: scroll;
}

.hourly-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  border-radius: 4px;
  background-color: rgba(255, 255, 255, 0.5);
}

.hourly-card .icon {
  font-size: 40px;
}
</style>
